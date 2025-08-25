package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Product
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-24T19:57:01.660543300-05:00[America/Lima]")
public class Product   {
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

  @JsonProperty("number")
  private String number = null;

  @JsonProperty("balance")
  private BigDecimal balance;

  @JsonProperty("limit")
  private BigDecimal limit;

  public Product id(String id) {
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

  public Product type(TypeEnum type) {
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

  public Product number(String number) {
    this.number = number;
    return this;
  }

  /**
   * Number of products if corresponds
   * @return number
  */
  @ApiModelProperty(value = "Number of products if corresponds")


  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Product balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Product Balance
   * @return balance
  */
  @ApiModelProperty(value = "Product Balance")

  @Valid

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Product limit(BigDecimal limit) {
    this.limit = limit;
    return this;
  }

  /**
   * Limit for a Credit Type Product
   * @return limit
  */
  @ApiModelProperty(value = "Limit for a Credit Type Product")

  @Valid

  public BigDecimal getLimit() {
    return limit;
  }

  public void setLimit(BigDecimal limit) {
    this.limit = limit;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.id, product.id) &&
        Objects.equals(this.type, product.type) &&
        Objects.equals(this.number, product.number) &&
        Objects.equals(this.balance, product.balance) &&
        Objects.equals(this.limit, product.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, number, balance, limit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
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

