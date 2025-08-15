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
 * TransactionPost
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TransactionPost  implements Serializable {
  private static final long serialVersionUID = 1L;

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
  private Person holder;

  @JsonProperty("signatory")
  private Person signatory;

  public TransactionPost product(Product product) {
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

  public TransactionPost type(TypeEnum type) {
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

  public TransactionPost amount(BigDecimal amount) {
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

  public TransactionPost createdDate(Date createdDate) {
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

  public TransactionPost client(Client client) {
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

  public TransactionPost holder(Person holder) {
    this.holder = holder;
    return this;
  }

  /**
   * Get holder
   * @return holder
  */
  @ApiModelProperty(value = "")

  @Valid

  public Person getHolder() {
    return holder;
  }

  public void setHolder(Person holder) {
    this.holder = holder;
  }

  public TransactionPost signatory(Person signatory) {
    this.signatory = signatory;
    return this;
  }

  /**
   * Get signatory
   * @return signatory
  */
  @ApiModelProperty(value = "")

  @Valid

  public Person getSignatory() {
    return signatory;
  }

  public void setSignatory(Person signatory) {
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
    TransactionPost transactionPost = (TransactionPost) o;
    return Objects.equals(this.product, transactionPost.product) &&
        Objects.equals(this.type, transactionPost.type) &&
        Objects.equals(this.amount, transactionPost.amount) &&
        Objects.equals(this.createdDate, transactionPost.createdDate) &&
        Objects.equals(this.client, transactionPost.client) &&
        Objects.equals(this.holder, transactionPost.holder) &&
        Objects.equals(this.signatory, transactionPost.signatory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, type, amount, createdDate, client, holder, signatory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionPost {\n");
    
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

