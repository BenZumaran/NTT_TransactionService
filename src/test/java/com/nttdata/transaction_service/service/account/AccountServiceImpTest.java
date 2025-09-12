package com.nttdata.transaction_service.service.account;

import com.nttdata.transaction_service.dto.account.AccountCardDTO;
import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Test Account Service")
@ExtendWith(MockitoExtension.class)
class AccountServiceImpTest {

    @Mock
    WebClient webClient;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @Spy
    AccountClient accountClient;

    @InjectMocks
    AccountServiceImp accountService;

    Mono<AccountResponseCreateDTO> accountResponseDTOMono;

    @BeforeEach
    void setUp() {
        accountResponseDTOMono = Mono.just(AccountResponseCreateDTO
                .builder()
                .id("accountid")
                .accountNumber("accountnumber")
                .balance(0)
                .active(true)
                .accountType("anytype")
                .allowedDayOfMonth(15)
                .authorizedSigners(new String[]{})
                .creationDate(LocalDate.now())
                .holderDocument("Holderdoc")
                .interbankNumber("acountplusnumbers")
                .interestRate(2)
                .linkedCard(AccountCardDTO.builder()
                        .id("cardid")
                        .build())
                .maintenanceFee(3)
                .monthlyMovementLimit(50)

                .build());
    }

    @Test
    void fetchGetAccountById() {
        when(accountClient.accountWebClient()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/" + anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(AccountResponseCreateDTO.class)).thenReturn(accountResponseDTOMono);

        StepVerifier.create(accountService.fetchGetAccountById("anyid"))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        when(responseSpec.bodyToMono(AccountResponseCreateDTO.class)).thenReturn(Mono.empty());

        StepVerifier.create(accountService.fetchGetAccountById("anyid"))
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();

        verify(accountClient, times(2)).accountWebClient();
        verify(webClient, times(2)).get();
        verify(requestHeadersUriSpec, times(2)).uri("/" + anyString());
        verify(requestHeadersSpec, times(2)).retrieve();
        verify(responseSpec, times(2)).bodyToMono(AccountResponseCreateDTO.class);

    }
}