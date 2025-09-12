package com.nttdata.transaction_service.service.credit;

import com.nttdata.transaction_service.dto.credit.CreditCardDTO;
import com.nttdata.transaction_service.dto.credit.CreditResponseDTO;
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
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Test Credit Service")
@ExtendWith(MockitoExtension.class)
class CreditServiceImpTest {

    @Mock
    WebClient webClient;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @Spy
    CreditClient creditClient;

    @InjectMocks
    CreditServiceImp creditService;

    Mono<CreditResponseDTO> creditResponseDTOMono;


    @BeforeEach
    void setUp() {
        creditResponseDTOMono = Mono.just(
                CreditResponseDTO.builder()
                        .id("creditId")
                        .balance(10)
                        .card(
                                CreditCardDTO.builder()
                                        .id("cardid")
                                        .brand("VISA")
                                        .last4("7890")
                                        .build()
                        )
                        .createdAt(LocalDateTime.now())
                        .customerId("customerid")
                        .limit(100)
                        .type("credit_card")
                        .dueDate(LocalDate.now())
                        .interestAnnual(50)
                        .status("ACTIVE")
                        .build()
        );

    }

    @Test
    void fetchGetCreditById() {
        when(creditClient.creditWebClient()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/" + anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CreditResponseDTO.class)).thenReturn(creditResponseDTOMono);

        StepVerifier.create(creditService.fetchGetCreditById("anyid"))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        when(responseSpec.bodyToMono(CreditResponseDTO.class)).thenReturn(Mono.empty());

        StepVerifier.create(creditService.fetchGetCreditById("anyid"))
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();

        verify(creditClient, times(2)).creditWebClient();
        verify(webClient, times(2)).get();
        verify(requestHeadersUriSpec, times(2)).uri("/" + anyString());
        verify(requestHeadersSpec, times(2)).retrieve();
        verify(responseSpec, times(2)).bodyToMono(CreditResponseDTO.class);

    }
}