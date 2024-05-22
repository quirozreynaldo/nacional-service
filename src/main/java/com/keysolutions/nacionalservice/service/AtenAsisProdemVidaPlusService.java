package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.AtenAsisProdemVidaPlus;
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
public class AtenAsisProdemVidaPlusService {
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

    private List<AtenAsisProdemVidaPlus> atenAsisProdemVidaPlusList = new ArrayList<>();

    public void addAtenAsisProdemVidaPlus(AtenAsisProdemVidaPlus atenAsisProdemVidaPlus) {
        if (atenAsisProdemVidaPlus != null) {
            atenAsisProdemVidaPlusList.add(atenAsisProdemVidaPlus);
        } else {
            log.info("atenAsisProdemVidaPlus vacio: {}", atenAsisProdemVidaPlus);
        }
    }

    public void createJiraSurvey() {
        List<AtenAsisProdemVidaPlus> jiraList = new ArrayList<>();
        for (AtenAsisProdemVidaPlus data : atenAsisProdemVidaPlusList) {
            if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }

        atenAsisProdemVidaPlusList.clear();
    }

    private void sendJira(List<AtenAsisProdemVidaPlus> atenAsisProdemVidaPlusList) {
        for (AtenAsisProdemVidaPlus atenAsisProdemVidaPlus : atenAsisProdemVidaPlusList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(atenAsisProdemVidaPlus));
                requestFieldValues.setDescription(descriptionFormat(atenAsisProdemVidaPlus));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ATEN_ASIS_PROD_VIDA_PLUS).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_ATEN_ASIS_PROD_VIDA_PLUS).getServiceDeskId());

                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_ATEN_ASIS_PROD_VIDA_PLUS,fileName));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", atenAsisProdemVidaPlus);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", atenAsisProdemVidaPlus);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private String summaryFormat(AtenAsisProdemVidaPlus atenAsisProdemVidaPlus) {
        return Constant.NAME_ATEN_ASIS_PROD_VIDA_PLUS + " " + atenAsisProdemVidaPlus.getTelefono();
    }

    private String descriptionFormat(AtenAsisProdemVidaPlus atenAsisProdemVidaPlus) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(atenAsisProdemVidaPlus.getIdProceso() + "\n");
        descripcion.append("Proveedor: ");
        descripcion.append(Utils.convertCharset(atenAsisProdemVidaPlus.getProveedor()) + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(atenAsisProdemVidaPlus.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(atenAsisProdemVidaPlus.getIdCiudad() + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(atenAsisProdemVidaPlus.getIdOperador()) + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(atenAsisProdemVidaPlus.getServicio()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(atenAsisProdemVidaPlus.getFechaContacto() + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(atenAsisProdemVidaPlus.getNombreTitular()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(atenAsisProdemVidaPlus.getTelefono() + "\n");
        descripcion.append("Documento: ");
        descripcion.append(atenAsisProdemVidaPlus.getIdDocumento() + "\n");
        descripcion.append("Especialidad : ");
        descripcion.append(Utils.convertCharset(atenAsisProdemVidaPlus.getEspecialidad()) + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getIdOperador()));
        replacements.put("ser_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getFechaContacto()));
        replacements.put("ntit_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getNombreTitular()));
        replacements.put("t_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getTelefono()));
        replacements.put("prv_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getProveedor()));
        replacements.put("idoc_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getIdDocumento()));
        replacements.put("esp_value", Utils.valueOrNA(atenAsisProdemVidaPlus.getEspecialidad()));
        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.ATEN_ASIS_PROD_VIDA_PLUS_CODE,atenAsisProdemVidaPlus.getFechaContacto(),atenAsisProdemVidaPlus.getTelefono())));

        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/N7YZTDV?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&ser=[ser_value]&fcon=[fcon_value]&ntit=[ntit_value]&t=[t_value]&prv=[prv_value]&idoc=[idoc_value]&esp=[esp_value]&idT=[idT_value]";
    }
}
