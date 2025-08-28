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
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionPost
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-27T20:13:55.820311800-05:00[America/Lima]")
public class TransactionPost   {
  @JsonProperty("sender")
  private Product sender;

  @JsonProperty("receiver")
  private Product receiver;

  @JsonProperty("type")
  private TransactionType type;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("holder")
  private Person holder = null;

  @JsonProperty("signatory")
  private Person signatory = null;

  public TransactionPost sender(Product sender) {
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

  public TransactionPost receiver(Product receiver) {
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

  public TransactionPost type(TransactionType type) {
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
    return Objects.equals(this.sender, transactionPost.sender) &&
        Objects.equals(this.receiver, transactionPost.receiver) &&
        Objects.equals(this.type, transactionPost.type) &&
        Objects.equals(this.amount, transactionPost.amount) &&
        Objects.equals(this.holder, transactionPost.holder) &&
        Objects.equals(this.signatory, transactionPost.signatory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sender, receiver, type, amount, holder, signatory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionPost {\n");
    
    sb.append("    sender: ").append(toIndentedString(sender)).append("\n");
    sb.append("    receiver: ").append(toIndentedString(receiver)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

