package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImp implements TransactionService{

    @Autowired
    TransactionRepository repository;

    @Autowired
    TransactionMapper mapper;

    public ResponseEntity<List<TransactionGet>> transactionsGet() {
        return mapper.getTransasctionListOfTransactionsGetList(repository.findAll());
    }

    @Override
    public ResponseEntity<Void> transactionsPost(TransactionPost transactionPost) {
        return null;
    }

    @Override
    public ResponseEntity<Void> transactionsIdPut(String id, TransactionPut transactionPut) {
        return null;
    }

    @Override
    public ResponseEntity<TransactionGet> transactionsIdGet(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> transactionsIdDelete(String id) {
        return null;
    }


}
