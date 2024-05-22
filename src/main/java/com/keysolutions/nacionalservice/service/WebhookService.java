package com.keysolutions.nacionalservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.WebhookResponseComplete;
import com.keysolutions.nacionalservice.model.log.WebhookLog;
import com.keysolutions.nacionalservice.util.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebhookService {
   @Autowired
   private ManageLog manageLog;


   public void recordWebhook(WebhookResponseComplete webhookResponse) {
      log.info("webhookResponse: {}", webhookResponse);

      manageLog.updateRecipientResponded(webhookResponse.getResources().getCollector_id(), webhookResponse.getResources().getRecipient_id(), Constant.RESPONDED);

      WebhookLog webhookLog = new WebhookLog();
      try {
         webhookLog.setName(webhookResponse.getName());
         webhookLog.setFilterType(webhookResponse.getFilter_type());
         webhookLog.setFilterId(webhookResponse.getFilter_id());
         webhookLog.setEventType(webhookResponse.getEvent_type());
         webhookLog.setEventId(webhookResponse.getEvent_id());
         webhookLog.setEventDatetime(webhookResponse.getEvent_datetime());
         webhookLog.setObjectType(webhookResponse.getObject_type());
         webhookLog.setObjectId(webhookResponse.getObject_id());
         webhookLog.setRespondentId(webhookResponse.getResources().getRespondent_id());
         webhookLog.setRecipientId(webhookResponse.getResources().getRecipient_id());
         webhookLog.setSurveyId(webhookResponse.getResources().getSurvey_id());
         webhookLog.setUserId(webhookResponse.getResources().getUser_id());
         webhookLog.setCollectorId(webhookResponse.getResources().getCollector_id());
         webhookLog.setStatus(Constant.ACTIVE_STATUS);
         manageLog.recordWebhook(webhookLog);
      } catch (Exception ex) {
         log.error("recordWebhook: {}", ex.getMessage());
      }

   }
}