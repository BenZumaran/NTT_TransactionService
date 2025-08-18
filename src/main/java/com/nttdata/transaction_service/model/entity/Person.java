package com.nttdata.transaction_service.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Person {
    private String document;
    private String fullName;
    private String signature;
}