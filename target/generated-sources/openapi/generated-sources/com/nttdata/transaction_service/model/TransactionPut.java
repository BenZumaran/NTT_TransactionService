package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nttdata.transaction_service.model.ClientNullable;
import com.nttdata.transaction_service.model.Person;
import com.nttdata.transaction_service.model.ProductNullable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionPut
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TransactionPut  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("product")
  private JsonNullable<ProductNullable> product = JsonNullable.undefined();

  /**
   * Type of transaction
   */
  public enum TypeEnum {
    DEPOSIT("deposit"),
    
    WITHDRAWAL("withdrawal"),
    
    PAYMENT("payment"),
    
    PURCHASE("purchase"),
    
    CHARGE("charge");

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
      return null;
    }
  }

  @JsonProperty("type")
  private JsonNullable<TypeEnum> type = JsonNullable.undefined();

  @JsonProperty("amount")
  private JsonNullable<BigDecimal> amount = JsonNullable.undefined();

  @JsonProperty("client")
  private JsonNullable<ClientNullable> client = JsonNullable.undefined();

  @JsonProperty("holder")
  private JsonNullable<Person> holder = JsonNullable.undefined();

  @JsonProperty("signatory")
  private JsonNullable<Person> signatory = JsonNullable.undefined();

  public TransactionPut id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Transaction unique id
   * @return id
  */
  @ApiModelProperty(value = "Transaction unique id")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TransactionPut product(ProductNullable product) {
    this.product = JsonNullable.of(product);
    return this;
  }

  /**
   * Get product
   * @return product
  */
  @ApiModelProperty(value = "")

  @Valid

  public JsonNullable<ProductNullable> getProduct() {
    return product;
  }

  public void setProduct(JsonNullable<ProductNullable> product) {
    this.product = product;
  }

  public TransactionPut type(TypeEnum type) {
    this.type = JsonNullable.of(type);
    return this;
  }

  /**
   * Type of transaction
   * @return type
  */
  @ApiModelProperty(value = "Type of transaction")


  public JsonNullable<TypeEnum> getType() {
    return type;
  }

  public void setType(JsonNullable<TypeEnum> type) {
    this.type = type;
  }

  public TransactionPut amount(BigDecimal amount) {
    this.amount = JsonNullable.of(amount);
    return this;
  }

  /**
   * Amount to change according transaction type
   * @return amount
  */
  @ApiModelProperty(value = "Amount to change according transaction type")

  @Valid

  public JsonNullable<BigDecimal> getAmount() {
    return amount;
  }

  public void setAmount(JsonNullable<BigDecimal> amount) {
    this.amount = amount;
  }

  public TransactionPut client(ClientNullable client) {
    this.client = JsonNullable.of(client);
    return this;
  }

  /**
   * Get client
   * @return client
  */
  @ApiModelProperty(value = "")

  @Valid

  public JsonNullable<ClientNullable> getClient() {
    return client;
  }

  public void setClient(JsonNullable<ClientNullable> client) {
    this.client = client;
  }

  public TransactionPut holder(Person holder) {
    this.holder = JsonNullable.of(holder);
    return this;
  }

  /**
   * Get holder
   * @return holder
  */
  @ApiModelProperty(value = "")

  @Valid

  public JsonNullable<Person> getHolder() {
    return holder;
  }

  public void setHolder(JsonNullable<Person> holder) {
    this.holder = holder;
  }

  public TransactionPut signatory(Person signatory) {
    this.signatory = JsonNullable.of(signatory);
    return this;
  }

  /**
   * Get signatory
   * @return signatory
  */
  @ApiModelProperty(value = "")

  @Valid

  public JsonNullable<Person> getSignatory() {
    return signatory;
  }

  public void setSignatory(JsonNullable<Person> signatory) {
    this.signatory = signatory;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionPut transactionPut = (TransactionPut) o;
    return Objects.equals(this.id, transactionPut.id) &&
        Objects.equals(this.product, transactionPut.product) &&
        Objects.equals(this.type, transactionPut.type) &&
        Objects.equals(this.amount, transactionPut.amount) &&
        Objects.equals(this.client, transactionPut.client) &&
        Objects.equals(this.holder, transactionPut.holder) &&
        Objects.equals(this.signatory, transactionPut.signatory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, product, type, amount, client, holder, signatory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionPut {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    client: ").append(toIndentedString(client)).append("\n");
    sb.append("    holder: ").append(toIndentedString(holder)).append("\n");
    sb.append("    signatory: ").append(toIndentedString(signatory)).append("\n");
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

