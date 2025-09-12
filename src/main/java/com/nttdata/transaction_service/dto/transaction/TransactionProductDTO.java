package com.nttdata.transaction_service.dto.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
public class TransactionProductDTO {
    public static final Map<String, String> TYPE_MAP = Map.of(
            "PERSONAL", "personal_credit",
            "BUSINESS", "business_credit",
            "CREDIT_CARD", "credit_card"
    );
    private String id;
    private String type;
    private String number;
    private double balance;
    private double limit;

    @JsonCreator
    public TransactionProductDTO(
            @JsonProperty("id") String id,
            @JsonProperty("type") String type,
            @JsonProperty("number") String number,
            @JsonProperty("balance") double balance,
            @JsonProperty("limit") double limit
    ) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.balance = balance;
        this.limit = limit;
    }

}

/*
//personal_credit, business_credit, credit_card
"sender": {
    "id": "string",
    "type": "savings_account",
    "number": "string",
    "balance": 0,
    "limit": 0
  },
  "receiver": {
    "id": "string",
    "type": "savings_account",
    "number": "string",
    "balance": 0,
    "limit": 0
  },

 */
