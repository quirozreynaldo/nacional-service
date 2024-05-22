package com.keysolutions.nacionalservice.controller;

import com.keysolutions.nacionalservice.model.log.WebhookLog;
import com.keysolutions.nacionalservice.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.keysolutions.nacionalservice.model.WebhookResponseComplete;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/nacional")
@RestController
public class WebhookController {
    @Autowired
    private WebhookService webhookLog;

    @RequestMapping(value = "/responsewebhook", method = RequestMethod.HEAD)
    public ResponseEntity<String> handleInboundWebhook(HttpServletRequest request) {
        log.info("Received HEAD request");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/responsewebhook")
    public ResponseEntity<String> handleInboundWebhook(@RequestBody WebhookResponseComplete webhookResponse,
            HttpServletRequest request) {
        log.info("Payload : {} ", webhookResponse);
        webhookLog.recordWebhook(webhookResponse);
        return ResponseEntity.ok("Webhook recibido correctamente");
    }
    
}
