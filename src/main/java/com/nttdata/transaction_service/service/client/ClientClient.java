package com.nttdata.transaction_service.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientClient {

    @Value("${external.client.service.uri}")
    String CLIENT_URI;

    @Bean
    public WebClient clientWebClient() {
        return WebClient.builder().baseUrl(CLIENT_URI).build();
    }

}
