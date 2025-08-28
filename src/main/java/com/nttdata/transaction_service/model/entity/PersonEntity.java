package com.nttdata.transaction_service.model.entity;

import com.nttdata.transaction_service.model.Person;
import lombok.Data;

@Data
public class PersonEntity {
    private String id;
    private String document;
    private String type;
    private String fullName;

}