package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ProductNullable
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ProductNullable  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("number")
  private JsonNullable<String> number = JsonNullable.undefined();

  /**
   * Type of product
   */
  public enum TypeEnum {
    ACCOUNT("account"),
    
    CREDIT("credit");

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

  @JsonProperty("balance")
  private BigDecimal balance;

  @JsonProperty("limit")
  private BigDecimal limit;

  public ProductNullable id(String id) {
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

  public ProductNullable number(String number) {
    this.number = JsonNullable.of(number);
    return this;
  }

  /**
   * Number of products if corresponds
   * @return number
  */
  @ApiModelProperty(value = "Number of products if corresponds")


  public JsonNullable<String> getNumber() {
    return number;
  }

  public void setNumber(JsonNullable<String> number) {
    this.number = number;
  }

  public ProductNullable type(TypeEnum type) {
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

  public ProductNullable balance(BigDecimal balance) {
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

  public ProductNullable limit(BigDecimal limit) {
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
    ProductNullable productNullable = (ProductNullable) o;
    return Objects.equals(this.id, productNullable.id) &&
        Objects.equals(this.number, productNullable.number) &&
        Objects.equals(this.type, productNullable.type) &&
        Objects.equals(this.balance, productNullable.balance) &&
        Objects.equals(this.limit, productNullable.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, number, type, balance, limit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductNullable {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

