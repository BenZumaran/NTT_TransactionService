package com.nttdata.transaction_service.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {
    @Id
    private String _id;
    private String type;
    private String number;
    private double balance;
    private double limit;
}