package com.erich.dev.springbootapirestwebflux.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${endpoint.rikyandmorty}")
    private String endPoint;

    @Bean
    public WebClient registerWebClientBuilder(){
        return WebClient.create(endPoint);
    }
}
