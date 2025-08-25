package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Person
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-24T19:57:01.660543300-05:00[America/Lima]")
public class Person   {
  @JsonProperty("document")
  private String document;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("signature")
  private String signature;

  public Person document(String document) {
    this.document = document;
    return this;
  }

  /**
   * Document number (8, 9, or 11 digits)
   * @return document
  */
  @ApiModelProperty(value = "Document number (8, 9, or 11 digits)")

@Pattern(regexp="^\\d{8,11}$") 
  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public Person fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Full responsible name
   * @return fullName
  */
  @ApiModelProperty(value = "Full responsible name")


  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Person signature(String signature) {
    this.signature = signature;
    return this;
  }

  /**
   * URL to signature image
   * @return signature
  */
  @ApiModelProperty(value = "URL to signature image")


  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.document, person.document) &&
        Objects.equals(this.fullName, person.fullName) &&
        Objects.equals(this.signature, person.signature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(document, fullName, signature);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("    document: ").append(toIndentedString(document)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
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

