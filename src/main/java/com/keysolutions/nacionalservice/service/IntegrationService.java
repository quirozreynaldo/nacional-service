package com.keysolutions.nacionalservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;

@Slf4j
@Service
public class IntegrationService {
    @Autowired
    private JiraService jiraService;
    public Issue createJiraTicket(TicketRequest ticketRequest) {
        log.info("ticketRequest: {}",ticketRequest);
        return jiraService.createJiraTicket(ticketRequest);
    }
}
