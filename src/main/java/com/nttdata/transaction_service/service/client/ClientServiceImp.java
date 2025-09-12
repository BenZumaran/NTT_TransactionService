package com.nttdata.transaction_service.service.client;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ClientServiceImp implements ClientService {

    @Autowired
    ClientClient client;

    @Autowired
    ReactiveCircuitBreaker reactiveCircuitBreaker;

    @Override
    public Mono<ClientResponseDTO> fetchGetClientById(String id) {
        return reactiveCircuitBreaker.run(
                client.clientWebClient().get()
                        .uri("/" + id).retrieve().bodyToMono(ClientResponseDTO.class),
                throwable -> {
                    log.error("Error in fetchGetClientById: {}", throwable.getMessage());
                    return Mono.just(ClientResponseDTO.builder().id(id).build());
                }
        );
    }

    @Override
    //@CircuitBreaker(name = "clientService", fallbackMethod = "fallbackCircuitBreakerFetchGetClientByDocument")
    public Mono<ClientResponseDTO> fetchGetClientByDocument(String document) {
        return reactiveCircuitBreaker.run(
                client.clientWebClient().get()
                        .uri("/document/" + document).retrieve().bodyToMono(ClientResponseDTO.class),
                throwable -> {
                    log.error("Error in fetchGetClientByDocument: {}", throwable.getMessage());
                    return Mono.just(ClientResponseDTO.builder().documentNumber(document).build());
                }
        );
    }


}
