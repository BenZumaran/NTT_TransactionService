package com.nttdata.transaction_service.service.kafka;

import com.nttdata.transaction_service.dto.transaction.TransactionPostDTO;
import com.nttdata.transaction_service.mapper.TransactionMapper;
import com.nttdata.transaction_service.service.transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class KafkaMessageListener {


    @Autowired
    TransactionService transactionService;

    @KafkaListener(topics = "transaction-credit-operation", groupId = "transactions-group")
    public void consumeEvents(TransactionPostDTO transactionPost) {
        transactionService.insertTransaction(
                        Mono.just(
                                TransactionMapper.transactionPostDtoToTransactionPost(transactionPost)))
                .subscribe();
        log.info("consumer consume the events {} ", transactionPost);
    }

//    @KafkaListener(topics = "javatechie-demo1",groupId = "jt-group-new")
//    public void consume2(String message) {
//        log.info("consumer2 consume the message {} ", message);
//    }
//
//    @KafkaListener(topics = "javatechie-demo1",groupId = "jt-group-new")
//    public void consume3(String message) {
//        log.info("consumer3 consume the message {} ", message);
//    }
//
//    @KafkaListener(topics = "javatechie-demo1",groupId = "jt-group-new")
//    public void consume4(String message) {
//        log.info("consumer4 consume the message {} ", message);
//    }
}