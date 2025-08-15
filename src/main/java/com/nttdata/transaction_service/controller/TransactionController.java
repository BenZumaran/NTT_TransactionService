package com.nttdata.transaction_service.controller;

import com.nttdata.transaction_service.business.TransactionService;
import com.nttdata.transaction_service.business.TransactionServiceImp;
import com.nttdata.transaction_service.model.TransactionGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService service;

    @GetMapping(
            value = "",
            produces = { "application/json" }
    )
    public ResponseEntity<List<TransactionGet>> transactionsGet(){
        return service.transactionsGet();
    }
}
