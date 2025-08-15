package com.nttdata.transaction_service.repository;

import com.nttdata.transaction_service.model.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

}

