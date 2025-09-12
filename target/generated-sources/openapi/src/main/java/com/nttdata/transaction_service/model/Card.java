package com.nttdata.transaction_service.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Card
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-11T14:02:40.114452100-05:00[America/Lima]")
public class Card   {
  @JsonProperty("id")
  private String id;

  /**
   * Type of card
   */
  public enum CardTypeEnum {
    DEBIT("DEBIT"),
    
    CREDIT("CREDIT");

    private String value;

    CardTypeEnum(String value) {
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
    public static CardTypeEnum fromValue(String value) {
      for (CardTypeEnum b : CardTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("cardType")
  private CardTypeEnum cardType = null;

  /**
   * Card brand
   */
  public enum BrandEnum {
    VISA("VISA"),
    
    MASTERCARD("MASTERCARD"),
    
    AMEX("AMEX"),
    
    OTHER("OTHER");

    private String value;

    BrandEnum(String value) {
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
    public static BrandEnum fromValue(String value) {
      for (BrandEnum b : BrandEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("brand")
  private BrandEnum brand = null;

  @JsonProperty("cardNumber")
  private String cardNumber = null;

  /**
   * Operational status of the card
   */
  public enum StatusEnum {
    ACTIVE("active"),
    
    BLOCKED("blocked"),
    
    EXPIRED("expired"),
    
    CANCELLED("cancelled");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("status")
  private StatusEnum status = null;

  @JsonProperty("isVirtual")
  private Boolean isVirtual = null;

  @JsonProperty("creationDate")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime creationDate = null;

  public Card id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique card ID
   * @return id
  */
  @ApiModelProperty(value = "Unique card ID")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Card cardType(CardTypeEnum cardType) {
    this.cardType = cardType;
    return this;
  }

  /**
   * Type of card
   * @return cardType
  */
  @ApiModelProperty(value = "Type of card")


  public CardTypeEnum getCardType() {
    return cardType;
  }

  public void setCardType(CardTypeEnum cardType) {
    this.cardType = cardType;
  }

  public Card brand(BrandEnum brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Card brand
   * @return brand
  */
  @ApiModelProperty(value = "Card brand")


  public BrandEnum getBrand() {
    return brand;
  }

  public void setBrand(BrandEnum brand) {
    this.brand = brand;
  }

  public Card cardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
    return this;
  }

  /**
   * Card number
   * @return cardNumber
  */
  @ApiModelProperty(value = "Card number")


  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Card status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Operational status of the card
   * @return status
  */
  @ApiModelProperty(value = "Operational status of the card")


  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Card isVirtual(Boolean isVirtual) {
    this.isVirtual = isVirtual;
    return this;
  }

  /**
   * Indicates if the card is virtual
   * @return isVirtual
  */
  @ApiModelProperty(value = "Indicates if the card is virtual")


  public Boolean getIsVirtual() {
    return isVirtual;
  }

  public void setIsVirtual(Boolean isVirtual) {
    this.isVirtual = isVirtual;
  }

  public Card creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Fecha de creación
   * @return creationDate
  */
  @ApiModelProperty(value = "Fecha de creación")

  @Valid

  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return Objects.equals(this.id, card.id) &&
        Objects.equals(this.cardType, card.cardType) &&
        Objects.equals(this.brand, card.brand) &&
        Objects.equals(this.cardNumber, card.cardNumber) &&
        Objects.equals(this.status, card.status) &&
        Objects.equals(this.isVirtual, card.isVirtual) &&
        Objects.equals(this.creationDate, card.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cardType, brand, cardNumber, status, isVirtual, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Card {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cardType: ").append(toIndentedString(cardType)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    cardNumber: ").append(toIndentedString(cardNumber)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isVirtual: ").append(toIndentedString(isVirtual)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
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

