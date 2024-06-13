package com.keysolutions.nacionalservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.CallCenterNspf;
import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.util.Constant;
import com.keysolutions.nacionalservice.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CallCenterNspfService {
    @Autowired
    private JiraService jiraService;
    @Autowired
    private ManageLog manageLog;
    @Autowired
    private ManageJiraInMemory manageJiraInMemory;
    private String fileName;
    
    private List<CallCenterNspf> callCenterNspfList = new ArrayList<>();

    public void addCallCenterNspf(CallCenterNspf callCenterNspf) {
        if (callCenterNspf != null) {
            callCenterNspfList.add(callCenterNspf);
        } else {
            log.info("callCenterNspf vacio: {}", callCenterNspf);
        }
    }

    public void createJiraSurvey() {
        List<CallCenterNspf> jiraList = new ArrayList<>();
        for (CallCenterNspf data : callCenterNspfList) {
            if (data.getIdCliente() != null && data.getIdCliente().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }
        callCenterNspfList.clear();
    }

    private void sendJira(List<CallCenterNspf> callCenterNspfList) {
        for (CallCenterNspf callCenterNspf : callCenterNspfList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(callCenterNspf));
                requestFieldValues.setDescription(descriptionFormat(callCenterNspf));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_CALLCENTER_NSPF).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_CALLCENTER_NSPF).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_CALLCENTER_NSPF,fileName,callCenterNspf.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", callCenterNspf);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", callCenterNspf);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

      private String summaryFormat(CallCenterNspf callCenterNspf) {
        return Constant.NAME_CALLCENTER_NSPF + " " + callCenterNspf.getIdCliente();
    }

    private String descriptionFormat(CallCenterNspf callCenterNspf) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(callCenterNspf.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(callCenterNspf.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(callCenterNspf.getIdCiudad() + "\n");
        descripcion.append("Id Siniestro: ");
        descripcion.append(Utils.convertCharset(callCenterNspf.getIdSiniestro()) + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(callCenterNspf.getIdOperador()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(callCenterNspf.getFechaContacto() + "\n");
        descripcion.append("Id Cliente: ");
        descripcion.append(callCenterNspf.getIdCliente() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(callCenterNspf.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(callCenterNspf.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(callCenterNspf.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(Utils.convertCharset(callCenterNspf.getIdOperador())));
        replacements.put("fcon_value", Utils.valueOrNA(callCenterNspf.getFechaContacto() ));
        replacements.put("isin_value", Utils.valueOrNA(Utils.convertCharset(callCenterNspf.getIdSiniestro())));
        replacements.put("icli_value", Utils.valueOrNA(callCenterNspf.getIdCliente()));
        if(callCenterNspf.getUniqueId()!=null && callCenterNspf.getUniqueId().trim().length() > 0){
            replacements.put("idT_value",callCenterNspf.getUniqueId());
        }else {
            replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.CALLCENTER_NSPF_CODE,callCenterNspf.getFechaContacto(),callCenterNspf.getIdCliente())));
        }
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.surveymonkey.com/r/CMRBHTK?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&fcon=[fcon_value]&isin=[isin_value]&icli=[icli_value]&idT=[idT_value]";
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
