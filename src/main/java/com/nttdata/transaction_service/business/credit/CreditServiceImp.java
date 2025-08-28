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
}
