package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.AsistenciaMedDomi;
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
public class AsistenciaMedDomiService {
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

    private List<AsistenciaMedDomi> asistenciaMedDomiList = new ArrayList<>();

    public void addAsistenciaMedDomi(AsistenciaMedDomi asistenciaMedDomi) {
        if (asistenciaMedDomi != null) {
            asistenciaMedDomiList.add(asistenciaMedDomi);
        } else {
            log.info("asistenciaMedDomi vacio: {}", asistenciaMedDomi);
        }
    }

    public void createJiraSurvey() {
        List<AsistenciaMedDomi> jiraList = new ArrayList<>();
        for (AsistenciaMedDomi data : asistenciaMedDomiList) {
            if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }

        asistenciaMedDomiList.clear();
    }

    private void sendJira(List<AsistenciaMedDomi> asistenciaMedDomiList) {
        for (AsistenciaMedDomi asistenciaMedDomi : asistenciaMedDomiList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(asistenciaMedDomi));
                requestFieldValues.setDescription(descriptionFormat(asistenciaMedDomi));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ASISTENCIA_MED_DOM).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ASISTENCIA_MED_DOM).getServiceDeskId());

                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_ASISTENCIA_MED_DOM,fileName));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", asistenciaMedDomi);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", asistenciaMedDomi);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private String summaryFormat(AsistenciaMedDomi asistenciaMedDomi) {
        return Constant.NAME_ASISTENCIA_MED_DOMI + " " + asistenciaMedDomi.getTelefono();
    }

    private String descriptionFormat(AsistenciaMedDomi asistenciaMedDomi) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(asistenciaMedDomi.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(asistenciaMedDomi.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(asistenciaMedDomi.getIdCiudad() + "\n");
        descripcion.append("Edad: ");
        descripcion.append(asistenciaMedDomi.getEdad() + "\n");
        descripcion.append("Motivo Llamada: ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getMotivoLlamada()) + "\n");
        descripcion.append("Producto: ");
        descripcion.append(asistenciaMedDomi.getProducto() + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getServicio()) + "\n");
        descripcion.append("Desenlace: ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getDesenlace()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(asistenciaMedDomi.getFechaContacto() + "\n");
        descripcion.append("Fecha Arribo: ");
        descripcion.append(asistenciaMedDomi.getFechaArribo() + "\n");
        descripcion.append("Incidencia : ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getIncidencia()) + "\n");
        descripcion.append("Unidad Asignada: ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getUnidadAsignada()) + "\n");
        descripcion.append("Paciente: ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getPaciente()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(asistenciaMedDomi.getTelefono() + "\n");
        descripcion.append("Teléfono Alternativo: ");
        descripcion.append(asistenciaMedDomi.getTelefonoAlternativo() + "\n");
        descripcion.append("Dirección Atencion: ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getDireccionAtencion()) + "\n");
        descripcion.append("Aclaraciones: ");
        descripcion.append(Utils.convertCharset(asistenciaMedDomi.getDireccionAtencion()) + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(asistenciaMedDomi.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(asistenciaMedDomi.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(asistenciaMedDomi.getIdCiudad()));
        replacements.put("ser_value", Utils.valueOrNA(asistenciaMedDomi.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(asistenciaMedDomi.getFechaContacto()));
        replacements.put("t_value", Utils.valueOrNA(asistenciaMedDomi.getTelefono()));
        replacements.put("talt_value", Utils.valueOrNA(asistenciaMedDomi.getTelefonoAlternativo()));
        replacements.put("pro_value", Utils.valueOrNA(asistenciaMedDomi.getProducto()));
        replacements.put("eda_value", Utils.valueOrNA(asistenciaMedDomi.getEdad()));
        replacements.put("mlla_value", Utils.valueOrNA(asistenciaMedDomi.getMotivoLlamada()));
        replacements.put("pac_value", Utils.valueOrNA(asistenciaMedDomi.getPaciente()));
        replacements.put("des_value", Utils.valueOrNA(asistenciaMedDomi.getDesenlace()));
        replacements.put("fari_value", Utils.valueOrNA(asistenciaMedDomi.getFechaArribo()));
        replacements.put("uasi_value", Utils.valueOrNA(asistenciaMedDomi.getUnidadAsignada()));
        replacements.put("acl_value", Utils.valueOrNA(asistenciaMedDomi.getAclaraciones()));
        replacements.put("dat_value", Utils.valueOrNA(asistenciaMedDomi.getDireccionAtencion()));

        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.ASISTENCIA_MED_DOMI_CODE,asistenciaMedDomi.getFechaContacto(),asistenciaMedDomi.getTelefono())));

        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/DZM3KC9?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&ser=[ser_value]&fcon=[fcon_value]&t=[t_value]&talt=[talt_value]&pro=[pro_value]&eda=[eda_value]&mlla=[mlla_value]&pac=[pac_value]&des=[des_value]&fari=[fari_value]&uasi=[uasi_value]&acl=[acl_value]&dat=[dat_value]&idT=[idT_value]";
    }
}
