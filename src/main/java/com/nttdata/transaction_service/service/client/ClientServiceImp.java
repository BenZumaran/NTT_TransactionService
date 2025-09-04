package com.nttdata.transaction_service.service.client;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImp implements ClientService {

    @Autowired
    ClientClient client;

    @Override
    public Mono<ClientResponseDTO> fetchGetClientById(String id) {
        return client.clientWebClient().get()
                .uri("/" + id).retrieve().bodyToMono(ClientResponseDTO.class);
    }

    @Override
    public Mono<ClientResponseDTO> fetchGetClientByDocument(String document) {
        return client.clientWebClient().get()
                .uri("/document/" + document).retrieve().bodyToMono(ClientResponseDTO.class);
    }
}
