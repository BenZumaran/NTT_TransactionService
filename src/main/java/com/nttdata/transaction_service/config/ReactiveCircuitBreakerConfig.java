package com.nttdata.transaction_service.config;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveCircuitBreakerConfig {


    @Bean
    public ReactiveCircuitBreaker reactiveCircuitBreakerConfigured(ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory) {
        return reactiveCircuitBreakerFactory.create("clientService");
    }
}
