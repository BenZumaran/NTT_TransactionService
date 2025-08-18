package com.nttdata.transaction_service.business;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionGetClientBalance;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;


public interface TransactionService {

    Flux<TransactionGet> transactionsGet();

    Mono<TransactionGet> transactionsPost(TransactionPost transactionPost);

    Mono<TransactionGet> transactionsPut(TransactionPut transactionPut);

    Mono<TransactionGet> transactionsIdGet(String id);

    Mono<String> transactionsIdDelete(String id);

    Mono<TransactionGetClientBalance> transactionsTypeClientIdGet(String type, String id);

}
