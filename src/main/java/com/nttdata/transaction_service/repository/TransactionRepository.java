package com.nttdata.transaction_service.repository;

import com.nttdata.transaction_service.dto.NumberProjection;
import com.nttdata.transaction_service.model.entity.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findBySenderIdOrderByCreatedDate(String senderId);
    Mono<NumberProjection> findTopNumberByOrderByNumberDesc();
    Flux<Transaction> findByTypeAndSenderId(String type, String senderId);
}

