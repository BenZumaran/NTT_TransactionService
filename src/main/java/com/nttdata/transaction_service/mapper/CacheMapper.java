package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.transaction.CacheTransactionDTO;
import com.nttdata.transaction_service.model.entity.Transaction;

public class CacheMapper {

    public static CacheTransactionDTO transactionToCacheTransactionDto(Transaction transaction) {
        return CacheTransactionDTO.builder()
                .id(transaction.getId())
                .number(transaction.getNumber())
                .sender(transaction.getSender())
                .receiver(transaction.getReceiver())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .createdDate(transaction.getCreatedDate())
                .holder(transaction.getHolder())
                .signatory(transaction.getSignatory())
                .card(transaction.getCard())
                .build();
    }

    public static Transaction cacheTransactionDtoToTransaction(CacheTransactionDTO transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .number(transaction.getNumber())
                .sender(transaction.getSender())
                .receiver(transaction.getReceiver())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .createdDate(transaction.getCreatedDate())
                .holder(transaction.getHolder())
                .signatory(transaction.getSignatory())
                .card(transaction.getCard())
                .build();
    }

}
