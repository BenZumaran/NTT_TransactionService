package com.nttdata.transaction_service.service.credit;

import com.nttdata.transaction_service.dto.credit.CreditResponseDTO;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<CreditResponseDTO> fetchGetCreditById(String clientId);

}
