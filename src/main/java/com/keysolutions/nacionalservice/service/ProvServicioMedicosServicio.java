package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageJiraInMemory;
import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageSurveyInMemory;
import com.keysolutions.nacionalservice.model.ProvServicioMedico;
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
public class ProvServicioMedicosServicio {
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

    private List<ProvServicioMedico> provServicioMedicoList = new ArrayList<>();

    public void addProvServicioMedico(ProvServicioMedico provServicioMedico) {
        if (provServicioMedico != null) {
            provServicioMedicoList.add(provServicioMedico);
        } else {
            log.info("provServicioMedico vacio: {}", provServicioMedico);
        }
    }
    public void createJiraSurvey() {
        List<ProvServicioMedico> jiraList = new ArrayList<>();
        List<ProvServicioMedico> surveyList = new ArrayList<>();
        for (ProvServicioMedico data : provServicioMedicoList) {
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

        provServicioMedicoList.clear();
    }

    public void sendJira(List<ProvServicioMedico> provServicioMedicoList) {
        for (ProvServicioMedico provServicioMedico : provServicioMedicoList) {
            try {
                TicketRequest ticketRequest = new TicketRequest();
                RequestFieldValues requestFieldValues = new RequestFieldValues();
                requestFieldValues.setSummary(summaryFormat(provServicioMedico));
                requestFieldValues.setDescription(descriptionFormat(provServicioMedico));
                ticketRequest.setRequestFieldValues(requestFieldValues);
                ticketRequest.setRequestTypeId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_PROV_SERV_MEDICO).getRequestTypeId());
                ticketRequest.setServiceDeskId(manageJiraInMemory.getServiceDeskInfo(Constant.CONFIG_PROV_SERV_MEDICO).getServiceDeskId());
                Issue issue = jiraService.createJiraTicket(ticketRequest);
                log.info("issue {}", issue);
                manageLog.recorJiralog(Utils.createJiraLog(issue.getIssueId(),issue.getIssueKey(),issue.getRequestTypeId(),issue.getServiceDeskId(),Constant.CONFIG_PROV_SERV_MEDICO,fileName,provServicioMedico.getUniqueId()));
            } catch (WebClientResponseException e) {
                log.error("Error al consumir el servicio Jira. Código de error: {}", e.getRawStatusCode());
                log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
                log.error("{}", provServicioMedico);
                manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendJira",e.getMessage()));
            } catch (Exception e) {
                log.error("Error inesperado al consumir el servicio", e);
                log.error("{}", provServicioMedico);
                manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendJira",e.getMessage()));
            }

        }
    }

    private void sendSurvey(List<ProvServicioMedico> provServicioMedicoList) {

        String contactListId = manageSurveyInMemory.getConfigContactList(Constant.CONFIG_NACIONAL_CONTACTS);
        Contacts contacts = new Contacts();
        List<Contact> contactsList = new ArrayList<>();
        String currentDateTime = Utils.getCurrentDateTimeFormatted();
        try {
            for (ProvServicioMedico data : provServicioMedicoList) {

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
                    customFields.setField5(data.getPoliza() != null ? data.getPoliza() : "NA");
                    customFields.setField6("NA");
                    customFields.setField7(Utils.convertCharset(data.getServicio() != null ? data.getServicio() : "NA"));
                    customFields.setField8("NA");
                    customFields.setField9(Utils.convertCharset(data.getNombreSolicitante() != null ? data.getNombreSolicitante() : "NA"));
                    customFields.setField10("NA");
                    customFields.setField11(Utils.convertCharset(data.getNombreAsegurado() != null ? data.getNombreAsegurado() : "NA"));
                    customFields.setField12(data.getFechaContacto() != null ? data.getFechaContacto() : "NA");
                    customFields.setField13("NA");
                    customFields.setField14("NA");
                    customFields.setField15(data.getFechaDenuncia() != null ? data.getFechaDenuncia() : "NA");
                    customFields.setField16("NA");
                    customFields.setField17(Utils.convertCharset(data.getIdEjecutivo() != null ? data.getIdEjecutivo() : "NA"));
                    customFields.setField18("NA");
                    customFields.setField19("NA");
                    customFields.setField20("NA");
                    customFields.setField21(Utils.convertCharset(data.getDignostico() != null ? data.getDignostico() : "NA"));
                    customFields.setField22(Utils.convertCharset(data.getIntermediario() != null ? data.getIntermediario() : "NA"));
                    customFields.setField23(Utils.convertCharset(data.getProducto() != null ? data.getProducto() : "NA"));
                    customFields.setField24(data.getIdProveedor() != null ? data.getIdProveedor() : "NA");
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
                    customFields.setField39("NA");
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

            String collectorId = manageSurveyInMemory.getConfigCollector(Constant.CONFIG_PROV_SERV_MEDICO);
            String messageConfigId = manageSurveyInMemory.getConfigMessage(Constant.CONFIG_PROV_SERV_MEDICO);
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
                manageLog.recordContactLog(Utils.fromContactToContactLog(contact,Constant.CONFIG_PROV_SERV_MEDICO,fileName));
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
            manageLog.recordRecipientLog(recipientResponse,collectorId,messageId,Constant.CONFIG_PROV_SERV_MEDICO);

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
            log.error("{}", provServicioMedicoList);
            manageLog.recorErrorlog(Utils.createErrorLog(""+e.getRawStatusCode(),e.getResponseBodyAsString(),"sendSurvey",e.getMessage()));
        } catch (Exception e) {
            // Capturar otros errores inesperados
            log.error("Error inesperado al consumir el servicio", e);
            log.error("{}", provServicioMedicoList);
            manageLog.recorErrorlog(Utils.createErrorLog(Constant.GENERAL_ERROR_CODE,Constant.GENERAL_ERROR_DESCRIPTION,"sendSurvey",e.getMessage()));

        }
    }

    private String getAliasEmail(ProvServicioMedico provServicioMedico, String currentDateTime) {
        String email = "";
        email = Utils.emailAlias(provServicioMedico.getEmail(), currentDateTime);
        return email;
    }

    private String getCodigo(ProvServicioMedico provServicioMedico, String currentDateTime) {
        String codigo = "";
        codigo = Constant.PROV_SERV_MEDICO_CODE+"."+Utils.toStringDateNoSigns(provServicioMedico.getFechaContacto())+"."+provServicioMedico.getTelefono();
        return codigo;
    }

    private String summaryFormat(ProvServicioMedico provServicioMedico) {
        return Constant.NAME_PROV_SERV_MEDICO + " " + provServicioMedico.getTelefono();
    }

    private String descriptionFormat(ProvServicioMedico provServicioMedico) {
        Map<String, String> replacements = new HashMap<>();
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Id Proveedor: ");
        descripcion.append(provServicioMedico.getIdProveedor() + "\n");
        descripcion.append("Id Rubro: ");
        descripcion.append(provServicioMedico.getIdRubro() + "\n");
        descripcion.append("Id Ciudad: ");
        descripcion.append(provServicioMedico.getIdCiudad() + "\n");
        descripcion.append("Producto: ");
        descripcion.append(Utils.convertCharset(provServicioMedico.getProducto()) + "\n");
        descripcion.append("Servicio: ");
        descripcion.append(Utils.convertCharset(provServicioMedico.getServicio()) + "\n");
        descripcion.append("Fecha Denuncia: ");
        descripcion.append(provServicioMedico.getFechaDenuncia() + "\n");
        descripcion.append("Fecha Contacto: ");
        descripcion.append(provServicioMedico.getFechaContacto() + "\n");
        descripcion.append("Id Ejecutivo: ");
        descripcion.append(Utils.convertCharset(provServicioMedico.getIdEjecutivo()) + "\n");
        descripcion.append("Nombre Asegurado: ");
        descripcion.append(Utils.convertCharset(provServicioMedico.getNombreAsegurado()) + "\n");
        descripcion.append("Nombre Solicitante: ");
        descripcion.append(Utils.convertCharset(provServicioMedico.getNombreSolicitante()) + "\n");
        descripcion.append("Teléfono: ");
        descripcion.append(provServicioMedico.getTelefono() + "\n");
        descripcion.append("Email: ");
        descripcion.append(provServicioMedico.getEmail() + "\n");

        descripcion.append("Poliza: ");
        descripcion.append(provServicioMedico.getPoliza() + "\n");
        descripcion.append("Dignóstico: ");
        descripcion.append(provServicioMedico.getDignostico() + "\n");
        descripcion.append("Intermediario: ");
        descripcion.append(provServicioMedico.getIntermediario() + "\n");
        descripcion.append("\n\n");

        replacements.put("irub_value", Utils.valueOrNA(provServicioMedico.getIdRubro()));
        replacements.put("iciu_value", Utils.valueOrNA(provServicioMedico.getIdCiudad()));
        replacements.put("ser_value", Utils.valueOrNA(provServicioMedico.getServicio()));
        replacements.put("fcon_value", Utils.valueOrNA(provServicioMedico.getFechaContacto()));
        replacements.put("nsol_value", Utils.valueOrNA(provServicioMedico.getNombreSolicitante()));
        replacements.put("t_value", Utils.valueOrNA(provServicioMedico.getTelefono()));
        replacements.put("e_value", Utils.valueOrNA(provServicioMedico.getEmail()));
        replacements.put("ieje_value", Utils.valueOrNA(provServicioMedico.getIdEjecutivo()));
        replacements.put("nase_value", Utils.valueOrNA(provServicioMedico.getNombreAsegurado()));
        replacements.put("fden_value", Utils.valueOrNA(provServicioMedico.getFechaDenuncia()));
        replacements.put("dia_value", Utils.valueOrNA(provServicioMedico.getDignostico()));
        replacements.put("int_value", Utils.valueOrNA(provServicioMedico.getIntermediario()));
        replacements.put("pro_value", Utils.valueOrNA(provServicioMedico.getProducto()));
        replacements.put("iprv_value", Utils.valueOrNA(provServicioMedico.getIdProveedor()));
        replacements.put("pol_value", Utils.valueOrNA(provServicioMedico.getPoliza()));
        if(provServicioMedico.getUniqueId()!=null && provServicioMedico.getUniqueId().trim().length() > 0){
            replacements.put("idT_value",provServicioMedico.getUniqueId());
        }else {
            replacements.put("idT_value", Utils.valueOrNA(Utils.getCodigoLink(Constant.PROV_SERV_MEDICO_CODE,provServicioMedico.getFechaContacto(),provServicioMedico.getTelefono())));
        }
        descripcion.append(Utils.replaceURLParameters(getWebLink(), replacements));

        return descripcion.toString();
    }

    private String getWebLink() {
        return "https://es.research.net/r/DKYDG9J?irub=[irub_value]&t=[t_value]&iciu=[iciu_value]&ser=[ser_value]&fcon=[fcon_value]&nsol=[nsol_value]&e=[e_value]&ieje=[ieje_value]&nase=[nase_value]&fden=[fden_value]&dia=[dia_value]&int=[int_value]&pro=[pro_value]&iprv=[iprv_value]&pol=[pol_value]&idT=[idT_value]";
    }

}
