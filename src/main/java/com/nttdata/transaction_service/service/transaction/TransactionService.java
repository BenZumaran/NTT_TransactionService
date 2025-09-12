package com.nttdata.transaction_service.service.transaction;

import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<TransactionGet> getTransactions();

    Mono<Void> deleteTransactionById(String id);

    Mono<TransactionGet> getTransactionById(String id);

    Mono<TransactionGet> insertTransaction(Mono<TransactionPost> transactionPostMono);

    Mono<TransactionGet> updateTransaction(Mono<TransactionPut> transactionPutMono);

    Flux<TransactionGet> getTransactionsByProductId(String id);

    Flux<TransactionGet> getTransactionsByClientDocument(String document, String from, String to);

    Flux<TransactionGet> getTransactionsByTypeAndProductIdBetweenDates(String id, String type, String from, String to);

}
