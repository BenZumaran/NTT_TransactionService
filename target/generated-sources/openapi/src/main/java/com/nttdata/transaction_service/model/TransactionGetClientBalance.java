package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.TransactionGet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionGetClientBalance
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-29T17:52:57.098615300-05:00[America/Lima]")
public class TransactionGetClientBalance   {
  @JsonProperty("product")
  private Product product;

  @JsonProperty("transactions")
  @Valid
  private List<TransactionGet> transactions = null;

  public TransactionGetClientBalance product(Product product) {
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

  public TransactionGetClientBalance transactions(List<TransactionGet> transactions) {
    this.transactions = transactions;
    return this;
  }

  public TransactionGetClientBalance addTransactionsItem(TransactionGet transactionsItem) {
    if (this.transactions == null) {
      this.transactions = new ArrayList<>();
    }
    this.transactions.add(transactionsItem);
    return this;
  }

  /**
   * Transactions relative to product
   * @return transactions
  */
  @ApiModelProperty(value = "Transactions relative to product")

  @Valid

  public List<TransactionGet> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<TransactionGet> transactions) {
    this.transactions = transactions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionGetClientBalance transactionGetClientBalance = (TransactionGetClientBalance) o;
    return Objects.equals(this.product, transactionGetClientBalance.product) &&
        Objects.equals(this.transactions, transactionGetClientBalance.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionGetClientBalance {\n");
    
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
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

