package com.nttdata.transaction_service.business.credit;

import com.nttdata.transaction_service.dto.credit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImp implements CreditService{

    @Autowired
    CreditClient creditClient;

    @Override
    public Mono<CreditResponseDTO> fetchGetCreditById(String clientId) {
        return creditClient.creditWebClient().get().uri(clientId)
                .retrieve().bodyToMono(CreditResponseDTO.class);
    }

    @Override
    public Mono<CreditResponseDTO> fetchInsertCredit(CreditCreateDTO creditCreateDTO) {
        return creditClient.creditWebClient().post().uri("").bodyValue(creditCreateDTO)
                .header("Content-Type", "application/json")
                .retrieve().bodyToMono(CreditResponseDTO.class);
    }

    @Override
    public Mono<CreditUpdateResponseDTO> fetchApplyPaymentToCredit(CreditPaymentDTO creditPaymentDTO, String idCredit) {
        return creditClient.creditWebClient().post().uri("/"+idCredit+"/payments")
                .bodyValue(creditPaymentDTO)
                .header("Content-Type","application/json")
                .retrieve().bodyToMono(CreditUpdateResponseDTO.class);
    }

    @Override
    public Mono<CreditUpdateResponseDTO> fetchApplyChargeToCredit(CreditChargeDTO creditChargeDTO, String idCredit) {
        return creditClient.creditWebClient().post().uri("/"+idCredit+"/charges")
                .bodyValue(creditChargeDTO)
                .header("Content-Type","application/json")
                .retrieve().bodyToMono(CreditUpdateResponseDTO.class);
    }
}
