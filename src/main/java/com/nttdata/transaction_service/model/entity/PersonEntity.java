package com.nttdata.transaction_service.model.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonEntity {
    private String id;
    private String document;
    private String type;
    private String fullName;

}