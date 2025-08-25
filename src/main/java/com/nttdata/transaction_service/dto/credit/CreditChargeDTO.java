package com.nttdata.transaction_service.dto.credit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreditChargeDTO {
    private double amount;
    private String merchant;
    private String note;

    @JsonCreator
    public CreditChargeDTO(
            @JsonProperty("amount") double amount,
            @JsonProperty("merchant") String merchant,
            @JsonProperty("note") String note
    ){
        this.amount = amount;
        this.merchant = merchant;
        this.note = note;
    }

}


/*
{
  "amount": 0.01,
  "merchant": "string",
  "note": "string"
}
 */