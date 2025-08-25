package com.nttdata.transaction_service.business.account;


import com.nttdata.transaction_service.dto.account.AccountCreateDTO;
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

    @Override
    public Mono<AccountResponseCreateDTO> fetchInsertAccount(AccountResponseCreateDTO accountCreateDTO) {
        return accountClient.accountWebClient().post().uri("")
                .bodyValue(accountCreateDTO)
                .header("Content-Type", "application/json")
                .retrieve().bodyToMono(AccountResponseCreateDTO.class);
    }

    @Override
    public Mono<AccountResponseCreateDTO> fetchUpdateAccount(AccountResponseCreateDTO accountUpdateDTO) {
        return accountClient.accountWebClient().put().uri("/"+accountUpdateDTO.getId())
                .bodyValue(accountUpdateDTO)
                .header("Content-Type", "application/json")
                .retrieve().bodyToMono(AccountResponseCreateDTO.class);
    }


}
