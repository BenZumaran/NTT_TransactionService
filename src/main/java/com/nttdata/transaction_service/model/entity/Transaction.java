package com.nttdata.transaction_service.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private int number;
    private ProductEntity sender;
    private ProductEntity receiver;
    private String type;
    private double amount;
    private LocalDateTime createdDate;
    private PersonEntity holder;
    private PersonEntity signatory;
    private CardEntity card;

}