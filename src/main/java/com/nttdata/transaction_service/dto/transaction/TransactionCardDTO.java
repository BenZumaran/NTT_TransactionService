package com.nttdata.transaction_service.dto.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
public class TransactionCardDTO {
    private String id;
    private String brand;
    private String cardType;
    private String cardNumber;
    private String status;
    private boolean isVirtual;
    private OffsetDateTime creationDate;

    @JsonCreator
    public TransactionCardDTO(
            @JsonProperty("id") String id,
            @JsonProperty("brand") String brand,
            @JsonProperty("cardType") String cardType,
            @JsonProperty("cardNumber") String cardNumber,
            @JsonProperty("status") String status,
            @JsonProperty("isVirtual") boolean isVirtual,
            @JsonProperty("creationDate") OffsetDateTime creationDate
    ) {
        this.id = id;
        this.brand = brand;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.status = status;
        this.isVirtual = isVirtual;
        this.creationDate = creationDate;
    }


}

/*
  "card": {
    "id": "string",
    "cardType": "DEBIT",
    "brand": "VISA",
    "cardNumber": "string",
    "status": "active",
    "isVirtual": true,
    "creationDate": "2025-09-09T02:47:09.491Z"
  }
 */