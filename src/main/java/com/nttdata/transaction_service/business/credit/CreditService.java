package com.nttdata.transaction_service.business.credit;

import com.nttdata.transaction_service.dto.credit.*;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<CreditResponseDTO> fetchGetCreditById(String clientId);

}
