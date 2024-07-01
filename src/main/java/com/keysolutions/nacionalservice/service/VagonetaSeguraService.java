package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.VagonetaSegura;
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
public class VagonetaSeguraService {
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

    private List<VagonetaSegura> vagonetaSeguraList = new ArrayList<>();

    public void addVagonetaSegura(VagonetaSegura vagonetaSegura) {
        if (vagonetaSegura != null) {
            vagonetaSeguraList.add(vagonetaSegura);
        } else {
            log.info("vagonetaSeguras vacio: {}", vagonetaSegura);
        }
    }

    public void createJiraSurvey() {
        List<VagonetaSegura> jiraList = new ArrayList<>();
        for (VagonetaSegura data : vagonetaSeguraList) {
            if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }

        vagonetaSeguraList.clear();
    }

    private void sendJira(List<VagonetaSegura> vagonetaSeguraList) {
        for (VagonetaSegura vagonetaSegura : vagonetaSeguraList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(vagonetaSegura));
                requestFieldValues.setDescription(descriptionFormat(vagonetaSegura));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_VAGONETA_SEGURA).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_VAGONETA_SEGURA).getServiceDeskId());

                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_VAGONETA_SEGURA,fileName,vagonetaSegura.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", vagonetaSegura);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", vagonetaSegura);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private String summaryFormat(VagonetaSegura vagonetaSegura) {
        return Constant.NAME_VAGONETA_SEGURA + " " + vagonetaSegura.getTelefono();
    }

    private String descriptionFormat(VagonetaSegura vagonetaSegura) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(vagonetaSegura.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(vagonetaSegura.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(vagonetaSegura.getIdCiudad() + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(vagonetaSegura.getIdOperador()) + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(vagonetaSegura.getServicio()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(vagonetaSegura.getFechaContacto() + "\n");
        descripcion.append("Fecha Servicio: ");
        descripcion.append(vagonetaSegura.getFechaServicio() + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(vagonetaSegura.getNombreTitular()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(vagonetaSegura.getTelefono() + "\n");
        descripcion.append("Teléfono Alternativo: ");
        descripcion.append(vagonetaSegura.getTelefonoAlternativo() + "\n");
        descripcion.append("Placa: ");
        descripcion.append(Utils.convertCharset(vagonetaSegura.getPlaca()) + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(vagonetaSegura.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(vagonetaSegura.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(vagonetaSegura.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(Utils.convertCharset(vagonetaSegura.getIdOperador())));
        replacements.put("ser_value", Utils.valueOrNA(Utils.convertCharset(vagonetaSegura.getServicio())));
        replacements.put("fcon_value", Utils.valueOrNA(vagonetaSegura.getFechaContacto() ));
        replacements.put("ntit_value", Utils.valueOrNA(Utils.convertCharset(vagonetaSegura.getNombreTitular())));
        replacements.put("t_value", Utils.valueOrNA(vagonetaSegura.getTelefono()));
        replacements.put("pla_value", Utils.valueOrNA(Utils.convertCharset(vagonetaSegura.getPlaca())));
        replacements.put("talt_value", Utils.valueOrNA(vagonetaSegura.getTelefonoAlternativo()));
        replacements.put("fser_value", Utils.valueOrNA(vagonetaSegura.getFechaServicio()));
        if(vagonetaSegura.getUniqueId()!=null && vagonetaSegura.getUniqueId().trim().length() > 0){
            replacements.put("idT_value",vagonetaSegura.getUniqueId());
        }else {
            replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.VAGONETA_SEGURA_CODE,vagonetaSegura.getFechaContacto(),vagonetaSegura.getTelefono())));
        }
         descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/9Z72QZQ?ipro=[ipro_value]&t=[t_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&ser=[ser_value]&fcon=[fcon_value]&ntit=[ntit_value]&pla=[pla_value]&talt=[talt_value]&fser=[fser_value]&idT=[idT_value]";
    }
}
