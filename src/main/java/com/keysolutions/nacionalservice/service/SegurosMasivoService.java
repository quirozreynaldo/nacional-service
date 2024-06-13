package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageSurveyInMemory;
import com.keysolutions.nacionalservice.model.SegurosMasivo;
import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.model.survey.*;
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
public class SegurosMasivoService {
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

    private List<SegurosMasivo> segurosMasivoList = new ArrayList<>();

    public void addSegurosMasivo(SegurosMasivo segurosMasivo) {
        if (segurosMasivo != null) {
            segurosMasivoList.add(segurosMasivo);
        } else {
            log.info("segurosMasivo vacio: {}", segurosMasivo);
        }
    }

    public void createJiraSurvey() {
        List<SegurosMasivo> jiraList = new ArrayList<>();
        List<SegurosMasivo> surveyList = new ArrayList<>();
        for (SegurosMasivo data : segurosMasivoList) {
            if (data.getEmail() != null && data.getEmail().trim().length() > 0) {
                surveyList.add(data);
            } else if (data.getTelefono() != null && data.getTelefono().trim().length() > 0) {
                jiraList.add(data);
            }
        }
        if (jiraList.size() > 0) {
            log.info("==========SEND-JIRA==========");
            sendJira(jiraList);
        }
        if (surveyList.size() > 0) {
            log.info("==========SEND-SURVEY==========");
            sendSurvey(surveyList);
        }

        segurosMasivoList.clear();
    }

