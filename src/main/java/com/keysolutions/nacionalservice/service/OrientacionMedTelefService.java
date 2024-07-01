package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.OrientacionMedTelef;
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
public class OrientacionMedTelefService {
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

    private List<OrientacionMedTelef> orientacionMedTelefList = new ArrayList<>();

    public void addOrientacionMedTelef(OrientacionMedTelef orientacionMedTelef) {
        if (orientacionMedTelef != null) {
            orientacionMedTelefList.add(orientacionMedTelef);
        } else {
            log.info("orientacionMedTelef vacio: {}", orientacionMedTelef);
        }
    }

    public void createJiraSurvey() {
        List<OrientacionMedTelef> jiraList = new ArrayList<>();
        for (OrientacionMedTelef data : orientacionMedTelefList) {
            if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }

        orientacionMedTelefList.clear();
    }

    private void sendJira(List<OrientacionMedTelef> orientacionMedTelefList) {
        for (OrientacionMedTelef orientacionMedTelef : orientacionMedTelefList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(orientacionMedTelef));
                requestFieldValues.setDescription(descriptionFormat(orientacionMedTelef));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ORIENTACION_MED_TELEF).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ORIENTACION_MED_TELEF).getServiceDeskId());

                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_ORIENTACION_MED_TELEF,fileName,orientacionMedTelef.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", orientacionMedTelef);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", orientacionMedTelef);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private String summaryFormat(OrientacionMedTelef orientacionMedTelef) {
        return Constant.NAME_ORIENTACION_MED_TELEF + " " + orientacionMedTelef.getTelefono();
    }

    private String descriptionFormat(OrientacionMedTelef orientacionMedTelef) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(orientacionMedTelef.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(orientacionMedTelef.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(orientacionMedTelef.getIdCiudad() + "\n");
        descripcion.append("Edad: ");
        descripcion.append(orientacionMedTelef.getEdad() + "\n");
        descripcion.append("Motivo Llamada: ");
        descripcion.append(Utils.convertCharset(orientacionMedTelef.getMotivoLlamada()) + "\n");
        descripcion.append("Contacto Covid: ");
        descripcion.append(orientacionMedTelef.getContactoCovid() + "\n");
        descripcion.append("Diagnóstico Presuntivo: ");
        descripcion.append(Utils.convertCharset(orientacionMedTelef.getDiagnosticoPresuntivo()) + "\n");
        descripcion.append("Conducta: ");
        descripcion.append(Utils.convertCharset(orientacionMedTelef.getConducta()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(orientacionMedTelef.getFechaContacto() + "\n");
        descripcion.append("Médico: ");
        descripcion.append(Utils.convertCharset(orientacionMedTelef.getMedico()) + "\n");
        descripcion.append("Paciente: ");
        descripcion.append(Utils.convertCharset(orientacionMedTelef.getPaciente()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(orientacionMedTelef.getTelefono() + "\n");
        descripcion.append("Teléfono Alternativo: ");
        descripcion.append(orientacionMedTelef.getTelefonoAlternativo() + "\n");
        descripcion.append("Email: ");
        descripcion.append(orientacionMedTelef.getEmail() + "\n");
        descripcion.append("Observaciones : ");
        descripcion.append(Utils.convertCharset(orientacionMedTelef.getObservaciones()) + "\n");
        descripcion.append("Aclaraciones: ");
        descripcion.append(Utils.convertCharset(orientacionMedTelef.getAclaraciones()) + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(orientacionMedTelef.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(orientacionMedTelef.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(orientacionMedTelef.getIdCiudad()));
        replacements.put("fcon_value", Utils.valueOrNA(orientacionMedTelef.getFechaContacto()));
        replacements.put("t_value", Utils.valueOrNA(orientacionMedTelef.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(orientacionMedTelef.getEmail()));
        replacements.put("talt_value", Utils.valueOrNA(orientacionMedTelef.getTelefonoAlternativo()));
        replacements.put("eda_value", Utils.valueOrNA(orientacionMedTelef.getEdad()));
        replacements.put("mlla_value", Utils.valueOrNA(orientacionMedTelef.getMotivoLlamada()));
        replacements.put("cco_value", Utils.valueOrNA(orientacionMedTelef.getContactoCovid()));
        replacements.put("dpr_value", Utils.valueOrNA(orientacionMedTelef.getDiagnosticoPresuntivo()));
        replacements.put("con_value", Utils.valueOrNA(orientacionMedTelef.getConducta()));
        replacements.put("med_value", Utils.valueOrNA(orientacionMedTelef.getMedico()));
        replacements.put("pac_value", Utils.valueOrNA(orientacionMedTelef.getPaciente()));
        replacements.put("obs_value", Utils.valueOrNA(orientacionMedTelef.getObservaciones()));
        replacements.put("acl_value", Utils.valueOrNA(orientacionMedTelef.getAclaraciones()));
        if(orientacionMedTelef.getUniqueId()!=null && orientacionMedTelef.getUniqueId().trim().length() > 0){
            replacements.put("idT_value",orientacionMedTelef.getUniqueId());
        }else {
            replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.ORIENTACION_MED_TELEF_CODE,orientacionMedTelef.getFechaContacto(),orientacionMedTelef.getTelefono())));
        }
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/XPCGJXN?ipro=[ipro_value]&t=[t_value]&irub=[irub_value]&iciu=[iciu_value]&fcon=[fcon_value]&e=[e_value]&talt=[talt_value]&eda=[eda_value]&mlla=[mlla_value]&cco=[cco_value]&dpr=[dpr_value]&con=[con_value]&med=[med_value]&pac=[pac_value]&obs=[obs_value]&acl=[acl_value]&idT=[idT_value]";
    }
}
