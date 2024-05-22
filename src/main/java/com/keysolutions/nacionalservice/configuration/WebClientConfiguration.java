package com.keysolutions.nacionalservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfiguration {
    @Value("${loaderservice.jira.urlbase}")
    private String urlBase;
    @Value("${surveymonkey.service.urlbase}")
    private String surveyMonkeyApiUrlBase;

    @Bean
    public WebClient webClientJira() {
        return WebClient.builder()
                .baseUrl(urlBase)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    @Bean
    public WebClient webClientApi() {
        final int size = 10 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
        .baseUrl(surveyMonkeyApiUrlBase)
                .exchangeStrategies(strategies)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).exchangeStrategies(strategies)
        .build();
    }
}
