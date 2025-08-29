package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.api.TransactionsApiDelegate;
import com.nttdata.transaction_service.business.account.AccountService;
import com.nttdata.transaction_service.business.credit.CreditService;
import com.nttdata.transaction_service.dto.NumberProjection;
import com.nttdata.transaction_service.mapper.AccountMapper;
import com.nttdata.transaction_service.mapper.CreditMapper;
import com.nttdata.transaction_service.mapper.TransactionMapper;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionGetClientBalance;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.repository.TransactionRepository;
import com.nttdata.transaction_service.util.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class TransactionApiDelegateImp implements TransactionsApiDelegate {

    @Autowired
    TransactionRepository repository;

    @Autowired
    AccountService accountService;

    @Autowired
    CreditService creditService;

    private static final Logger log = LoggerFactory.getLogger(TransactionApiDelegateImp.class);

    @Override
    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsGet(ServerWebExchange exchange) {
        try {
            return Mono.just(new ResponseEntity<>(repository.findAll()
                            .map(TransactionMapper::transactionToTransactionGet), HttpStatus.OK))
                    .doOnSuccess(nonusing -> log.info("Transactions found."))
                    .doOnError(nonusing2 -> log.error("Error at found transactions --> transactionsGet"));

        } catch (Exception ex) {
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @Override
    public Mono<ResponseEntity<Void>> transactionsIdDelete(String id, ServerWebExchange exchange) {
        try {
            return repository.deleteById(id)
                    .doOnSuccess(nonusing2 -> log.info("Transaction with id {} was deleted.", id))
                    .doOnError(nonusing3 -> log.error("Error at delete transaction --> transactionsIdDelete."))
                    .map(nonusing -> new ResponseEntity<>(HttpStatus.OK));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        }
    }

    //To Get specific Transaction by ID
    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsIdGet(String id, ServerWebExchange exchange) {
        try {
            //Search in DB
            return repository.findById(id)
                    //Map response to TransactionGet to respond
                    .switchIfEmpty(Mono.error(new TransactionNotFoundException(id)))
                    .map(TransactionMapper::transactionToTransactionGet)
                    //Map to ResponseEntity for final return
                    .map(transactionGet ->
                            new ResponseEntity<>(transactionGet, HttpStatus.OK))
                    //Log success
                    .doOnSuccess(nonusing -> log.info("Transaction with id {} was found.", id))
                    //Log error
                    .onErrorResume(TransactionNotFoundException.class, error -> {
                        log.error(error.getMessage());
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }).onErrorResume(error -> {
                        log.error("Error: {} --> transactionsIdGet", error.getMessage());

                        return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                    });
        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsPost(Mono<TransactionPost> transactionPostMono, ServerWebExchange exchange) {
        return transactionPostMono.zipWhen(transactionPost -> repository.findTopNumberByOrderByNumberDesc()
                        .map(NumberProjection::getNumber)
                        .switchIfEmpty(Mono.just(0)))
                .map(TransactionMapper::transactionPostToTransaction)
                .flatMap(repository::save)
                .map(TransactionMapper::transactionToTransactionGet)
                .map(transactionGet -> new ResponseEntity<>(transactionGet, HttpStatus.CREATED))
                .doOnSuccess(transactionGetResponseEntity ->
                        log.info("Transaction was inserted id: {}",
                                transactionGetResponseEntity.getBody().getId()))
                //Log error
                .onErrorReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST))
                .doOnError(errorMessage -> log.error("Error: {} --> transactionsPost", errorMessage.getMessage()));
    }

    //To Update specific Transaction
    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsPut(Mono<TransactionPut> transactionPutMono, ServerWebExchange exchange) {
        try {
            //Zip When to Search with repository by TransactionPut's ID
            return transactionPutMono.zipWhen(transactionPut -> repository.findById(transactionPut.getId()))
                    //Valid only changes sent in TransactionPut
                    .map(TransactionMapper::transactionPutAndTransactionPreviousToTransaction)
                    //Save the response to DB
                    .flatMap(transaction -> repository.save(transaction))
                    //Map to TransactionGet for response purposes
                    .map(TransactionMapper::transactionToTransactionGet)
                    //Map to ResponseEntity to return final response
                    .map(transactionGet -> new ResponseEntity<>(transactionGet, HttpStatus.OK))
                    //Log success of update
                    .doOnSuccess(nonusing -> log.info("Transaction with was updated."))
                    //Log error
                    .doOnError(nonusing2 -> log.error("Error at update transaction --> transactionsPut"));
        } catch (Exception ex) {
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Override
    public Mono<ResponseEntity<TransactionGetClientBalance>> transactionsProductIdGet(String id, ServerWebExchange exchange) {
        return repository.findBySenderIdOrderByCreatedDate(id)
                .map(TransactionMapper::transactionToTransactionGet)
                .collectList()
                .zipWhen(transactionGets -> {
                    switch (transactionGets.get(0).getSender().getType().getValue()) {
                        case "savings_account":
                        case "checking_account":
                        case "fixed_term_account":
                            return accountService.fetchGetAccountById(transactionGets.get(0).getSender().getId())
                                    .map(AccountMapper::accountResponseCreateDtoToProduct);
                        case "personal_credit":
                        case "business_credit":
                        case "credit_card":
                            return creditService.fetchGetCreditById(transactionGets.get(0).getSender().getId())
                                    .map(CreditMapper::creditResponseDtoToProduct);
                    }
                    return Mono.error(new Error("Transaction Product Type not Found. Valid Types: [ savings_account, checking_account, fixed_term_account, personal_credit, business_credit, credit_card "));
                }).map(TransactionMapper::productAndTransactionGetListToTransactionGetClientBalance)
                .map(transactionGetClientBalance -> new ResponseEntity<>(transactionGetClientBalance, HttpStatus.OK))
                .doOnSuccess(nousing -> log.info("Information for product with id {} find.", id))
                .onErrorResume(error -> {
                    log.error("Error at find information for Product with id {} --> transactionsProductIdGet", id);
                    return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsClientDocumentGet(String document,
                                                                                    String from,
                                                                                    String to,
                                                                                    ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(repository.findByHolderDocumentAndCreatedDateBetween(document, LocalDateTime.parse(from), LocalDateTime.parse(to))
                .map(TransactionMapper::transactionToTransactionGet)
                .onErrorResume(error -> {
                    log.error("Error at find Transactions for client with document {} --> transactionsClientDocumentGet", document);
                    return Flux.empty();
                })
                .doOnComplete(() -> log.info("Transactions for client with document {} find.", document))
                , HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsTypeProductIdGet(String id,
                                                                                   String type,
                                                                                   String from,
                                                                                   String to,
                                                                                   ServerWebExchange exchange) {

        return Mono.just(new ResponseEntity<>(repository.findByTypeAndSenderIdAndCreatedDateBetween(type, id, LocalDateTime.parse(from), LocalDateTime.parse(to))
                .map(TransactionMapper::transactionToTransactionGet)
                .onErrorResume(error -> {
                    log.error("Error at find Transactions for product with id {} --> transactionsTypeProductIdGet", id);
                    return Flux.empty();
                })
                .doOnComplete(() -> log.info("Transactions for product with id {} find.", id))
                , HttpStatus.OK));

    }

}
