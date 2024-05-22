package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageSurveyInMemory;
import com.keysolutions.nacionalservice.model.InmedicalBancoGanadero;
import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.util.Constant;
import com.keysolutions.nacionalservice.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InmedicalBancoGanaderoService {
    @Autowired
    private SurveyMonkeyService surveyMonkeyService;
    @Autowired
    private ManageSurveyInMemory manageSurveyInMemory;
    @Autowired
    private JiraService jiraService;
    @Autowired
    private ManageLog manageLog;
    @Autowired
    private ManageJiraInMemory manageJiraInMemory;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private List<InmedicalBancoGanadero> inmedicalBancoGanaderoList = new ArrayList<>();

    public void addInmedicaBancoGandero(InmedicalBancoGanadero inmedicalBancoGanadero) {
        if (inmedicalBancoGanadero != null) {
            inmedicalBancoGanaderoList.add(inmedicalBancoGanadero);
        } else {
            log.info("addInmedicaBancoGandero vacio: {}", inmedicalBancoGanadero);
        }
    }

    public void createJiraSurvey() {
        List<InmedicalBancoGanadero> jiraList = new ArrayList<>();
        List<InmedicalBancoGanadero> surveyList = new ArrayList<>();
        for (InmedicalBancoGanadero data : inmedicalBancoGanaderoList) {
            if (data.getEmail() != null && data.getEmail().trim().length() > 0) {
                surveyList.add(data);
            } else if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        } else {
            if (surveyList.size() > 0) {
                log.info("==========NO TIENE COMPILAR EMAIL CONFIGURADO SEND-SURVEY==========");
                //sendSurvey(surveyList);
            }
        }
        inmedicalBancoGanaderoList.clear();
    }

    private void sendJira(List<InmedicalBancoGanadero> inmedicalBancoGanaderoList) {
        for (InmedicalBancoGanadero inmedicalBancoGanadero : inmedicalBancoGanaderoList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(inmedicalBancoGanadero));
                requestFieldValues.setDescription(descriptionFormat(inmedicalBancoGanadero));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_INMEDICAL_BANCO_GANADERO).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_INMEDICAL_BANCO_GANADERO).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_INMEDICAL_BANCO_GANADERO,fileName));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", inmedicalBancoGanadero);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", inmedicalBancoGanadero);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }
        }
    }
    private String summaryFormat(InmedicalBancoGanadero inmedicalBancoGanadero) {
        return Constant.NAME_INMEDICAL_BANCO_GANADERO + " " + inmedicalBancoGanadero.getTelefono();
    }

    private String descriptionFormat(InmedicalBancoGanadero inmedicalBancoGanadero) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(inmedicalBancoGanadero.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(inmedicalBancoGanadero.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(inmedicalBancoGanadero.getIdCiudad() + "\n");
        descripcion.append("Proveedor: ");
        descripcion.append(Utils.convertCharset(inmedicalBancoGanadero.getProveedor()) + "\n");
        descripcion.append("Id Centro: ");
        descripcion.append(Utils.convertCharset(inmedicalBancoGanadero.getIdCentro()) + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(inmedicalBancoGanadero.getServicio()) + "\n");
        descripcion.append("Fecha contacto: ");
        descripcion.append(inmedicalBancoGanadero.getFechaContacto() + "\n");
        descripcion.append("Id Documento: ");
        descripcion.append(inmedicalBancoGanadero.getIdDocumento() + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(inmedicalBancoGanadero.getNombreTitular()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(inmedicalBancoGanadero.getNombreSolicitante()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(inmedicalBancoGanadero.getTelefono() + "\n");
        descripcion.append("Email: ");
        descripcion.append(inmedicalBancoGanadero.getEmail() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(inmedicalBancoGanadero.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(inmedicalBancoGanadero.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(inmedicalBancoGanadero.getIdCiudad()));
        replacements.put("ser_value", Utils.valueOrNA(inmedicalBancoGanadero.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(inmedicalBancoGanadero.getFechaContacto()));
        replacements.put("ntit_value", Utils.valueOrNA(inmedicalBancoGanadero.getNombreTitular()));
        replacements.put("nsol_value", Utils.valueOrNA(inmedicalBancoGanadero.getNombreSolicitante()));
        replacements.put("t_value", Utils.valueOrNA(inmedicalBancoGanadero.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(inmedicalBancoGanadero.getEmail()));
        replacements.put("prv_value", Utils.valueOrNA(inmedicalBancoGanadero.getProveedor()));
        replacements.put("icen_value", Utils.valueOrNA(inmedicalBancoGanadero.getIdCentro()));
        replacements.put("idoc_value", Utils.valueOrNA(inmedicalBancoGanadero.getIdDocumento()));
        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.INMEDICAL_BANCO_GANADERO_CODE,inmedicalBancoGanadero.getFechaContacto(),inmedicalBancoGanadero.getTelefono())));
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/DXHF2LT?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&ser=[ser_value]&fcon=[fcon_value]&ntit=[ntit_value]&nsol=[nsol_value]&t=[t_value]&e=[e_value]&prv=[prv_value]&icen=[icen_value]&idoc=[idoc_value]&idT=[idT_value]";
    }
}
