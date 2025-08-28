package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.nttdata.transaction_service.model.Person;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.TransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionGet
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-27T20:13:55.820311800-05:00[America/Lima]")
public class TransactionGet   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("number")
  private Integer number;

  @JsonProperty("sender")
  private Product sender;

  @JsonProperty("receiver")
  private Product receiver;

  @JsonProperty("type")
  private TransactionType type;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("createdDate")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdDate;

  @JsonProperty("holder")
  private Person holder = null;

  @JsonProperty("signatory")
  private Person signatory = null;

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

  public TransactionGet sender(Product sender) {
    this.sender = sender;
    return this;
  }

  /**
   * Get sender
   * @return sender
  */
  @ApiModelProperty(value = "")

  @Valid

  public Product getSender() {
    return sender;
  }

  public void setSender(Product sender) {
    this.sender = sender;
  }

  public TransactionGet receiver(Product receiver) {
    this.receiver = receiver;
    return this;
  }

  /**
   * Get receiver
   * @return receiver
  */
  @ApiModelProperty(value = "")

  @Valid

  public Product getReceiver() {
    return receiver;
  }

  public void setReceiver(Product receiver) {
    this.receiver = receiver;
  }

  public TransactionGet type(TransactionType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @ApiModelProperty(value = "")

  @Valid

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
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

  public TransactionGet createdDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * Transaction date
   * @return createdDate
  */
  @ApiModelProperty(value = "Transaction date")

  @Valid

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public TransactionGet holder(Person holder) {
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

  public TransactionGet signatory(Person signatory) {
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
    TransactionGet transactionGet = (TransactionGet) o;
    return Objects.equals(this.id, transactionGet.id) &&
        Objects.equals(this.number, transactionGet.number) &&
        Objects.equals(this.sender, transactionGet.sender) &&
        Objects.equals(this.receiver, transactionGet.receiver) &&
        Objects.equals(this.type, transactionGet.type) &&
        Objects.equals(this.amount, transactionGet.amount) &&
        Objects.equals(this.createdDate, transactionGet.createdDate) &&
        Objects.equals(this.holder, transactionGet.holder) &&
        Objects.equals(this.signatory, transactionGet.signatory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, number, sender, receiver, type, amount, createdDate, holder, signatory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionGet {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    sender: ").append(toIndentedString(sender)).append("\n");
    sb.append("    receiver: ").append(toIndentedString(receiver)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
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

