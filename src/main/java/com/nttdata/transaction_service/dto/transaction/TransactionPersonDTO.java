package com.nttdata.transaction_service.dto.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class TransactionPersonDTO {
    private String id;
    private String document;

    @JsonCreator
    public TransactionPersonDTO(
            @JsonProperty("id") String id,
            @JsonProperty("document") String document
    ) {
        if (id != null)
            this.id = id;
        if (document != null)
            this.document = document;
    }

}



/*

  "holder": {
    "id": "string",
    "document": "59216150",
    "type": "personal",
    "fullName": "string"
  },
  "signatory": {
    "id": "string",
    "document": "47824005",
    "type": "personal",
    "fullName": "string"
  },


 */