package com.nttdata.transaction_service.service.card;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CardClient {

    @Value("${external.card.service.uri}")
    private String CARD_URI;

    @Bean
    public WebClient cardWebClient() {
        return WebClient.builder().baseUrl(CARD_URI).build();
    }

}
