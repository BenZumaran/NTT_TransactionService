package com.nttdata.transaction_service.model.entity;

import com.nttdata.transaction_service.model.TransactionGet;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private int number;
    private ProductEntity product;
    private String type;
    private ClientEntity client;
    private double amount;
    private LocalDateTime createdDate;
    private PersonEntity holder;
    private PersonEntity signatory;

}