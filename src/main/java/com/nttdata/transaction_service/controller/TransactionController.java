package com.nttdata.transaction_service.controller;

import com.nttdata.transaction_service.business.TransactionService;
import com.nttdata.transaction_service.business.TransactionServiceImp;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
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

    @GetMapping(
            value = "/{id}",
            produces = { "application/json" }
    )
    public ResponseEntity<TransactionGet> transactionsIdGet(@ApiParam(value = "",required=true) @PathVariable("id") String id){
        return service.transactionsIdGet(id);
    }

    @PostMapping(
            value = "",
            consumes = { "application/json" }
    )
    public ResponseEntity<TransactionGet> transactionsPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody TransactionPost transactionPost){
        return service.transactionsPost(transactionPost);
    }

    @PutMapping(
            value = "",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<TransactionGet> transactionsPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody TransactionPut transactionPut){
        return service.transactionsPut(transactionPut);
    }


    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> transactionsIdDelete(@ApiParam(value = "",required=true) @PathVariable("id") String id){
        return service.transactionsIdDelete(id);
    }

}


