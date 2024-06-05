package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageSurveyInMemory;
import com.keysolutions.nacionalservice.model.CentroRehaOdonto;
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
public class CentroRehaOdontoService {
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



    private List<CentroRehaOdonto> centroRehaOdontoList = new ArrayList<>();

    public void addCentroRehaOdonto(CentroRehaOdonto centroRehaOdonto) {
        if (centroRehaOdonto != null) {
            centroRehaOdontoList.add(centroRehaOdonto);
        } else {
            log.info("centroRehaOdonto vacio: {}", centroRehaOdonto);
        }
    }

    public void createJiraSurvey() {
        List<CentroRehaOdonto> jiraList = new ArrayList<>();
        List<CentroRehaOdonto> surveyList = new ArrayList<>();
        for (CentroRehaOdonto data : centroRehaOdontoList) {
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
                log.info("======SEND-SURVEY==========");
                sendSurvey(surveyList);
        }

        centroRehaOdontoList.clear();
    }

    public void sendJira(List<CentroRehaOdonto> centroRehaOdontoList) {
        for (CentroRehaOdonto centroRehaOdonto : centroRehaOdontoList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(centroRehaOdonto));
                requestFieldValues.setDescription(descriptionFormat(centroRehaOdonto));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(), issue.getIssueKey(),issue.getRequestTypeId(), issue.getServiceDeskId(), Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA,fileName,centroRehaOdonto.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", centroRehaOdonto);
                manageLog.recorErrorlog(Utils.createErrorLog("" + e.getRawStatusCode(), e.getResponseBodyAsString(),
                        "sendJira", e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", centroRehaOdonto);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE, Constant.GENERAL_ERROR_DESCRIPTION, "sendJira", e.getMessage()));
            }

        }
    }

