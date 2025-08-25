package com.nttdata.transaction_service.model.entity;

import com.nttdata.transaction_service.model.Person;
import lombok.Data;

@Data
public class PersonEntity {
    private String document;
    private String fullName;
    private String signature;

}