package com.nttdata.transaction_service.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Type of transaction
 */
public enum TransactionType {
  
  DEPOSIT("deposit"),
  
  WITHDRAWAL("withdrawal"),
  
  PAYMENT("payment"),
  
  PURCHASE("purchase"),
  
  CHARGE("charge"),
  
  CREATE("create"),
  
  TRANSFER("transfer");

  private String value;

  TransactionType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static TransactionType fromValue(String value) {
    for (TransactionType b : TransactionType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

