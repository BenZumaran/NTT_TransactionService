package com.nttdata.transaction_service.model.entity;

import com.nttdata.transaction_service.model.Client;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ClientEntity {
    @Id
    @Field("_id")
    private String id;
    private String type;
    private String document;

}