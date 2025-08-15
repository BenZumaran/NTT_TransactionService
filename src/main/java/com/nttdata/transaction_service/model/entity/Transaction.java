package com.nttdata.transaction_service.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String _id;
    private int number;
    private Product product;
    private String type;
    private double amount;
    private LocalDateTime createdDate;
    private Client client;
    private Person holder;
    private Person signatory;
}