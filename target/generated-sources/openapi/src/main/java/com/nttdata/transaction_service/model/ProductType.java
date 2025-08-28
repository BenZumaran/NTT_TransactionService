package com.nttdata.transaction_service.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Type of product
 */
public enum ProductType {
  
  SAVINGS_ACCOUNT("savings_account"),
  
  CHECKING_ACCOUNT("checking_account"),
  
  FIXED_TERM_ACCOUNT("fixed_term_account"),
  
  PERSONAL_CREDIT("personal_credit"),
  
  BUSINESS_CREDIT("business_credit"),
  
  CREDIT_CARD("credit_card");

  private String value;

  ProductType(String value) {
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
  public static ProductType fromValue(String value) {
    for (ProductType b : ProductType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

