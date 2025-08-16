package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nttdata.transaction_service.model.Client;
import com.nttdata.transaction_service.model.Person;
import com.nttdata.transaction_service.model.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionGet
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TransactionGet  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("number")
  private Integer number;

  @JsonProperty("product")
  private Product product;

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
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("createdDate")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private Date createdDate;

  @JsonProperty("client")
  private Client client;

  @JsonProperty("holder")
  private JsonNullable<Person> holder = JsonNullable.undefined();

  @JsonProperty("signatory")
  private JsonNullable<Person> signatory = JsonNullable.undefined();

  public TransactionGet id(String id) {
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

  public TransactionGet number(Integer number) {
    this.number = number;
    return this;
  }

  /**
   * Transaction number
   * @return number
  */
  @ApiModelProperty(value = "Transaction number")


  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public TransactionGet product(Product product) {
    this.product = product;
    return this;
  }

  /**
   * Get product
   * @return product
  */
  @ApiModelProperty(value = "")

  @Valid

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public TransactionGet type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type of transaction
   * @return type
  */
  @ApiModelProperty(value = "Type of transaction")


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public TransactionGet amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Amount to change according transaction type
   * @return amount
  */
  @ApiModelProperty(value = "Amount to change according transaction type")

  @Valid

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public TransactionGet createdDate(Date createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * Transaction date
   * @return createdDate
  */
  @ApiModelProperty(value = "Transaction date")

  @Valid

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public TransactionGet client(Client client) {
    this.client = client;
    return this;
  }

  /**
   * Get client
   * @return client
  */
  @ApiModelProperty(value = "")

  @Valid

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public TransactionGet holder(Person holder) {
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

  public TransactionGet signatory(Person signatory) {
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
    TransactionGet transactionGet = (TransactionGet) o;
    return Objects.equals(this.id, transactionGet.id) &&
        Objects.equals(this.number, transactionGet.number) &&
        Objects.equals(this.product, transactionGet.product) &&
        Objects.equals(this.type, transactionGet.type) &&
        Objects.equals(this.amount, transactionGet.amount) &&
        Objects.equals(this.createdDate, transactionGet.createdDate) &&
        Objects.equals(this.client, transactionGet.client) &&
        Objects.equals(this.holder, transactionGet.holder) &&
        Objects.equals(this.signatory, transactionGet.signatory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, number, product, type, amount, createdDate, client, holder, signatory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionGet {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
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

