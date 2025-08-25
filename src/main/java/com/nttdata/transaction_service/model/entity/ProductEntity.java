package com.nttdata.transaction_service.model.entity;

import com.nttdata.transaction_service.model.Product;
import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ProductEntity {
    @Id
    @Field("_id")
    private String id;
    private String type;
    private String number;
    private double balance;
    private double limit;
}
