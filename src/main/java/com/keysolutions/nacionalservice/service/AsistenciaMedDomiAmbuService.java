package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.AsistenciaMedDomiAmbu;
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
public class AsistenciaMedDomiAmbuService {
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

    private List<AsistenciaMedDomiAmbu> asistenciaMedAmbuDomiList = new ArrayList<>();

    public void addAsistenciaMedDomiAmbu(AsistenciaMedDomiAmbu asistenciaMedAmbuDomi) {
        if (asistenciaMedAmbuDomi != null) {
            asistenciaMedAmbuDomiList.add(asistenciaMedAmbuDomi);
        } else {
            log.info("asistenciaMedAmbuDomi vacio: {}", asistenciaMedAmbuDomi);
        }
    }

    public void createJiraSurvey() {
        List<AsistenciaMedDomiAmbu> jiraList = new ArrayList<>();
        for (AsistenciaMedDomiAmbu data : asistenciaMedAmbuDomiList) {
            if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }

        asistenciaMedAmbuDomiList.clear();
    }

    private void sendJira(List<AsistenciaMedDomiAmbu> asistenciaMedAmbuDomiList) {
        for (AsistenciaMedDomiAmbu asistenciaMedAmbuDomi : asistenciaMedAmbuDomiList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(asistenciaMedAmbuDomi));
                requestFieldValues.setDescription(descriptionFormat(asistenciaMedAmbuDomi));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ASISTENCIA_MED_DOM_AMBU).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ASISTENCIA_MED_DOM_AMBU).getServiceDeskId());

                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_ASISTENCIA_MED_DOM_AMBU,fileName));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", asistenciaMedAmbuDomi);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", asistenciaMedAmbuDomi);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private String summaryFormat(AsistenciaMedDomiAmbu asistenciaMedAmbuDomi) {
        return Constant.NAME_ASISTENCIA_MED_DOMI_AMBU + " " + asistenciaMedAmbuDomi.getTelefono();
    }

    private String descriptionFormat(AsistenciaMedDomiAmbu asistenciaMedAmbuDomi) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(asistenciaMedAmbuDomi.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(asistenciaMedAmbuDomi.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(asistenciaMedAmbuDomi.getIdCiudad() + "\n");
        descripcion.append("Edad: ");
        descripcion.append(asistenciaMedAmbuDomi.getEdad() + "\n");
        descripcion.append("Motivo Llamada: ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getMotivoLlamada()) + "\n");
        descripcion.append("Producto: ");
        descripcion.append(asistenciaMedAmbuDomi.getProducto() + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getServicio()) + "\n");
        descripcion.append("Desenlace: ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getDesenlace()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(asistenciaMedAmbuDomi.getFechaContacto() + "\n");
        descripcion.append("Fecha Arribo: ");
        descripcion.append(asistenciaMedAmbuDomi.getFechaArribo() + "\n");
        descripcion.append("Incidencia : ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getIncidencia()) + "\n");
        descripcion.append("Unidad Asignada: ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getUnidadAsignada()) + "\n");
        descripcion.append("Paciente: ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getPaciente()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(asistenciaMedAmbuDomi.getTelefono() + "\n");
        descripcion.append("Teléfono Alternativo: ");
        descripcion.append(asistenciaMedAmbuDomi.getTelefonoAlternativo() + "\n");
        descripcion.append("Dirección Atencion: ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getDireccionAtencion()) + "\n");
        descripcion.append("Aclaraciones: ");
        descripcion.append(Utils.convertCharset(asistenciaMedAmbuDomi.getDireccionAtencion()) + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getIdCiudad()));
        replacements.put("ser_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getFechaContacto()));
        replacements.put("t_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getTelefono()));
        replacements.put("talt_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getTelefonoAlternativo()));
        replacements.put("pro_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getProducto()));
        replacements.put("eda_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getEdad()));
        replacements.put("mlla_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getMotivoLlamada()));
        replacements.put("pac_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getPaciente()));
        replacements.put("des_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getDesenlace()));
        replacements.put("fari_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getFechaArribo()));
        replacements.put("inc_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getIncidencia()));
        replacements.put("uasi_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getUnidadAsignada()));
        replacements.put("acl_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getAclaraciones()));
        replacements.put("dat_value", Utils.valueOrNA(asistenciaMedAmbuDomi.getDireccionAtencion()));

        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.ASISTENCIA_MED_DOMI_AMBU_CODE,asistenciaMedAmbuDomi.getFechaContacto(),asistenciaMedAmbuDomi.getTelefono())));

        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/D6QND8P?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&ser=[ser_value]&fcon=[fcon_value]&t=[t_value]&talt=[talt_value]&pro=[pro_value]&eda=[eda_value]&mlla=[mlla_value]&pac=[pac_value]&des=[des_value]&fari=[fari_value]&inc=[inc_value]&uasi=[uasi_value]&acl=[acl_value]&dat=[dat_value]&idT=[idT_value]";
    }
}
