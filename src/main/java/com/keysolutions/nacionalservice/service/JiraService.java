package com.keysolutions.nacionalservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@Service
public class JiraService {
    @Value("${loaderservice.jira.urlrequest}")
    private String urlRequest;
    @Value("${loaderservice.jira.username}")
    private String userName;
    @Value("${loaderservice.jira.password}")
    private String password;

    @Autowired
    private WebClient webClientJira;
    public Issue createJiraTicket(TicketRequest ticketRequest) {
        log.info("createJiraTicket: " + ticketRequest);
        Issue issue = new Issue();
        try {
            String auth = userName + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String jiraToken = "Basic " + new String(encodedAuth);
            issue = webClientJira
                    .post()
                    .uri(urlRequest)
                    .header(HttpHeaders.AUTHORIZATION, jiraToken)
                    .body(Mono.just(ticketRequest), TicketRequest.class)
                    .retrieve()
                    .bodyToMono(Issue.class)
                    .block();
            log.info("createJiraTicket: {}", issue);

        } catch (WebClientResponseException e) {
            // Capturar errores relacionados con la respuesta del servidor
            log.error("Error al consumir el servicio. Código de error: {}", e.getRawStatusCode());
            log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
            log.error("{}",ticketRequest);
            throw e;
        } catch (Exception e) {
            // Capturar otros errores inesperados
            log.error("Error inesperado al consumir el servicio", e);
            log.error("{}",ticketRequest);
            throw e;
        }
        return issue;
    }
    
}
