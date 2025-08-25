package com.nttdata.transaction_service.dto.credit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreditPaymentDTO {
    private double amount;
    private String note;

    @JsonCreator
    public CreditPaymentDTO(
            @JsonProperty("amount") double amount,
            @JsonProperty("note") String note){
        this.amount = amount;
        this.note = note;
    }

}
