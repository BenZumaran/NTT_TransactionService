package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.api.TransactionsApiDelegate;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.service.transaction.TransactionService;
import com.nttdata.transaction_service.util.TransactionNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TransactionApiDelegateImp implements TransactionsApiDelegate {

    @Autowired
    TransactionService transactionService;


    @Override
    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsGet(ServerWebExchange exchange) {
        return transactionService
                .getTransactions()
                .collectList()
                .map(list -> ResponseEntity.ok(Flux.fromIterable(list)))
                .onErrorReturn(e -> e instanceof TransactionNotFoundException, ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> transactionsIdDelete(String id, ServerWebExchange exchange) {
        return transactionService
                .deleteTransactionById(id)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    //To Get specific Transaction by ID
    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsIdGet(String id, ServerWebExchange exchange) {
        return transactionService
                .getTransactionById(id)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @Override
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackCircuitBreakerTransactionPost")
    public Mono<ResponseEntity<TransactionGet>> transactionsPost(Mono<TransactionPost> transactionPostMono, ServerWebExchange exchange) {
        return transactionService
                .insertTransaction(transactionPostMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<String>> fallbackCircuitBreakerTransactionPost(Exception ex) {
        log.info("fallback circuit breaker post transaction");
        return Mono.just(new ResponseEntity<>("Not available, try later", HttpStatus.LOCKED));
    }

    //To Update specific Transaction
    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsPut(Mono<TransactionPut> transactionPutMono, ServerWebExchange exchange) {
        return transactionService
                .updateTransaction(transactionPutMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsProductIdGet(String id, ServerWebExchange exchange) {
        return transactionService
                .getTransactionsByProductId(id)
                .collectList()
                .map(list -> ResponseEntity.ok(Flux.fromIterable(list)))
                .onErrorReturn(e -> e instanceof TransactionNotFoundException, ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @Override

    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsClientDocumentGet(
            String document, String from, String to, ServerWebExchange exchange) {
        return transactionService
                .getTransactionsByClientDocument(document, from, to)
                .collectList()
                .map(list -> ResponseEntity.ok(Flux.fromIterable(list)))
                .onErrorReturn(e -> e instanceof TransactionNotFoundException, ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsTypeProductIdGet(
            String id, String type, String from, String to, ServerWebExchange exchange) {
        return transactionService
                .getTransactionsByTypeAndProductIdBetweenDates(id, type, from, to)
                .collectList()
                .map(list -> ResponseEntity.ok(Flux.fromIterable(list)))
                .onErrorReturn(e -> e instanceof TransactionNotFoundException, ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

}
