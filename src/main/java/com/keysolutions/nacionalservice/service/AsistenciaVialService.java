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
import com.keysolutions.nacionalservice.model.AsistenciaVial;
import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.util.Constant;
import com.keysolutions.nacionalservice.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsistenciaVialService {
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

    private List<AsistenciaVial> asistenciaVialList = new ArrayList<>();

    public void addAsistenciaVial(AsistenciaVial asistenciaVial) {
        if (asistenciaVial != null) {
            asistenciaVialList.add(asistenciaVial);
        } else {
            log.info("asistenciaVials vacio: {}", asistenciaVial);
        }
    }

    public void createJiraSurvey() {
        List<AsistenciaVial> jiraList = new ArrayList<>();
        for (AsistenciaVial data : asistenciaVialList) {
            if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }

        asistenciaVialList.clear();
    }

        private void sendJira(List<AsistenciaVial> asistenciaVialList) {
        for (AsistenciaVial asistenciaVial : asistenciaVialList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(asistenciaVial));
                requestFieldValues.setDescription(descriptionFormat(asistenciaVial));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ASISTENCIA_VIAL).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ASISTENCIA_VIAL).getServiceDeskId());
                
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_ASISTENCIA_VIAL,fileName,asistenciaVial.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", asistenciaVial);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", asistenciaVial);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

      private String summaryFormat(AsistenciaVial asistenciaVial) {
        return Constant.NAME_ASISTENCIA_VIAL + " " + asistenciaVial.getTelefono();
    }

    private String descriptionFormat(AsistenciaVial asistenciaVial) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(asistenciaVial.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(asistenciaVial.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(asistenciaVial.getIdCiudad() + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(asistenciaVial.getIdOperador()) + "\n");
        descripcion.append("Id Siniestro: ");
        descripcion.append(Utils.convertCharset(asistenciaVial.getIdSiniestro()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(asistenciaVial.getFechaContacto() + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(asistenciaVial.getNombreTitular()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(asistenciaVial.getNombreSolicitante()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(asistenciaVial.getTelefono() + "\n");
        descripcion.append("Teléfono Alternativo: ");
        descripcion.append(asistenciaVial.getTelefonoAlternativo() + "\n");
        descripcion.append("Placa: ");
        descripcion.append(Utils.convertCharset(asistenciaVial.getPlaca()) + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(asistenciaVial.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(asistenciaVial.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(asistenciaVial.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(Utils.convertCharset(asistenciaVial.getIdOperador())));
        replacements.put("fcon_value", Utils.valueOrNA(asistenciaVial.getFechaContacto() ));
        replacements.put("ntit_value", Utils.valueOrNA(Utils.convertCharset(asistenciaVial.getNombreTitular())));
        replacements.put("nsol_value", Utils.valueOrNA(Utils.convertCharset(asistenciaVial.getNombreSolicitante())));
        replacements.put("t_value", Utils.valueOrNA(asistenciaVial.getTelefono()));
        replacements.put("pla_value", Utils.valueOrNA(Utils.convertCharset(asistenciaVial.getPlaca())));
        replacements.put("isin_value", Utils.valueOrNA(""));
        replacements.put("talt_value", Utils.valueOrNA(""));
        if(asistenciaVial.getUniqueId()!=null && asistenciaVial.getUniqueId().trim().length() > 0){
            replacements.put("idT_value",asistenciaVial.getUniqueId());
        }else {
            replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.ASISTENCIA_VIAL_CODE,asistenciaVial.getFechaContacto(),asistenciaVial.getTelefono())));
        }
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/XLCHNDB?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&fcon=[fcon_value]&ntit=[ntit_value]&nsol=[nsol_value]&t=[t_value]&pla=[pla_value]&isin=[isin_value]&talt=[talt_value]&idT=[idT_value]";
    }
}
