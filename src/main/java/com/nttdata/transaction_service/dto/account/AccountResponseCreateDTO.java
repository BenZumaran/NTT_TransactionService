package com.nttdata.transaction_service.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountResponseCreateDTO {
    private String id;
    private String accountNumber;
    private String interbankNumber;
    private String holderDocument;
    private String[] authorizedSigners;
    private String accountType;
    private double balance;
    private double interestRate;
    private int monthlyMovementLimit;
    private double maintenanceFee;
    private int allowedDayOfMonth;
    private LocalDate creationDate = LocalDate.now();
    private boolean active = true;
    private AccountCardDTO linkedCard;

    @JsonCreator
    public AccountResponseCreateDTO(
            @JsonProperty("id") String id,
            @JsonProperty("accountNumber") String accountNumber,
            @JsonProperty("interbankNumber") String interbankNumber,
            @JsonProperty("holderDocument") String holderDocument,
            @JsonProperty("authorizedSigners") String[] authorizedSigners,
            @JsonProperty("accountType") String accountType,
            @JsonProperty("balance") double balance,
            @JsonProperty("interestRate") double interestRate,
            @JsonProperty("monthlyMovementLimit") int monthlyMovementLimit,
            @JsonProperty("maintenanceFee") double maintenanceFee,
            @JsonProperty("allowedDayOfMonth") int allowedDayOfMonth,
            @JsonProperty("creationDate") LocalDate creationDate,
            @JsonProperty("active") boolean active,
            @JsonProperty("linkedCar") AccountCardDTO linkedCard
    ) {
         this.id = id;
         this.accountNumber = accountNumber;
         this.interbankNumber = interbankNumber;
         this.holderDocument = holderDocument;
         this.authorizedSigners = authorizedSigners;
         this.accountType = accountType;
         this.balance = balance;
         this.interestRate = interestRate;
         this.monthlyMovementLimit = monthlyMovementLimit;
         this.maintenanceFee = maintenanceFee;
         this.allowedDayOfMonth = allowedDayOfMonth;
         this.creationDate = creationDate;
         this.active = active;
         this.linkedCard = linkedCard;
    }

    /*
    {
  "id": "string",
  "accountNumber": "string",
  "interbankNumber": "string",
  "holderDocument": "string",
  "authorizedSigners": [
    "string"
  ],
  "accountType": "SAVINGS",
  "balance": 0,
  "interestRate": 0,
  "monthlyMovementLimit": 0,
  "maintenanceFee": 0,
  "allowedDayOfMonth": 0,
  "creationDate": "2025-08-23",
  "active": true,
  "linkedCard": {
    "id": "string"
  }
}
     */
}
