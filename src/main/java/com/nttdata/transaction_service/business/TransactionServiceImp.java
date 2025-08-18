package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionGetClientBalance;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.model.entity.Transaction;
import com.nttdata.transaction_service.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionServiceImp implements TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    TransactionMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImp.class);

    public Flux<TransactionGet> transactionsGet() {
        try {
            Flux<TransactionGet> responseList = mapper.getTransactionGetListOfTransactionsList(repository.findAll());
            responseList.subscribe(transactionGet -> log.info("Transactions were find."));
            return responseList;
        } catch (NoSuchElementException nex) {
            log.error("Problems with Optional for one or more Transactional in list");
            log.error(nex.toString());
            //.util.NoSuchElementException: No value present
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return Flux.empty();
    }

    @Override
    public Mono<TransactionGet> transactionsPost(TransactionPost transactionPost) {
        try {
            Mono<TransactionGet> response = mapper.getTransactionGetOfTransaction(repository.save(mapper.getTransactionOfTransactionPost(transactionPost)));
            response.subscribe(transactionGet -> log.info("Transaction with id: {}, were added.", transactionGet.getId()));
            return response;
        } catch (NoSuchElementException nex) {
            log.error("Problems with Optional at insert transactional.");
            log.error(nex.toString());
            //.util.NoSuchElementException: No value present
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return Mono.empty();
    }

    @Override
    public Mono<TransactionGet> transactionsPut(TransactionPut transactionPut) {
        try {
            Mono<TransactionGet> response = repository.findById(transactionPut.getId())
                    .flatMap(transaction -> mapper.getTransactionOfTransactionPut(transactionPut, Mono.just(transaction)))
                    .map(repository::save)
                    .flatMap(mapper::getTransactionGetOfTransaction);
            response.subscribe(transactionGet -> log.info("Transaction with id: {}, were updated.", transactionGet.getId()));
            return response;

        } catch (NoSuchElementException nex) {
            log.error("Problems with Optional for Transactional with id: {}", transactionPut.getId());
            log.error(nex.toString());
            //.util.NoSuchElementException: No value present
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return Mono.empty();
    }

    @Override
    public Mono<TransactionGet> transactionsIdGet(String id) {
        try {
            Mono<TransactionGet> response = mapper.getTransactionGetOfTransaction(repository.findById(id));
            response.subscribe(transactionGet -> log.info("Transaction with id: {} find.", transactionGet.getId()));
            return response;
        } catch (NoSuchElementException nex) {
            log.error("Problems with Optional for Transaction with id: {}", id);
            log.error(nex.toString());
            //.util.NoSuchElementException: No value present
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return Mono.empty();
    }

    @Override
    public Mono<String> transactionsIdDelete(String id) {
        try {
            Mono<Void> emptyResponse = repository.deleteById(id);
            emptyResponse.subscribe(m -> log.info("Transaction with id: {}, was deleted.", id));
            return Mono.just("Transaction with id: " + id + ", was deleted.");
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return Mono.empty();
    }

    @Override
    public Mono<TransactionGetClientBalance> transactionsTypeClientIdGet(String type, String id) {
        return   null;
        /*
        "product": {
        "id": "f53d05e257b6e6062a644c39",
        "type": "account",
        "number": null,
        "balance": 865.22,
        "limit": 1281.57
        }

        repository.findByClientIdSortByCreatedDate(id);
*/
    }


}
