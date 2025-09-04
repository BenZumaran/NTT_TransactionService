package com.nttdata.transaction_service.service.client;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<ClientResponseDTO> fetchGetClientById(String id);

    Mono<ClientResponseDTO> fetchGetClientByDocument(String document);

}
