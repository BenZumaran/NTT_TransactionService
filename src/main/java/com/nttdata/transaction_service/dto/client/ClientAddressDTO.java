package com.nttdata.transaction_service.dto.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientAddressDTO {

    private String line1;
    private String city;
    private String country;

    @JsonCreator
    public ClientAddressDTO(
            @JsonProperty("line1") String line1,
            @JsonProperty("city") String city,
            @JsonProperty("country") String country) {
        this.line1 = line1;
        this.city = city;
        this.country = country;
    }


}
