package com.nttdata.transaction_service.dto.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.transaction_service.dto.account.AccountCardDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreditCreateDTO {
    private String customerId;
    private String type;
    private double limit;
    private double interestAnnual;
    private LocalDate dueDate;
    private CreditCardDTO card;


    public  CreditCreateDTO(
            @JsonProperty("customerId") String customerId,
            @JsonProperty("type") String type,
            @JsonProperty("limit") double limit,
            @JsonProperty("interestAnnual") double interestAnnual,
            @JsonProperty("dueDate") LocalDate dueDate,
            @JsonProperty("card") CreditCardDTO card){
               this.customerId = customerId;
               this.type = type;
               this.limit = limit;
               this.interestAnnual = interestAnnual;
               this.dueDate = dueDate;
               this.card = card;
    }

}

/*
{
  "customerId": "string",
  "type": "PERSONAL",
  "limit": 5000,
  "interestAnnual": 128,
  "dueDate": "2025-08-18",
  "card": {
    "id": "string",
    "last4": "3644",
    "brand": "VISA"
  }
}
 */