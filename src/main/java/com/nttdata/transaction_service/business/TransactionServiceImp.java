package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.model.entity.Transaction;
import com.nttdata.transaction_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImp implements TransactionService{

    @Autowired
    TransactionRepository repository;

    @Autowired
    TransactionMapper mapper;

    public ResponseEntity<List<TransactionGet>> transactionsGet() {
        try{
            return new ResponseEntity<List<TransactionGet>>(mapper.getTransasctionGetListOfTransactionsList(repository.findAll()), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<TransactionGet> transactionsPost(TransactionPost transactionPost) {
        try{
            return new ResponseEntity<TransactionGet>(mapper.getTransactionGetOfTransaction(repository.save(mapper.getTransactionOfTransactionPost(transactionPost))), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            //.util.NoSuchElementException: No value present
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<TransactionGet> transactionsPut(TransactionPut transactionPut) {
        Optional<Transaction> previousTransaction = repository.findById(transactionPut.getId());
        return new ResponseEntity<>(mapper.getTransactionGetOfTransaction(repository.save(mapper.getTransactionOfTransactionPut(transactionPut, previousTransaction))),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionGet> transactionsIdGet(String id) {
        try {
              return new ResponseEntity<TransactionGet>(mapper.getTransactionGetOfTransactionOptional(repository.findById(id)),HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Void> transactionsIdDelete(String id) {
        try{
            repository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
