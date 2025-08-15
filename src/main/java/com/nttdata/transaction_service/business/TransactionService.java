package com.nttdata.transaction_service.business;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;


public interface TransactionService {

    ResponseEntity<List<TransactionGet>> transactionsGet();

    ResponseEntity<Void> transactionsPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody TransactionPost transactionPost);

    ResponseEntity<Void> transactionsIdPut(@ApiParam(value = "",required=true) @PathVariable("id") String id, @ApiParam(value = "" ,required=true )  @Valid @RequestBody TransactionPut transactionPut);

    ResponseEntity<TransactionGet> transactionsIdGet(@ApiParam(value = "",required=true) @PathVariable("id") String id);

    ResponseEntity<Void> transactionsIdDelete(@ApiParam(value = "",required=true) @PathVariable("id") String id);

}
