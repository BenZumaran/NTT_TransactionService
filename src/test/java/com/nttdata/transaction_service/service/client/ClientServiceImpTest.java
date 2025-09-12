package com.nttdata.transaction_service.service.client;

import com.nttdata.transaction_service.dto.client.ClientAddressDTO;
import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Test Client Service")
@ExtendWith(MockitoExtension.class)
@Slf4j
class ClientServiceImpTest {

    @Mock
    ReactiveCircuitBreaker reactiveCircuitBreaker;

    @Mock
    WebClient webClient;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @Spy
    ClientClient client;

    @InjectMocks
    ClientServiceImp clientService;

    Mono<ClientResponseDTO> clientResponseDTOMono;

    @BeforeEach
    void setUp() {

        clientResponseDTOMono = Mono.just(
                ClientResponseDTO.builder()
                        .id("clientid")
                        .type("personal")
                        .email("clientemail")
                        .documentNumber("12345678")
                        .phone("987654321")
                        .active("ACTIVE")
                        .segment("NORMAL")
                        .address(
                                ClientAddressDTO.builder()
                                        .country("Peru")
                                        .city("Lima")
                                        .line1("Any Address")
                                        .build()
                        )
                        .build()
        );
    }

    @Test
    @DisplayName("Get Client By Id")
    void fetchGetClientById() {
        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenReturn(clientResponseDTOMono);
        when(client.clientWebClient()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/" + anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ClientResponseDTO.class)).thenReturn(clientResponseDTOMono);

        StepVerifier.create(clientService.fetchGetClientById("anyid"))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenReturn(Mono.empty());

        StepVerifier.create(clientService.fetchGetClientById("anyid"))
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();

        when(responseSpec.bodyToMono(ClientResponseDTO.class)).thenReturn(Mono.error(new IllegalArgumentException()));

        StepVerifier.create(clientService.fetchGetClientById("anyid"))
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();

        verify(client, times(3)).clientWebClient();
        verify(webClient, times(3)).get();
        verify(requestHeadersUriSpec, times(3)).uri("/" + anyString());
        verify(requestHeadersSpec, times(3)).retrieve();
        verify(responseSpec, times(3)).bodyToMono(ClientResponseDTO.class);
        verify(reactiveCircuitBreaker, times(3)).run(any(Mono.class), any());

    }

    @Test
    @DisplayName("Get Client By Document")
    void fetchGetClientByDocument() {
        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenReturn(clientResponseDTOMono);
        when(client.clientWebClient()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/document/" + anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ClientResponseDTO.class)).thenReturn(clientResponseDTOMono);

        StepVerifier.create(clientService.fetchGetClientByDocument("anydocument"))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenReturn(Mono.empty());
        StepVerifier.create(clientService.fetchGetClientByDocument("anydocument"))
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();

        when(reactiveCircuitBreaker.run(any(Mono.class), any())).thenThrow(new IllegalArgumentException());
        Assertions.assertThrows(IllegalArgumentException.class, () -> clientService.fetchGetClientByDocument("anydocument"));

        verify(client, times(3)).clientWebClient();
        verify(webClient, times(3)).get();
        verify(requestHeadersUriSpec, times(3)).uri("/document/" + anyString());
        verify(requestHeadersSpec, times(3)).retrieve();
        verify(responseSpec, times(3)).bodyToMono(ClientResponseDTO.class);
        verify(reactiveCircuitBreaker, times(3)).run(any(Mono.class), any());

    }

}