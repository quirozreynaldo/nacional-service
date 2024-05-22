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
import com.keysolutions.nacionalservice.model.AccidentesPersonales;
import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.util.Constant;
import com.keysolutions.nacionalservice.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccidentesPersonalesService {
    @Autowired
    private JiraService jiraService;
    @Autowired
    private ManageLog manageLog;
    @Autowired
    private ManageJiraInMemory manageJiraInMemory;
    private String fileName;

    private List<AccidentesPersonales> accidentesPersonalesList = new ArrayList<>();

    public void addAccidentesPersonales(AccidentesPersonales accidentesPersonales) {
        if (accidentesPersonales != null) {
            accidentesPersonalesList.add(accidentesPersonales);
        } else {
            log.info("accidentesPersonales vacio: {}", accidentesPersonales);
        }
    }

    public void createJiraSurvey() {
        List<AccidentesPersonales> jiraList = new ArrayList<>();
        List<AccidentesPersonales> surveyList = new ArrayList<>();
        for (AccidentesPersonales data : accidentesPersonalesList) {
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
                log.info("======NO TIENE RECOPILADOR DEFINIDO PARA SEND-SURVEY==========");
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,
                        Constant.GENERAL_ERROR_DESCRIPTION, "sendSurvey",
                        fileName + " NO TIENE RECOPILADOR PARA SEND-SURVEY"));
                // sendSurvey(surveyList);
            }
        }
        accidentesPersonalesList.clear();
    }

    private void sendJira(List<AccidentesPersonales> accidentesPersonalesList) {
        for (AccidentesPersonales accidentesPersonales : accidentesPersonalesList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(accidentesPersonales));
                requestFieldValues.setDescription(descriptionFormat(accidentesPersonales));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ACCIDENTES_PERSONALES).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ACCIDENTES_PERSONALES).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(), issue.getIssueKey(), issue.getRequestTypeId(),issue.getServiceDeskId(), Constant.CONFIG_ACCIDENTES_PERSONALES, fileName));

            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", accidentesPersonales);
                manageLog.recorErrorlog(Utils.createErrorLog("" + e.getRawStatusCode(), e.getResponseBodyAsString(), "sendJira", e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", accidentesPersonales);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION, "sendJira", e.getMessage()));
            }

        }
    }

    private String summaryFormat(AccidentesPersonales accidentesPersonales) {
        return Constant.NAME_ACCIDENTES_PERSONALES + " " + accidentesPersonales.getTelefono();
    }

    private String descriptionFormat(AccidentesPersonales accidentesPersonales) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(accidentesPersonales.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(accidentesPersonales.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(accidentesPersonales.getIdCiudad() + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(accidentesPersonales.getIdOperador()) + "\n");
        descripcion.append("Id Servicio: ");
        descripcion.append(Utils.convertCharset(accidentesPersonales.getServicio()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(accidentesPersonales.getFechaContacto() + "\n");
        descripcion.append("Placa: ");
        descripcion.append(Utils.convertCharset(accidentesPersonales.getPlaca()) + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(accidentesPersonales.getNombreTitular()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(accidentesPersonales.getNombreSolicitante()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(accidentesPersonales.getTelefono() + "\n");
        descripcion.append("Email: ");
        descripcion.append(accidentesPersonales.getEmail() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(accidentesPersonales.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(accidentesPersonales.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(accidentesPersonales.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(Utils.convertCharset(accidentesPersonales.getIdOperador())));
        replacements.put("ser_value", Utils.valueOrNA(Utils.convertCharset(accidentesPersonales.getServicio())));
        replacements.put("fcon_value", Utils.valueOrNA(accidentesPersonales.getFechaContacto()));
        replacements.put("ntit_value", Utils.valueOrNA(Utils.convertCharset(accidentesPersonales.getNombreTitular())));
        replacements.put("nsol_value", Utils.valueOrNA(Utils.convertCharset(accidentesPersonales.getNombreSolicitante())));
        replacements.put("t_value", Utils.valueOrNA(accidentesPersonales.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(accidentesPersonales.getEmail()));
        replacements.put("pla_value", Utils.valueOrNA(Utils.convertCharset(accidentesPersonales.getPlaca())));
        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.ACCIDENTES_PERSONALES_CODE,accidentesPersonales.getFechaContacto(),accidentesPersonales.getTelefono())));


        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/XVRYTMT?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&ser=[ser_value]&fcon=[fcon_value]&ntit=[ntit_value]&nsol=[nsol_value]&t=[t_value]&e=[e_value]&pla=[pla_value]&idT=[idT_value]";
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