    public void sendJira(List<SegurosMasivo> segurosMasivoList) {
        for (SegurosMasivo segurosMasivo : segurosMasivoList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(segurosMasivo));
                requestFieldValues.setDescription(descriptionFormat(segurosMasivo));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_SEGUROS_MASIVO).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_SEGUROS_MASIVO).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_SEGUROS_MASIVO,fileName,segurosMasivo.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", segurosMasivo);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", segurosMasivo);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private void sendSurvey(List<SegurosMasivo> segurosMasivoList) {

        String contactListId = manageSurveyInMemory.getConfigContactList(Constant.CONFIG_NACIONAL_CONTACTS);
        Contacts contacts = new Contacts();
        List<Contact> contactsList = new ArrayList<>();
        String currentDateTime = Utils.getCurrentDateTimeFormatted();
        try {
            for (SegurosMasivo data : segurosMasivoList) {

                if (data.getEmail() != null) {
                    Contact contact = new Contact();
                    contact.setEmail(getAliasEmail(data, currentDateTime));
                    contact.setFirstName(data.getEmail());
                    contact.setLastName(data.getUniqueId()!=null && data.getUniqueId().trim().length() > 0 ? data.getUniqueId(): getCodigo(data, currentDateTime));
                    CustomFields customFields = new CustomFields();
                    customFields.setField1(data.getTelefono() != null ? data.getTelefono() : "NA");
                    customFields.setField2("NA");
                    customFields.setField3(data.getIdRubro() != null ? data.getIdRubro() : "NA");
                    customFields.setField4(data.getIdCiudad() != null ? data.getIdCiudad() : "NA");
                    customFields.setField5("NA");
                    customFields.setField6("NA");
                    customFields.setField7(Utils.convertCharset(data.getServicio() != null ? data.getServicio() : "NA"));
                    customFields.setField8("NA");
                    customFields.setField9(Utils.convertCharset(data.getNombreSolicitante() != null ? data.getNombreSolicitante() : "NA"));
                    customFields.setField10("NA");
                    customFields.setField11("NA");
                    customFields.setField12(data.getFechaContacto() != null ? data.getFechaContacto() : "NA");
                    customFields.setField13("NA");
                    customFields.setField14("NA");
                    customFields.setField15("NA");
                    customFields.setField16(data.getIdDocumento() != null ? data.getIdDocumento() : "NA");
                    customFields.setField17("NA");
                    customFields.setField18("NA");
                    customFields.setField19("NA");
                    customFields.setField20("NA");
                    customFields.setField21("NA");
                    customFields.setField22("NA");
                    customFields.setField23(Utils.convertCharset(data.getProducto() != null ? data.getProducto() : "NA"));
                    customFields.setField24("NA");
                    customFields.setField25("NA");
                    customFields.setField26("NA");
                    customFields.setField27("NA");
                    customFields.setField28("NA");
                    customFields.setField29("NA");
                    customFields.setField30("NA");
                    customFields.setField31("NA");
                    customFields.setField32("NA");
                    customFields.setField33("NA");
                    customFields.setField34("NA");
                    customFields.setField35("NA");
                    customFields.setField36("NA");
                    customFields.setField37("NA");
                    customFields.setField38("NA");
                    customFields.setField39(data.getIdProceso() != null ? data.getIdProceso() : "NA");
                    customFields.setField40("NA");
                    customFields.setField41(Utils.convertCharset(data.getProveedorDeAsistencia() != null ? data.getProveedorDeAsistencia() : "NA"));
                    customFields.setField42("NA");
                    customFields.setField43("NA");
                    customFields.setField44(Utils.convertCharset(data.getSponsor() != null ? data.getSponsor() : "NA"));
                    customFields.setField45(data.getFechaUsoServicio() != null ? data.getFechaUsoServicio() : "NA");
                    customFields.setField46("NA");
                    customFields.setField47("NA");
                    customFields.setField48("NA");
                    customFields.setField49("NA");
                    customFields.setField50(data.getUniqueId());
                    contact.setCustomFields(customFields);
                    contactsList.add(contact);
                }
            }
            // Dividir la lista de contactos en lotes de 100 y procesar cada lote
            int batchSize = 100;
            for (int i = 0; i < contactsList.size(); i += batchSize) {
                try {
                    List<Contact> batch = contactsList.subList(i, Math.min(i + batchSize, contactsList.size()));
                    contacts.setContacts(batch);
            Succeeded succeeded = surveyMonkeyService.createMultiContacts(contacts, contactListId);
            log.debug("succeeded:{}", succeeded);
            Utils.waitMilliSeconds(500);

            List<Contact> contactList = new ArrayList<>();
            log.info("succeeded.getExisting():{}", succeeded.getExisting());
            log.info("succeeded.getSucceeded(): {}", succeeded.getSucceeded());
            log.info("succeeded.getInvalid(): {}", succeeded.getInvalid());
            if (succeeded.getExisting().size() > 0) {
                contactList.addAll(succeeded.getExisting());
            }
            if (succeeded.getSucceeded().size() > 0) {
                contactList.addAll(succeeded.getSucceeded());
            }

            String collectorId = manageSurveyInMemory.getConfigCollector(Constant.CONFIG_SEGUROS_MASIVO);
            String messageConfigId = manageSurveyInMemory.getConfigMessage(Constant.CONFIG_SEGUROS_MASIVO);
            MessageResponse messageResponse = surveyMonkeyService.getCollectorMessages(collectorId, messageConfigId);
            String messageId = messageResponse.getId();
            Utils.waitMilliSeconds(500);
            /////////////////////////////////////////////////////////////////////////
            List<String> list_contacts = new ArrayList<>();
            List<String> contacts_ids = new ArrayList<>();

            RecipientRequestList recipientRequestList = new RecipientRequestList();
            recipientRequestList.setContact_list_ids(list_contacts);
            for (Contact contact : contactList) {
                contact.setUniqueId(contact.getCustomFields().getField50());
                contacts_ids.add(contact.getId());
                manageLog.recordContactLog(Utils.fromContactToContactLog(contact,Constant.CONFIG_SEGUROS_MASIVO,fileName));
            }
            try{
                for (Contact contact : succeeded.getExisting()) {
                    contact.setUniqueId(contact.getCustomFields().getField50());
                    manageLog.recordInvalidRepetedContactLog(Utils.fromContactToInvalidRepetedContactLog(contact,Constant.CONFIG_SEGUROS_MASIVO,fileName,Constant.EMAIL_REPETED));
                }
                for (Contact contact : succeeded.getInvalid()) {
                    contact.setUniqueId(contact.getCustomFields().getField50());
                    manageLog.recordInvalidRepetedContactLog(Utils.fromContactToInvalidRepetedContactLog(contact,Constant.CONFIG_SEGUROS_MASIVO,fileName,Constant.EMAIL_INVALID));
                }
            }catch (Exception ex){
                log.error("Email Existing/Invalid: {}",ex.getMessage());
            }

            recipientRequestList.setContact_list_ids(contacts_ids);

            RecipientRequest recipientRequest = new RecipientRequest();
            recipientRequest.setContact_list_ids(list_contacts.toArray(new String[list_contacts.size()]));
            recipientRequest.setContact_ids(contacts_ids.toArray(new String[contacts_ids.size()]));
            RecipientResponse recipientResponse = surveyMonkeyService.addRecipientBulk(recipientRequest, collectorId, messageId);
            log.info("{}", recipientResponse);
            manageLog.recordRecipientLog(recipientResponse,collectorId,messageId,Constant.CONFIG_SEGUROS_MASIVO);

            ////////////////////////////////////////////////////////////////////////

            SendSurveyRequest sendSurveyRequest = new SendSurveyRequest();
            sendSurveyRequest.setScheduled_date(Utils.getCurrentDateTimeString());
            SendSurveyResponse sendSurveyResponse =surveyMonkeyService.sendSurvey(sendSurveyRequest, collectorId,messageId);
            log.info("{}", sendSurveyResponse);
            Utils.waitMilliSeconds(500);
                } catch (WebClientResponseException e) {
                    // Capturar errores relacionados con la respuesta del servidor
                    log.error("Error al consumir el servicio SurveyMonkey. Código de error: {}", e.getRawStatusCode());
                    log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                    log.error("{}", segurosMasivoList);
                    manageLog.recorErrorlog(Utils.createErrorLog("" + e.getRawStatusCode(), e.getResponseBodyAsString(), "sendSurvey", e.getMessage()));
                } catch (Exception e) {
                    // Capturar otros errores inesperados
                    log.error("Error inesperado al consumir el servicio", e);
                    log.error("{}", segurosMasivoList);
                    manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE, Constant.GENERAL_ERROR_DESCRIPTION, "sendSurvey", e.getMessage()));
                }
            }

        } catch (WebClientResponseException e) {
            // Capturar errores relacionados con la respuesta del servidor
            log.error("Error al consumir el servicio SurveyMonkey. Código de error: {}", e.getRawStatusCode());
            log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
            log.error("{}", segurosMasivoList);
            manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendSurvey",e.getMessage()));
        } catch (Exception e) {
            // Capturar otros errores inesperados
            log.error("Error inesperado al consumir el servicio", e);
            log.error("{}", segurosMasivoList);
            manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendSurvey",e.getMessage()));

        }
    }

    private String getAliasEmail(SegurosMasivo segurosMasivo, String currentDateTime) {
        String email = "";
        email = Utils.emailAlias(segurosMasivo.getEmail(), currentDateTime);
        return email;
    }

    private String getCodigo(SegurosMasivo segurosMasivo, String currentDateTime) {
        String codigo = "";
        codigo = Constant.SEGUROS_MASIVO_CODE+"."+Utils.toStringDateNoSigns(segurosMasivo.getFechaContacto())+"."+segurosMasivo.getTelefono();
        return codigo;
    }

    private String summaryFormat(SegurosMasivo segurosMasivo) {
        return Constant.NAME_SERVICIOS_MASIVO + " " + segurosMasivo.getTelefono();
    }

    private String descriptionFormat(SegurosMasivo segurosMasivo) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(segurosMasivo.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(segurosMasivo.getIdRubro() + "\n");
        descripcion.append("Sponsor: ");
        descripcion.append(Utils.convertCharset(segurosMasivo.getSponsor()) + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(segurosMasivo.getIdCiudad() + "\n");
        descripcion.append("Producto: ");
        descripcion.append(Utils.convertCharset(segurosMasivo.getProducto()) + "\n");
        descripcion.append("Proveedor de Asistencia: ");
        descripcion.append(Utils.convertCharset(segurosMasivo.getProveedorDeAsistencia()) + "\n");
        descripcion.append("Id Servicio: ");
        descripcion.append(Utils.convertCharset(segurosMasivo.getServicio()) + "\n");
        descripcion.append("Fecha contacto: ");
        descripcion.append(segurosMasivo.getFechaContacto() + "\n");
        descripcion.append("Fecha Uso del Servicio: ");
        descripcion.append(segurosMasivo.getFechaUsoServicio() + "\n");
        descripcion.append("Id Documento: ");
        descripcion.append(Utils.convertCharset(segurosMasivo.getIdDocumento()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(segurosMasivo.getNombreSolicitante()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(segurosMasivo.getTelefono() + "\n");
        descripcion.append("Email: ");
        descripcion.append(segurosMasivo.getEmail() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(segurosMasivo.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(segurosMasivo.getIdRubro()));
        replacements.put("sp_value", Utils.valueOrNA(segurosMasivo.getSponsor()));
        replacements.put("iciu_value", Utils.valueOrNA(segurosMasivo.getIdCiudad()));
        replacements.put("prod_value", Utils.valueOrNA(segurosMasivo.getProducto()));
        replacements.put("pa_value", Utils.valueOrNA(segurosMasivo.getProveedorDeAsistencia()));
        replacements.put("ser_value", Utils.valueOrNA(segurosMasivo.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(segurosMasivo.getFechaContacto()));
        replacements.put("fus_value", Utils.valueOrNA(segurosMasivo.getFechaUsoServicio()));
        replacements.put("Idoc_value", Utils.valueOrNA(segurosMasivo.getIdDocumento()));
        replacements.put("nsol_value", Utils.valueOrNA(segurosMasivo.getNombreSolicitante()));
        replacements.put("t_value", Utils.valueOrNA(segurosMasivo.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(segurosMasivo.getEmail()));
        if(segurosMasivo.getUniqueId()!=null && segurosMasivo.getUniqueId().trim().length() > 0){
            replacements.put("idT_value",segurosMasivo.getUniqueId());
        }else {
            replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.SEGUROS_MASIVO_CODE,segurosMasivo.getFechaContacto(),segurosMasivo.getTelefono())));
        }
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/DWVKKNK?ipro=[ipro_value]&irub=[irub_value]&sp=[sp_value]&iciu=[iciu_value]&pro=[prod_value]&pa=[pa_value]&ser=[ser_value]&fcon=[fcon_value]&fus=[fus_value]&Idoc=[Idoc_value]&nsol=[nsol_value]&t=[t_value]&e=[e_value]&idT=[idT_value]";
    }
}
