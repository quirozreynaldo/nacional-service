package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageSurveyInMemory;
import com.keysolutions.nacionalservice.model.TallersE2e;
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
public class TallersE2eService {
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

    private List<TallersE2e> tallersE2eList = new ArrayList<>();

    public void addTalleresE2e(TallersE2e tallersE2e) {
        if (tallersE2e != null) {
            tallersE2eList.add(tallersE2e);
        } else {
            log.info("tallersE2e vacio: {}", tallersE2e);
        }
    }

    public void createJiraSurvey() {
        List<TallersE2e> jiraList = new ArrayList<>();
        List<TallersE2e> surveyList = new ArrayList<>();
        for (TallersE2e data : tallersE2eList) {
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

        tallersE2eList.clear();
    }

    public void sendJira(List<TallersE2e> tallersE2eList) {
        for (TallersE2e tallersE2e : tallersE2eList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(tallersE2e));
                requestFieldValues.setDescription(descriptionFormat(tallersE2e));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_TALLERES_E2E).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_TALLERES_E2E).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_TALLERES_E2E,fileName,tallersE2e.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", tallersE2e);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", tallersE2e);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }
    private void sendSurvey(List<TallersE2e> tallersE2eList) {

        String contactListId = manageSurveyInMemory.getConfigContactList(Constant.CONFIG_NACIONAL_CONTACTS);
        Contacts contacts = new Contacts();
        List<Contact> contactsList = new ArrayList<>();
        String currentDateTime = Utils.getCurrentDateTimeFormatted();
        try {
            for (TallersE2e data : tallersE2eList) {

                if (data.getEmail() != null) {
                    Contact contact = new Contact();
                    contact.setEmail(getAliasEmail(data, currentDateTime));
                    contact.setFirstName(data.getEmail());
                    contact.setLastName(getCodigo(data, currentDateTime));
                    CustomFields customFields = new CustomFields();
                    customFields.setField1(data.getTelefono() != null ? data.getTelefono() : "NA");
                    customFields.setField2(data.getPlaca() != null ? data.getPlaca() : "NA");
                    customFields.setField3(data.getIdRubro() != null ? data.getIdRubro() : "NA");
                    customFields.setField4(data.getIdCiudad() != null ? data.getIdCiudad() : "NA");
                    customFields.setField5(data.getIdPoliza() != null ? data.getIdPoliza() : "NA");
                    customFields.setField6(Utils.convertCharset(data.getIdOperador() != null ? data.getIdOperador() : "NA"));
                    customFields.setField7(Utils.convertCharset(data.getServicio() != null ? data.getServicio() : "NA"));
                    customFields.setField8(Utils.convertCharset(data.getNombreTitular() != null&&data.getNombreTitular().trim().length()>0 ? data.getNombreTitular() : "NA"));
                    customFields.setField9(Utils.convertCharset(data.getNombreSolicitante() != null ? data.getNombreSolicitante() : "NA"));
                    customFields.setField10("NA");
                    customFields.setField11("NA");
                    customFields.setField12(data.getFechaContacto() != null ? data.getFechaContacto() : "NA");
                    customFields.setField13(Utils.convertCharset(data.getMarca() != null ? data.getMarca() : "NA"));
                    customFields.setField14(Utils.convertCharset(data.getModelo() != null ? data.getModelo() : "NA"));
                    customFields.setField15("NA");
                    customFields.setField16("NA");
                    customFields.setField17("NA");
                    customFields.setField18("NA");
                    customFields.setField19("NA");
                    customFields.setField20("NA");
                    customFields.setField21("NA");
                    customFields.setField22(Utils.convertCharset(data.getIntermediario() != null ? data.getIntermediario() : "NA"));
                    customFields.setField23("NA");
                    customFields.setField24("NA");
                    customFields.setField25("NA");
                    customFields.setField26("NA");
                    customFields.setField27("NA");
                    customFields.setField28("NA");
                    customFields.setField29("NA");
                    customFields.setField30("NA");
                    customFields.setField31("NA");
                    customFields.setField32(data.getPeriodo() != null ? data.getPeriodo() : "NA");
                    customFields.setField33("NA");
                    customFields.setField34("NA");
                    customFields.setField35("NA");
                    customFields.setField36("NA");
                    customFields.setField37("NA");
                    customFields.setField38("NA");
                    customFields.setField39(data.getIdProceso() != null ? data.getIdProceso() : "NA");
                    customFields.setField40("NA");
                    customFields.setField41(Utils.convertCharset(data.getColor() != null ? data.getColor() : "NA"));
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

            String collectorId = manageSurveyInMemory.getConfigCollector(Constant.CONFIG_TALLERES_E2E);
            String messageConfigId = manageSurveyInMemory.getConfigMessage(Constant.CONFIG_TALLERES_E2E);
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
                manageLog.recordContactLog(Utils.fromContactToContactLog(contact,Constant.CONFIG_TALLERES_E2E,fileName));
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
            manageLog.recordRecipientLog(recipientResponse,collectorId,messageId,Constant.CONFIG_TALLERES_E2E);

            ////////////////////////////////////////////////////////////////////////

            SendSurveyRequest sendSurveyRequest = new SendSurveyRequest();
            sendSurveyRequest.setScheduled_date(Utils.getCurrentDateTimeString());
            SendSurveyResponse sendSurveyResponse = surveyMonkeyService.sendSurvey(sendSurveyRequest, collectorId,messageId);
            log.info("{}", sendSurveyResponse);
            Utils.waitMilliSeconds(500);


        } catch (WebClientResponseException e) {
            // Capturar errores relacionados con la respuesta del servidor
            log.error("Error al consumir el servicio SurveyMonkey. Código de error: {}", e.getRawStatusCode());
            log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
            log.error("{}", tallersE2eList);
            manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendSurvey",e.getMessage()));
        } catch (Exception e) {
            // Capturar otros errores inesperados
            log.error("Error inesperado al consumir el servicio", e);
            log.error("{}", tallersE2eList);
            manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendSurvey",e.getMessage()));

        }
    }

    private String getAliasEmail(TallersE2e tallersE2e, String currentDateTime) {
        String email = "";
        email = Utils.emailAlias(tallersE2e.getEmail(), currentDateTime);
        return email;
    }

    private String getCodigo(TallersE2e tallersE2e, String currentDateTime) {
        String codigo = "";
        codigo = Constant.TALLER_E2E_CODE+"."+Utils.toStringDateNoSigns(tallersE2e.getFechaContacto())+"."+tallersE2e.getTelefono();
        return codigo;
    }

    private String summaryFormat(TallersE2e tallersE2e) {
        return Constant.NAME_TALLERES_E2E + " " + tallersE2e.getTelefono();
    }
    private String descriptionFormat(TallersE2e tallersE2e) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(tallersE2e.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(tallersE2e.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(tallersE2e.getIdCiudad() + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getIdOperador()) + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getServicio()) + "\n");
        descripcion.append("Fecha contacto: ");
        descripcion.append(tallersE2e.getFechaContacto() + "\n");
        descripcion.append("Periodo: ");
        descripcion.append(tallersE2e.getPeriodo() + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getNombreTitular()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getNombreSolicitante()) + "\n");
        descripcion.append("Télefono: ");
        descripcion.append(tallersE2e.getTelefono() + "\n");
        descripcion.append("Marca: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getMarca()) + "\n");
        descripcion.append("Modelo: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getModelo()) + "\n");
        descripcion.append("Placa: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getPlaca()) + "\n");
        descripcion.append("Color: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getColor()) + "\n");
        descripcion.append("Id Poliza: ");
        descripcion.append(Utils.convertCharset(tallersE2e.getIdPoliza()) + "\n");
        descripcion.append("Intermediario: ");
        descripcion.append(tallersE2e.getIntermediario() + "\n");
        descripcion.append("Email: ");
        descripcion.append(tallersE2e.getEmail() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(tallersE2e.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(tallersE2e.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(tallersE2e.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(tallersE2e.getIdOperador()));
        replacements.put("ser_value", Utils.valueOrNA(tallersE2e.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(tallersE2e.getFechaContacto()));
        replacements.put("ntit_value", Utils.valueOrNA(tallersE2e.getNombreTitular()));
        replacements.put("nsol_value", Utils.valueOrNA(tallersE2e.getNombreSolicitante()));
        replacements.put("t_value", Utils.valueOrNA(tallersE2e.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(tallersE2e.getEmail()));
        replacements.put("ipol_value", Utils.valueOrNA(tallersE2e.getIdPoliza()));
        replacements.put("pla_value", Utils.valueOrNA(tallersE2e.getPlaca()));
        replacements.put("int_value", Utils.valueOrNA(tallersE2e.getIntermediario()));
        replacements.put("per_value", Utils.valueOrNA(tallersE2e.getPeriodo()));
        replacements.put("mar_value", Utils.valueOrNA(tallersE2e.getMarca()));
        replacements.put("mod_value", Utils.valueOrNA(tallersE2e.getModelo()));
        replacements.put("col_value", Utils.valueOrNA(tallersE2e.getColor()));
        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.TALLER_E2E_CODE,tallersE2e.getFechaContacto(),tallersE2e.getTelefono())));
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/95LS6JH?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&ser=[ser_value]&fcon=[fcon_value]&ntit=[ntit_value]&nsol=[nsol_value]&t=[t_value]&e=[e_value]&ipol=[ipol_value]&pla=[pla_value]&int=[int_value]&per=[per_value]&mar=[mar_value]&mod=[mod_value]&col=[col_value]&idT=[idT_value]";
    }
}
