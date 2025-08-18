package com.nttdata.transaction_service.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Product {
    @Id
    @Field("_id")
    private String id;
    private String type;
    private String number;
    private double balance;
    private double limit;
}