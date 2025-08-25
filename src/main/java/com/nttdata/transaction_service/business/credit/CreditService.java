package com.nttdata.transaction_service.business.credit;

import com.nttdata.transaction_service.dto.credit.*;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<CreditResponseDTO> fetchGetCreditById(String clientId);

    Mono<CreditResponseDTO> fetchInsertCredit(CreditCreateDTO creditCreateDTO);

    Mono<CreditUpdateResponseDTO> fetchApplyPaymentToCredit(CreditPaymentDTO creditPaymentDTO, String idCredit);

    Mono<CreditUpdateResponseDTO> fetchApplyChargeToCredit(CreditChargeDTO creditChargeDTO, String idCredit);

}
