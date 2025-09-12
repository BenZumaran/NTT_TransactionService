package com.nttdata.transaction_service.service.transaction;

import com.nttdata.transaction_service.dto.NumberProjection;
import com.nttdata.transaction_service.mapper.TransactionMapper;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.repository.TransactionRepository;
import com.nttdata.transaction_service.service.client.ClientService;
import com.nttdata.transaction_service.util.TransactionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Service
public class TransactionServiceImp implements TransactionService {

    @Autowired
    TransactionRepository repository;
    @Autowired
    ClientService clientService;

    @Override
    public Flux<TransactionGet> getTransactions() {


        try {
            return repository.findAll()
                    .map(TransactionMapper::transactionToTransactionGet)
                    .switchIfEmpty(Flux.error(new TransactionNotFoundException()))
                    .doOnComplete(() -> log.info("Transactions found."))
                    .doOnError(error -> log.error("Error: {} --> getTransactions", error.getMessage()));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Flux.empty();
        }
    }

    @Override
    public Mono<Void> deleteTransactionById(String id) {
        try {

            return repository.deleteById(id)
                    .doOnSuccess(nonusing2 -> log.info("Transaction with id {} was deleted.", id))
                    .doOnError(nonusing3 -> log.error("Error at delete transaction --> deleteTransactionById."));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.empty();
        }
    }

    @Override
    public Mono<TransactionGet> getTransactionById(String id) {
        try {


            //Search in DB
            return
                    //Map response to TransactionGet to respond
                    //cacheOperations.get(id).map(
                    //                res -> res
                    //        ).map(CacheMapper::cacheTransactionDtoToTransaction)
                    //        .switchIfEmpty(
                    //                repository.findById(id)
                    //                        .map(transaction -> {
                    //                                    cacheOperations
                    //                                            .set(id, CacheMapper.transactionToCacheTransactionDto(transaction))
                    //                                            .subscribe();
                    //                                    return transaction;
                    //                                }
                    //                        ))
                    repository.findById(id)
                            .switchIfEmpty(Mono.error(new TransactionNotFoundException(id)))
                            .map(TransactionMapper::transactionToTransactionGet)
                            //Log success
                            .doOnSuccess(nonusing -> log.info("Transaction with id {} was found.", id))
                            .doOnError(error ->
                                    log.error("Error: {} --> getTransactionById", error.getMessage()));
        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.empty();
        }
    }

    @Override
    public Mono<TransactionGet> insertTransaction(Mono<TransactionPost> transactionPostMono) {
        try {
            return transactionPostMono.zipWhen(transactionPost -> repository
                            .findTopNumberByOrderByNumberDesc()
                            .map(NumberProjection::getNumber)
                            .switchIfEmpty(Mono.just(0)))
                    .map(TransactionMapper::transactionPostToTransaction)
                    .zipWhen(transaction -> {
                        if (transaction.getHolder() != null)
                            if (transaction.getHolder().getId() != null)
                                return clientService.fetchGetClientById(transaction.getHolder().getId());
                            else
                                return clientService.fetchGetClientByDocument(transaction.getHolder().getDocument());
                        else if (transaction.getSignatory().getId() != null)
                            return clientService.fetchGetClientById(transaction.getSignatory().getId());
                        else
                            return clientService.fetchGetClientByDocument(transaction.getSignatory().getDocument());
                    })
                    .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                            .filter(throwable -> throwable instanceof WebClientResponseException)) // Retries 3 times with 1-second backoff only for WebClientRequestException
                    .map(TransactionMapper::updatePersonsFromTransactionAndClientResponseDTO)
                    .flatMap(repository::save)
                    .map(TransactionMapper::transactionToTransactionGet)
//                    .map(transactionGet -> {
//                        Cache cache = cacheManager.getCache("TRANSACTION_CACHE");
//                        if (cache != null)
//                            cache.put(transactionGet.getId(), transactionGet);
//                        return transactionGet;
//                    })
                    .doOnSuccess(transactionGetResponseEntity ->
                            log.info("Transaction was inserted id: {}",
                                    transactionGetResponseEntity.getId()))
                    //Log error
                    .doOnError(error ->
                            log.error("Error: {} --> insertTransaction", error.getMessage()));
        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.empty();
        }
    }


    @Override
    public Mono<TransactionGet> updateTransaction(Mono<TransactionPut> transactionPutMono) {
        try {
            //Zip When to Search with repository by TransactionPut's ID
            return transactionPutMono.zipWhen(
                            transactionPut -> repository
                                    .findById(transactionPut.getId())
                                    .switchIfEmpty(Mono.error(
                                            new TransactionNotFoundException(transactionPut.getId()
                                            ))))
                    //Valid only changes sent in TransactionPut
                    .map(TransactionMapper::transactionPutAndTransactionPreviousToTransaction)
                    //Save the response to DB
                    .flatMap(transaction -> repository.save(transaction))
                    //Map to TransactionGet for response purposes
                    .map(TransactionMapper::transactionToTransactionGet)
                    //Log success of update
                    .doOnSuccess(nonusing -> log.info("Transaction with was updated."))
                    //Log error
                    .doOnError(nonusing2 -> log.error("Error at update transaction --> updateTransaction"));
        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.empty();
        }
    }

    @Override
    public Flux<TransactionGet> getTransactionsByProductId(String id) {
        try {
            return repository.findBySenderIdOrderByCreatedDate(id)
                    .map(TransactionMapper::transactionToTransactionGet)
                    .switchIfEmpty(Flux.error(new TransactionNotFoundException()))
                    .doOnComplete(() -> log.info("Information for product with id {} find.", id))
                    .doOnError(error -> log.error("Error: {} --> getTransactionsByProductId", error.getMessage()));

        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Flux.empty();
        }
    }

    @Override
    public Flux<TransactionGet> getTransactionsByClientDocument(String document, String from, String to) {
        try {
            return repository.findByHolderDocumentAndCreatedDateBetween(document, LocalDateTime.parse(from), LocalDateTime.parse(to))
                    .map(TransactionMapper::transactionToTransactionGet)
                    .switchIfEmpty(Flux.error(new TransactionNotFoundException()))
                    .doOnComplete(() -> log.info("Transactions for client with document {} find.", document))
                    .doOnError(error -> log.error("Error: {} --> getTransactionsByClientDocument", error.getMessage()));
        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Flux.empty();
        }
    }

    @Override
    public Flux<TransactionGet> getTransactionsByTypeAndProductIdBetweenDates(String id, String type, String from, String to) {

        try {
            return repository.findByTypeAndSenderIdAndCreatedDateBetween(type, id, LocalDateTime.parse(from), LocalDateTime.parse(to))
                    .map(TransactionMapper::transactionToTransactionGet)
                    .switchIfEmpty(Flux.error(new TransactionNotFoundException()))
                    .doOnComplete(() -> log.info("Transactions for product with id {} find.", id))
                    .doOnError(error -> log.error("Error: {} --> getTransactionsByTypeAndProductIdBetweenDates", error.getMessage()));
        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Flux.empty();
        }
    }
}