    private void sendSurvey(List<CentroRehaOdonto> centroRehaOdontoList) {

        String contactListId = manageSurveyInMemory.getConfigContactList(Constant.CONFIG_NACIONAL_CONTACTS);
        Contacts contacts = new Contacts();
        List<Contact> contactsList = new ArrayList<>();
        String currentDateTime = Utils.getCurrentDateTimeFormatted();
        try {
            for (CentroRehaOdonto data : centroRehaOdontoList) {
                if (data.getEmail() != null) {
                    Contact contact = new Contact();
                    contact.setEmail(getAliasEmail(data, currentDateTime));
                    contact.setFirstName(data.getEmail());
                    contact.setLastName(getCodigo(data, currentDateTime));
                    CustomFields customFields = new CustomFields();
                    customFields.setField1(data.getTelefono() != null ? data.getTelefono() : "NA");
                    customFields.setField2( "NA");
                    customFields.setField3(data.getIdRubro() != null ? data.getIdRubro() : "NA");
                    customFields.setField4(data.getIdCiudad() != null ? data.getIdCiudad() : "NA");
                    customFields.setField5("NA");
                    customFields.setField6(Utils.convertCharset(data.getIdOperador() != null ? data.getIdOperador() : "NA"));
                    customFields.setField7("NA");
                    customFields.setField8("NA");
                    customFields.setField9("NA");
                    customFields.setField10( "NA");
                    customFields.setField11( "NA");
                    customFields.setField12(data.getFechaContacto() != null ? data.getFechaContacto() : "NA");
                    customFields.setField13( "NA");
                    customFields.setField14( "NA");
                    customFields.setField15("NA");
                    customFields.setField16("NA");
                    customFields.setField17("NA");
                    customFields.setField18("NA");
                    customFields.setField19(Utils.convertCharset(data.getPlan() != null ? data.getPlan() : "NA"));
                    customFields.setField20("NA");
                    customFields.setField21("NA");
                    customFields.setField22("NA");
                    customFields.setField23("NA");
                    customFields.setField24("NA");
                    customFields.setField25("NA");
                    customFields.setField26("NA");
                    customFields.setField27("NA");
                    customFields.setField28("NA");
                    customFields.setField29(Utils.convertCharset(data.getCentroMedico() != null ? data.getCentroMedico() : "NA"));
                    customFields.setField30("NA");
                    customFields.setField31("NA");
                    customFields.setField32("NA");
                    customFields.setField33(Utils.convertCharset(data.getPaciente() != null ? data.getPaciente() : "NA"));
                    customFields.setField34("NA");
                    customFields.setField35("NA");
                    customFields.setField36("NA");
                    customFields.setField37("NA");
                    customFields.setField38("NA");
                    customFields.setField39(data.getIdProceso() != null ? data.getIdProceso() : "NA");
                    customFields.setField40("NA");
                    customFields.setField41("NA");
                    customFields.setField42(data.getFechaSalida() != null ? data.getFechaSalida() : "NA");
                    customFields.setField43("NA");
                    customFields.setField44("NA");
                    customFields.setField45("NA");
                    customFields.setField46("NA");
                    customFields.setField47("NA");
                    customFields.setField48("NA");
                    customFields.setField49(data.getTelefonoAdicional() != null ? data.getTelefonoAdicional() : "NA");
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

            String collectorId = manageSurveyInMemory.getConfigCollector(Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA);
            String messageConfigId = manageSurveyInMemory.getConfigMessage(Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA);
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
                manageLog.recordContactLog(Utils.fromContactToContactLog(contact,Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA,fileName));
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
            manageLog.recordRecipientLog(recipientResponse,collectorId,messageId,Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA);

            ////////////////////////////////////////////////////////////////////////

              SendSurveyRequest sendSurveyRequest = new SendSurveyRequest();
              sendSurveyRequest.setScheduled_date(Utils.getCurrentDateTimeString());
              SendSurveyResponse sendSurveyResponse =
              surveyMonkeyService.sendSurvey(sendSurveyRequest, collectorId,messageId);
              log.info("{}", sendSurveyResponse);
              Utils.waitMilliSeconds(500);


        } catch (WebClientResponseException e) {
            // Capturar errores relacionados con la respuesta del servidor
            log.error("Error al consumir el servicio SurveyMonkey. Código de error: {}", e.getRawStatusCode());
            log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
            log.error("{}", centroRehaOdontoList);
            manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendSurvey",e.getMessage()));
        } catch (Exception e) {
            // Capturar otros errores inesperados
            log.error("Error inesperado al consumir el servicio", e);
            log.error("{}", centroRehaOdontoList);
            manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendSurvey",e.getMessage()));

        }
    }

    private String getAliasEmail(CentroRehaOdonto centroRehaOdonto, String currentDateTime) {
        String email = "";
        email = Utils.emailAlias(centroRehaOdonto.getEmail(), currentDateTime);
        return email;
    }

    private String getCodigo(CentroRehaOdonto centroRehaOdonto, String currentDateTime) {
        String codigo = "";
        codigo = Constant.CENTRO_REHA_ODONTOLOGICA_CODE+"."+Utils.toStringDateNoSigns(centroRehaOdonto.getFechaContacto())+"."+centroRehaOdonto.getTelefono();
        return codigo;
    }

    private String summaryFormat(CentroRehaOdonto centroRehaOdonto) {
        return Constant.NAME_CENTRO_REHA_ODONTOLOGICA + " " + centroRehaOdonto.getTelefono();
    }

    private String descriptionFormat(CentroRehaOdonto centroRehaOdonto) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proceso: ");
        descripcion.append(centroRehaOdonto.getIdProceso() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(centroRehaOdonto.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(centroRehaOdonto.getIdCiudad() + "\n");
        descripcion.append("Id Operador: ");
        descripcion.append(Utils.convertCharset(centroRehaOdonto.getIdOperador()) + "\n");
        descripcion.append("Centro Médico: ");
        descripcion.append(Utils.convertCharset(centroRehaOdonto.getCentroMedico()) + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(centroRehaOdonto.getFechaContacto() + "\n");
        descripcion.append("Fecha Salida: ");
        descripcion.append(centroRehaOdonto.getFechaSalida() + "\n");
        descripcion.append("Paciente: ");
        descripcion.append(Utils.convertCharset(centroRehaOdonto.getPaciente()) + "\n");
        descripcion.append("Plan: ");
        descripcion.append(Utils.convertCharset(centroRehaOdonto.getPlan()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(centroRehaOdonto.getTelefono() + "\n");
        descripcion.append("Email: ");
        descripcion.append(centroRehaOdonto.getEmail() + "\n");
        descripcion.append("Teléfono Adicional: ");
        descripcion.append(centroRehaOdonto.getTelefonoAdicional() + "\n");
        descripcion.append("\n\n");

        replacements.put("ipro_value", Utils.valueOrNA(centroRehaOdonto.getIdProceso()));
        replacements.put("irub_value", Utils.valueOrNA(centroRehaOdonto.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(centroRehaOdonto.getIdCiudad()));
        replacements.put("iope_value", Utils.valueOrNA(centroRehaOdonto.getIdOperador()));
        replacements.put("fcon_value", Utils.valueOrNA(centroRehaOdonto.getFechaContacto()));
        replacements.put("t_value", Utils.valueOrNA(centroRehaOdonto.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(centroRehaOdonto.getEmail()));
        replacements.put("cmed_value", Utils.valueOrNA(centroRehaOdonto.getCentroMedico()));
        replacements.put("fsal_value", Utils.valueOrNA(centroRehaOdonto.getFechaSalida()));
        replacements.put("pln_value", Utils.valueOrNA(centroRehaOdonto.getPlan()));
        replacements.put("pac_value", Utils.valueOrNA(centroRehaOdonto.getPaciente()));
        replacements.put("tadi_value", Utils.valueOrNA(centroRehaOdonto.getTelefonoAdicional()));
        replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.CENTRO_REHA_ODONTOLOGICA_CODE,centroRehaOdonto.getFechaContacto(),centroRehaOdonto.getTelefono())));
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/X5R3RBS?ipro=[ipro_value]&irub=[irub_value]&iciu=[iciu_value]&iope=[iope_value]&fcon=[fcon_value]&t=[t_value]&e=[e_value]&cmed=[cmed_value]&fsal=[fsal_value]&pln=[pln_value]&pac=[pac_value]&tadi=[tadi_value]&idT=[idT_value]";
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
