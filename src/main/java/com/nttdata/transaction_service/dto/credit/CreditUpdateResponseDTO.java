package com.nttdata.transaction_service.dto.credit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreditUpdateResponseDTO {
    private double limit;
    private double balance;
    private double available;

    @JsonCreator
    public CreditUpdateResponseDTO(
            @JsonProperty("limit") double limit,
            @JsonProperty("balance") double balance,
            @JsonProperty("available") double available){
        this.limit = limit;
        this.balance = balance;
        this.available = available;
    }

}

/*
{
  "limit": 0,
  "balance": 0,
  "available": 0
}
 */
