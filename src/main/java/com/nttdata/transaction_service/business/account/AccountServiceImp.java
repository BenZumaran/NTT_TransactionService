package com.nttdata.transaction_service.business.account;


import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    AccountClient accountClient;


    @Override
    public Mono<AccountResponseCreateDTO> fetchGetAccountById(String accountId) {
        return accountClient.accountWebClient().get()
                .uri("/"+accountId).retrieve().bodyToMono(AccountResponseCreateDTO.class);
    }


}
