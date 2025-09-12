package com.nttdata.transaction_service.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
public class ProductEntity {
    @Id
    @Field("_id")
    private String id;
    private String type;
    private String number;
    private double balance;
    private double limit;
}
