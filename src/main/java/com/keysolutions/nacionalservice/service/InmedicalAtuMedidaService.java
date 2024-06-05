package com.keysolutions.nacionalservice.service;
import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageSurveyInMemory;
import com.keysolutions.nacionalservice.model.InmedicalAtuMedida;
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
public class InmedicalAtuMedidaService {
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

    private List<InmedicalAtuMedida> inmedicalAtuMedidaList = new ArrayList<>();

    public void addInmedicalAtuMedida(InmedicalAtuMedida inmedicalAtuMedida) {
        if (inmedicalAtuMedida != null) {
            inmedicalAtuMedidaList.add(inmedicalAtuMedida);
        } else {
            log.info("inmedicalAtuMedida vacio: {}", inmedicalAtuMedida);
        }
    }
    public void createJiraSurvey() {
        List<InmedicalAtuMedida> jiraList = new ArrayList<>();
        List<InmedicalAtuMedida> surveyList = new ArrayList<>();
        for (InmedicalAtuMedida data : inmedicalAtuMedidaList) {
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

        inmedicalAtuMedidaList.clear();
    }

    public void sendJira(List<InmedicalAtuMedida> inmedicalAtuMedidaList) {
        for (InmedicalAtuMedida inmedicalAtuMedida : inmedicalAtuMedidaList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(inmedicalAtuMedida));
                requestFieldValues.setDescription(descriptionFormat(inmedicalAtuMedida));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_INMEDICAL_ATU_MEDIDA).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_INMEDICAL_ATU_MEDIDA).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_INMEDICAL_ATU_MEDIDA,fileName,inmedicalAtuMedida.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", inmedicalAtuMedida);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", inmedicalAtuMedida);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private void sendSurvey(List<InmedicalAtuMedida> inmedicalAtuMedidaList) {

        String contactListId = manageSurveyInMemory.getConfigContactList(Constant.CONFIG_NACIONAL_CONTACTS);
        Contacts contacts = new Contacts();
        List<Contact> contactsList = new ArrayList<>();
        String currentDateTime = Utils.getCurrentDateTimeFormatted();
        try {
            for (InmedicalAtuMedida data : inmedicalAtuMedidaList) {

                if (data.getEmail() != null) {
                    Contact contact = new Contact();
                    contact.setEmail(getAliasEmail(data, currentDateTime));
                    contact.setFirstName(data.getEmail());
                    contact.setLastName(getCodigo(data, currentDateTime));
                    CustomFields customFields = new CustomFields();
                    customFields.setField1(data.getTelefono() != null ? data.getTelefono() : "NA");
                    customFields.setField2("NA");
                    customFields.setField3(data.getIdRubro() != null ? data.getIdRubro() : "NA");
                    customFields.setField4(data.getIdCiudad() != null ? data.getIdCiudad() : "NA");
                    customFields.setField5("NA");
                    customFields.setField6("NA");
                    customFields.setField7(Utils.convertCharset(data.getServicio() != null ? data.getServicio() : "NA"));
                    customFields.setField8(Utils.convertCharset(data.getNombreTitular() != null&&data.getNombreTitular().trim().length()>0 ? data.getNombreTitular() : "NA"));
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
                    customFields.setField23("NA");
                    customFields.setField24("NA");
                    customFields.setField25(Utils.convertCharset(data.getProveedor() != null ? data.getProveedor() : "NA"));
                    customFields.setField26("NA");
                    customFields.setField27("NA");
                    customFields.setField28(Utils.convertCharset(data.getIdCentro() != null ? data.getIdCentro() : "NA"));
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
                    customFields.setField41("NA");
                    customFields.setField42("NA");
                    customFields.setField43("NA");
                    customFields.setField44("NA");
                    customFields.setField45("NA");
                    customFields.setField46("NA");
                    customFields.setField47("NA");
                    customFields.setField48("NA");
                    customFields.setField49("NA");
                    customFields.setField50(data.getUniqueId());
                    contact.setCustomFields(customFields);
                    contactsList.add(contact);
                }
            }
            contacts.setContacts(contactsList);
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

            String collectorId = manageSurveyInMemory.getConfigCollector(Constant.CONFIG_INMEDICAL_ATU_MEDIDA);
            String messageConfigId = manageSurveyInMemory.getConfigMessage(Constant.CONFIG_INMEDICAL_ATU_MEDIDA);
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
                manageLog.recordContactLog(Utils.fromContactToContactLog(contact,Constant.CONFIG_INMEDICAL_ATU_MEDIDA,fileName));
            }
            try{
                for (Contact contact : succeeded.getExisting()) {
                    contact.setUniqueId(contact.getCustomFields().getField50());
                    manageLog.recordInvalidRepetedContactLog(Utils.fromContactToInvalidRepetedContactLog(contact,Constant.CONFIG_ATENCION_INICIAL,fileName,Constant.EMAIL_REPETED));
                }
                for (Contact contact : succeeded.getInvalid()) {
                    contact.setUniqueId(contact.getCustomFields().getField50());
                    manageLog.recordInvalidRepetedContactLog(Utils.fromContactToInvalidRepetedContactLog(contact,Constant.CONFIG_ATENCION_INICIAL,fileName,Constant.EMAIL_INVALID));
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
            manageLog.recordRecipientLog(recipientResponse,collectorId,messageId,Constant.CONFIG_INMEDICAL_ATU_MEDIDA);

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
            log.error("{}", inmedicalAtuMedidaList);
            manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendSurvey",e.getMessage()));
        } catch (Exception e) {
            // Capturar otros errores inesperados
            log.error("Error inesperado al consumir el servicio", e);
            log.error("{}", inmedicalAtuMedidaList);
            manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendSurvey",e.getMessage()));

        }
    }

    private String getAliasEmail(InmedicalAtuMedida inmedicalAtuMedida, String currentDateTime) {
        String email = "";
        email = Utils.emailAlias(inmedicalAtuMedida.getEmail(), currentDateTime);
        return email;
    }

    private String getCodigo(InmedicalAtuMedida inmedicalAtuMedida, String currentDateTime) {
        String codigo = "";
        codigo = Constant.INMEDICAL_ATU_MEDIDA_CODE+"."+Utils.toStringDateNoSigns(inmedicalAtuMedida.getFechaContacto())+"."+inmedicalAtuMedida.getTelefono();
        return codigo;
    }

    private String summaryFormat(InmedicalAtuMedida inmedicalAtuMedida) {
        return Constant.NAME_INMEDICAL_ATU_MEDIDA + " " + inmedicalAtuMedida.getTelefono();
    }

    private String descriptionFormat(InmedicalAtuMedida inmedicalAtuMedida) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(inmedicalAtuMedida.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(inmedicalAtuMedida.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(inmedicalAtuMedida.getIdCiudad() + "\n");
        descripcion.append("Proveedor: ");
        descripcion.append(Utils.convertCharset(inmedicalAtuMedida.getProveedor()) + "\n");
        descripcion.append("Id Centro: ");
        descripcion.append(Utils.convertCharset(inmedicalAtuMedida.getIdCentro()) + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(inmedicalAtuMedida.getServicio()) + "\n");
        descripcion.append("Fecha contacto: ");
        descripcion.append(inmedicalAtuMedida.getFechaContacto() + "\n");
        descripcion.append("Id Documento: ");
        descripcion.append(inmedicalAtuMedida.getIdDocumento() + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(inmedicalAtuMedida.getNombreTitular()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(inmedicalAtuMedida.getNombreSolicitante()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(inmedicalAtuMedida.getTelefono() + "\n");
        descripcion.append("Email: ");
        descripcion.append(inmedicalAtuMedida.getEmail() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(inmedicalAtuMedida.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(inmedicalAtuMedida.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(inmedicalAtuMedida.getIdCiudad()));
        replacements.put("ser_value", Utils.valueOrNA(inmedicalAtuMedida.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(inmedicalAtuMedida.getFechaContacto()));
        replacements.put("ntit_value", Utils.valueOrNA(inmedicalAtuMedida.getNombreTitular()));
        replacements.put("nsol_value", Utils.valueOrNA(inmedicalAtuMedida.getNombreSolicitante()));
        replacements.put("t_value", Utils.valueOrNA(inmedicalAtuMedida.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(inmedicalAtuMedida.getEmail()));
        replacements.put("prv_value", Utils.valueOrNA(inmedicalAtuMedida.getProveedor()));
        replacements.put("icen_value", Utils.valueOrNA(inmedicalAtuMedida.getIdCentro()));
        replacements.put("idoc_value", Utils.valueOrNA(inmedicalAtuMedida.getIdDocumento()));
        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.INMEDICAL_ATU_MEDIDA_CODE,inmedicalAtuMedida.getFechaContacto(),inmedicalAtuMedida.getTelefono())));
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/XZW8B58?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&ser=[ser_value]&fcon=[fcon_value]&ntit=[ntit_value]&nsol=[nsol_value]&t=[t_value]&e=[e_value]&prv=[prv_value]&icen=[icen_value]&idoc=[idoc_value]&idT=[idT_value]";
    }
}
