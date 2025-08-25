package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionPostProduct
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-24T19:57:01.660543300-05:00[America/Lima]")
public class TransactionPostProduct   {
  @JsonProperty("id")
  private String id;

  /**
   * Type of product
   */
  public enum TypeEnum {
    SAVINGS_ACCOUNT("savings_account"),
    
    CHECKING_ACCOUNT("checking_account"),
    
    FIXED_TERM_ACCOUNT("fixed_term_account"),
    
    PERSONAL_CREDIT("personal_credit"),
    
    BUSINESS_CREDIT("business_credit"),
    
    CREDIT_CARD("credit_card");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  public TransactionPostProduct id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique ID
   * @return id
  */
  @ApiModelProperty(value = "Unique ID")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TransactionPostProduct type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type of product
   * @return type
  */
  @ApiModelProperty(value = "Type of product")


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionPostProduct transactionPostProduct = (TransactionPostProduct) o;
    return Objects.equals(this.id, transactionPostProduct.id) &&
        Objects.equals(this.type, transactionPostProduct.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionPostProduct {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

