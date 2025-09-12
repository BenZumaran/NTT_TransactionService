package com.nttdata.transaction_service.dto.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
public class TransactionPostDTO {

    private TransactionProductDTO sender;
    private TransactionProductDTO receiver;
    private TransactionPersonDTO holder;
    private TransactionPersonDTO signatory;
    private String type;
    private double amount;
    private TransactionCardDTO card;

    @JsonCreator
    public TransactionPostDTO(
            @JsonProperty("sender") TransactionProductDTO sender,
            @JsonProperty("receiver") TransactionProductDTO receiver,
            @JsonProperty("holder") TransactionPersonDTO holder,
            @JsonProperty("signatory") TransactionPersonDTO signatory,
            @JsonProperty("type") String type,
            @JsonProperty("amount") double amount,
            @JsonProperty("card") TransactionCardDTO card
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.holder = holder;
        this.signatory = signatory;
        this.type = type;
        this.amount = amount;
        this.card = card;
    }

}



/*
{
  "type": "deposit",
  "amount": 0,
}
 */