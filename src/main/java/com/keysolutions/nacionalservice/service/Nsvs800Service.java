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
import com.keysolutions.nacionalservice.database.ManageSurveyInMemory;
import com.keysolutions.nacionalservice.model.Nsvs800;
import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.model.survey.Contact;
import com.keysolutions.nacionalservice.model.survey.Contacts;
import com.keysolutions.nacionalservice.model.survey.CustomFields;
import com.keysolutions.nacionalservice.model.survey.MessageResponse;
import com.keysolutions.nacionalservice.model.survey.RecipientRequest;
import com.keysolutions.nacionalservice.model.survey.RecipientRequestList;
import com.keysolutions.nacionalservice.model.survey.RecipientResponse;
import com.keysolutions.nacionalservice.model.survey.Succeeded;
import com.keysolutions.nacionalservice.util.Constant;
import com.keysolutions.nacionalservice.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Nsvs800Service {
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


    private List<Nsvs800> nSvs800List = new ArrayList<>();

    public void addNsvs800(Nsvs800 nsvs800) {
        if (nsvs800 != null) {
            nSvs800List.add(nsvs800);
        } else {
            log.info("Nsvs800 vacio: {}", nsvs800);
        }
    }

    public void createJiraSurvey() {
        List<Nsvs800> jiraList = new ArrayList<>();
        List<Nsvs800> surveyList = new ArrayList<>();
        for (Nsvs800 data : nSvs800List) {
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
                log.info("======NO TIENE RECOPILADOR PARA SEND-SURVEY==========");
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION, "sendSurvey", fileName + " NO TIENE RECOPILADOR PARA SEND-SURVEY"));
                //sendSurvey(surveyList);
            }
        }
        nSvs800List.clear();
    }

    private void sendJira(List<Nsvs800> nsvs800List) {
        for (Nsvs800 nsvs800 : nsvs800List) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(nsvs800));
                requestFieldValues.setDescription(descriptionFormat(nsvs800));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_NSVS_800).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_NSVS_800).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(), issue.getIssueKey(), issue.getRequestTypeId(), issue.getServiceDeskId(), Constant.CONFIG_NSVS_800, fileName,nsvs800.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", nsvs800);
                manageLog.recorErrorlog(Utils.createErrorLog("" + e.getRawStatusCode(), e.getResponseBodyAsString(),
                        "sendJira", e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", nsvs800);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE, Constant.GENERAL_ERROR_DESCRIPTION, "sendJira", e.getMessage()));
            }

        }
    }

    private void sendSurvey(List<Nsvs800> nSvs800List) {

        String contactListId = manageSurveyInMemory.getConfigContactList(Constant.CONFIG_NACIONAL_CONTACTS);
        Contacts contacts = new Contacts();
        List<Contact> contactsList = new ArrayList<>();
        String currentDateTime = Utils.getCurrentDateTimeFormatted();
        try {
            for (Nsvs800 data : nSvs800List) {
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
                    customFields.setField5(data.getIdPoliza() != null ? data.getIdPoliza() : "NA");
                    customFields.setField6(Utils.convertCharset(data.getIdOperador() != null ? data.getIdOperador() : "NA"));
                    customFields.setField7(Utils.convertCharset(data.getServicio() != null ? data.getServicio() : "NA"));
                    customFields.setField8(Utils.convertCharset(data.getNombreTitular() != null ? data.getNombreTitular() : "NA"));
                    customFields.setField9(Utils.convertCharset(data.getNombreSolicitante() != null ? data.getNombreSolicitante() : "NA"));
                    customFields.setField10("NA");
                    customFields.setField11("NA");
                    customFields.setField12(data.getFechaContacto() != null ? data.getFechaContacto() : "NA");
                    customFields.setField13("NA");
                    customFields.setField14("NA");
                    customFields.setField15("NA");
                    customFields.setField16("NA");
                    customFields.setField17("NA");
                    customFields.setField18("NA");
                    customFields.setField19("NA");
                    customFields.setField20("NA");
                    customFields.setField21("NA");
                    customFields.setField22("NA");
                    customFields.setField23("NA");
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
                    customFields.setField40(Utils.convertCharset(data.getIdObjeto() != null ? data.getIdObjeto() : "NA"));
                    customFields.setField41("NA");
                    customFields.setField42("NA");
                    customFields.setField43("NA");
                    customFields.setField44("NA");
                    customFields.setField45("NA");
                    customFields.setField46("NA");
                    customFields.setField47("NA");
                    customFields.setField48("NA");
                    customFields.setField49("NA");
                    customFields.setField50("NA");
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

                    String collectorId = manageSurveyInMemory.getConfigCollector(Constant.CONFIG_NSVS_800);
                    String messageConfigId = manageSurveyInMemory.getConfigMessage(Constant.CONFIG_NSVS_800);
                    MessageResponse messageResponse = surveyMonkeyService.getCollectorMessages(collectorId, messageConfigId);
                    String messageId = messageResponse.getId();
                    Utils.waitMilliSeconds(500);
                    /////////////////////////////////////////////////////////////////////////
                    List<String> list_contacts = new ArrayList<>();
                    List<String> contacts_ids = new ArrayList<>();

                    RecipientRequestList recipientRequestList = new RecipientRequestList();
                    recipientRequestList.setContact_list_ids(list_contacts);
                    for (Contact contact : contactList) {
                        contacts_ids.add(contact.getId());
                        manageLog.recordContactLog(Utils.fromContactToContactLog(contact, Constant.CONFIG_NSVS_800, fileName));
                    }

                    recipientRequestList.setContact_list_ids(contacts_ids);

                    RecipientRequest recipientRequest = new RecipientRequest();
                    recipientRequest.setContact_list_ids(list_contacts.toArray(new String[list_contacts.size()]));
                    recipientRequest.setContact_ids(contacts_ids.toArray(new String[contacts_ids.size()]));
                    RecipientResponse recipientResponse = surveyMonkeyService.addRecipientBulk(recipientRequest, collectorId, messageId);
                    log.info("{}", recipientResponse);
                    manageLog.recordRecipientLog(recipientResponse, collectorId, messageId, Constant.CONFIG_NSVS_800);

                    ////////////////////////////////////////////////////////////////////////
                    /*
                     * SendSurveyRequest sendSurveyRequest = new SendSurveyRequest();
                     * // sendSurveyRequest.setScheduled_date("2024-03-30T09:30:00+00:00");
                     * sendSurveyRequest.setScheduled_date(Utils.getCurrentDateTimeString());
                     * SendSurveyResponse sendSurveyResponse =
                     * surveyMonkeyService.sendSurvey(sendSurveyRequest, collectorId,messageId);
                     * log.info("{}", sendSurveyResponse);
                     * Utils.waitMilliSeconds(500);
                     */

                } catch (WebClientResponseException e) {
                    // Capturar errores relacionados con la respuesta del servidor
                    log.error("Error al consumir el servicio SurveyMonkey. Código de error: {}", e.getRawStatusCode());
                    log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                    log.error("{}", nSvs800List);
                    manageLog.recorErrorlog(Utils.createErrorLog("" + e.getRawStatusCode(), e.getResponseBodyAsString(), "sendSurvey", e.getMessage()));
                } catch (Exception e) {
                    // Capturar otros errores inesperados
                    log.error("Error inesperado al consumir el servicio", e);
                    log.error("{}", nSvs800List);
                    manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE, Constant.GENERAL_ERROR_DESCRIPTION, "sendSurvey", e.getMessage()));
                }
            }

        } catch (WebClientResponseException e) {
            // Capturar errores relacionados con la respuesta del servidor
            log.error("Error al consumir el servicio SurveyMonkey. Código de error: {}", e.getRawStatusCode());
            log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
            log.error("{}", nSvs800List);
            manageLog.recorErrorlog(Utils.createErrorLog("" + e.getRawStatusCode(), e.getResponseBodyAsString(), "sendSurvey", e.getMessage()));
        } catch (Exception e) {
            // Capturar otros errores inesperados
            log.error("Error inesperado al consumir el servicio", e);
            log.error("{}", nSvs800List);
            manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE, Constant.GENERAL_ERROR_DESCRIPTION, "sendSurvey", e.getMessage()));

        }
    }

    private String getAliasEmail(Nsvs800 nsvs800, String currentDateTime) {
        String email = "";
        email = Utils.emailAlias(nsvs800.getEmail(), currentDateTime);
        return email;
    }

    private String getCodigo(Nsvs800 nsvs800, String currentDateTime) {
        String codigo = "";
        codigo = Constant.NSVS800_CODE + "." + Utils.toStringDateNoSigns(nsvs800.getFechaContacto()) + "." + nsvs800.getTelefono();
        return codigo;
    }

    private String summaryFormat(Nsvs800 nsvs800) {
        return Constant.NAME_NSVS_800 + " " + nsvs800.getTelefono();
    }

    private String descriptionFormat(Nsvs800 nsvs800) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(nsvs800.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(nsvs800.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(nsvs800.getIdCiudad() + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(nsvs800.getIdOperador()) + "\n");
        descripcion.append("Id Servicio: ");
        descripcion.append(Utils.convertCharset(nsvs800.getServicio()) + "\n");
        descripcion.append("Fecha contacto: ");
        descripcion.append(nsvs800.getFechaContacto() + "\n");
        descripcion.append("Id Objeto: ");
        descripcion.append(Utils.convertCharset(nsvs800.getIdObjeto()) + "\n");
        descripcion.append("Nombre Titular: ");
        descripcion.append(Utils.convertCharset(nsvs800.getNombreTitular()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(nsvs800.getNombreSolicitante()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(nsvs800.getTelefono() + "\n");
        descripcion.append("Email: ");
        descripcion.append(nsvs800.getEmail() + "\n");
        descripcion.append("Id Poliza: ");
        descripcion.append(nsvs800.getIdPoliza() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(nsvs800.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(nsvs800.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(nsvs800.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(Utils.convertCharset(nsvs800.getIdOperador())));
        replacements.put("ser_value", Utils.valueOrNA(Utils.convertCharset(nsvs800.getServicio())));
        replacements.put("fcon_value", Utils.valueOrNA(nsvs800.getFechaContacto()));
        replacements.put("iobj_value", Utils.valueOrNA(Utils.convertCharset(nsvs800.getIdObjeto())));
        replacements.put("ntit_value", Utils.valueOrNA(Utils.convertCharset(nsvs800.getNombreTitular())));
        replacements.put("nsol_value", Utils.valueOrNA(Utils.convertCharset(nsvs800.getNombreSolicitante())));
        replacements.put("t_value", Utils.valueOrNA(nsvs800.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(nsvs800.getEmail()));
        replacements.put("ipol_value", Utils.valueOrNA(nsvs800.getIdPoliza()));
        if(nsvs800.getUniqueId()!=null && nsvs800.getUniqueId().trim().length() > 0){
            replacements.put("idT_value",nsvs800.getUniqueId());
        }else {
            replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.NSVS800_CODE, nsvs800.getFechaContacto(), nsvs800.getTelefono())));
        }
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/X7TNSMH?ipro=[ipro_value]&t=[t_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&ser=[ser_value]&fcon=[fcon_value]&iobj=[iobj_value]&ntit=[ntit_value]&nsol=[nsol_value]&e=[e_value]&ipol=[ipol_value]&idT=[idT_value]";
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
