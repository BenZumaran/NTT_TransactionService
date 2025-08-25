package com.nttdata.transaction_service.business.account;

import com.nttdata.transaction_service.dto.account.AccountCreateDTO;
import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import reactor.core.publisher.Mono;


public interface AccountService {

    Mono<AccountResponseCreateDTO> fetchGetAccountById(String accountId);

    Mono<AccountResponseCreateDTO> fetchInsertAccount(AccountResponseCreateDTO accountCreateDTO);

    Mono<AccountResponseCreateDTO> fetchUpdateAccount(AccountResponseCreateDTO accountUpdateDTO);

}
