package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.model.Client;
import com.nttdata.transaction_service.model.entity.ClientEntity;

public class ClientMapper {

    // Validate and Converts Client Entity to Client
    public static Client clientEntityToClient(ClientEntity clientEntity){
        Client client = new Client();
        client.setId(clientEntity.getId());
        if (clientEntity.getType() != null)
            client.setType(Client.TypeEnum.valueOf(clientEntity.getType().toUpperCase()));
        if (clientEntity.getDocument() != null)
            client.setDocument(clientEntity.getDocument());
        return client;
    }

    // Validate and Converts Client to Client Entity
    public static ClientEntity clientToClientEntity(Client client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(client.getId());
        if (client.getType() != null)
            clientEntity.setType(client.getType().getValue());
        if(client.getDocument() != null)
            clientEntity.setDocument(client.getDocument());
        return clientEntity;
    }



}
