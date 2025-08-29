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
 * Person
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-29T17:52:57.098615300-05:00[America/Lima]")
public class Person   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("document")
  private String document;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    PERSONAL("personal"),
    
    PERSONAL_VIP("personal_vip"),
    
    BUSINESS("business"),
    
    BUSINESS_VIP("business_vip");

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
  private TypeEnum type = null;

  @JsonProperty("fullName")
  private String fullName = null;

  public Person id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique Holder Identifier
   * @return id
  */
  @ApiModelProperty(value = "Unique Holder Identifier")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public Person type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @ApiModelProperty(value = "")


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.id, person.id) &&
        Objects.equals(this.document, person.document) &&
        Objects.equals(this.type, person.type) &&
        Objects.equals(this.fullName, person.fullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, document, type, fullName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    document: ").append(toIndentedString(document)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
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

