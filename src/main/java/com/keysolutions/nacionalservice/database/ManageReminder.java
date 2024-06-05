package com.keysolutions.nacionalservice.database;

import com.keysolutions.nacionalservice.model.log.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ManageReminder {
    @Autowired
    @Qualifier("digitalTemplate")
    private JdbcTemplate jdbcTemplate;
    public List<ConsultasReclamosDb> retrieveConsultasReclamosReminder(){
        String sqlSelect="select cr.consulta_reclamo_id, cr.id_proceso, " +
                " cr.id_rubro, " +
                " cr.id_ciudad, " +
                " cr.id_operador, " +
                " cr.servicio, " +
                " cr.fecha_contacto, " +
                " cr.fecha_cierre_freshdesk, " +
                " cr.id_objecto, " +
                " cr.nombre_titular, " +
                " cr.nombre_solicitante, " +
                " cr.telefono, " +
                " cr.email, " +
                " cr.poliza, " +
                " cr.asunto, " +
                " cr.nombre_archivo, " +
                " cr.status, " +
                " cr.record_date  from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  ,deaxs_record.ns_consulta_reclamo cr " +
                " where nrl.responded is null " +
                " and nrl.service_complain ='CONSULTAS_RECLAMOS' " +
                " and ncl.service_complain ='CONSULTAS_RECLAMOS' " +
                " and nrl.recipient_id = ncl.recipient_id  " +
                " and ncl.first_name =cr.email " +
                " and ncl.nombre_archivo =cr.nombre_archivo " +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59') " +
                "  AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<ConsultasReclamosDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new ConsultasReclamosDb(rs.getString("consulta_reclamo_id"),
                            rs.getString("id_proceso"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("id_operador"),
                            rs.getString("servicio"),
                            rs.getString("fecha_contacto"),
                            rs.getString("fecha_cierre_freshdesk"),
                            rs.getString("id_objecto"),
                            rs.getString("nombre_titular"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("poliza"),
                            rs.getString("asunto"),
                            rs.getString("consulta_reclamo_id"),//uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveConsultasReclamosReminder fail", ex);
        }
        return result;
    }
    public List<AtencionInicialDb> retrieveAtencionInicialReminder(){
        String sqlSelect=" select cr.atencion_inicial_id, " +
                "       cr.id_proceso, " +
                "   cr.id_rubro, " +
                "   cr.id_ciudad, " +
                "   cr.id_operador, " +
                "   cr.id_ejecutivo, " +
                "   cr.fecha_contacto, " +
                "   cr.placa, " +
                "   cr.nombre_titular, " +
                "   cr.nombre_solicitante, " +
                "   cr.telefono, " +
                "   cr.email, " +
                "   cr.poliza, " +
                "   cr.nombre_archivo, " +
                "   cr.status, " +
                "   cr.record_date  from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  ,deaxs_record.ns_atencion_inicial cr " +
                " where nrl.responded is null " +
                " and nrl.service_complain ='ATENCION_INICIAL'" +
                " and ncl.service_complain ='ATENCION_INICIAL'" +
                " and nrl.recipient_id = ncl.recipient_id  " +
                " and ncl.first_name =cr.email" +
                " and ncl.nombre_archivo =cr.nombre_archivo" +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59')" +
                "                    AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<AtencionInicialDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new AtencionInicialDb(rs.getString("atencion_inicial_id"),
                            rs.getString("id_proceso"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("id_operador"),
                            rs.getString("id_ejecutivo"),
                            rs.getString("fecha_contacto"),
                            rs.getString("placa"),
                            rs.getString("nombre_titular"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("poliza"),
                            rs.getString("atencion_inicial_id"), //uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveConsultasReclamosReminder fail", ex);
        }
        return result;
    }
    public List<InmedicalAtuMedidaDb> retrieveInmedialAtuMedidaReminder(){
        String sqlSelect="select cr.inmediacal_atu_id, " +
                " cr.id_proceso, " +
                " cr.id_rubro, " +
                " cr.id_ciudad, " +
                " cr.proveedor, " +
                " cr.id_centro, " +
                " cr.servicio, " +
                " cr.fecha_contacto, " +
                " cr.id_documento, " +
                " cr.nombre_titular, " +
                " cr.nombre_solicitante, " +
                " cr.telefono, " +
                " cr.email, " +
                " cr.nombre_archivo, " +
                " cr.status, " +
                " cr.record_date   from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  , deaxs_record.ns_inmedical_atu_medida cr " +
                " where nrl.responded is null" +
                " and nrl.service_complain ='INMEDICAL_ATU_MEDIDA'" +
                " and ncl.service_complain ='INMEDICAL_ATU_MEDIDA'" +
                " and nrl.recipient_id = ncl.recipient_id  " +
                " and ncl.first_name =cr.email" +
                " and ncl.nombre_archivo =cr.nombre_archivo" +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59') " +
                "                    AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<InmedicalAtuMedidaDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new InmedicalAtuMedidaDb(rs.getString("inmediacal_atu_id"),
                            rs.getString("id_proceso"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("proveedor"),
                            rs.getString("id_centro"),
                            rs.getString("servicio"),
                            rs.getString("fecha_contacto"),
                            rs.getString("id_documento"),
                            rs.getString("nombre_titular"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("inmediacal_atu_id"),//uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveInmedialAtuMedidaReminder fail", ex);
        }
        return result;
    }
    public List<TallersE2eDb> retrieveTallersE2eReminder(){
        String sqlSelect="select cr.talleres_e2e_id, " +
                "cr.id_proceso,id_rubro, " +
                "cr.id_ciudad, " +
                "cr.id_operador, " +
                "cr.servicio, " +
                "cr.fecha_contacto, " +
                "cr.periodo, " +
                "cr.nombre_titular, " +
                "cr.nombre_solicitante, " +
                "cr.telefono, " +
                "cr.marca, " +
                "cr.modelo, " +
                "cr.placa, " +
                "cr.color, " +
                "cr.poliza, " +
                "cr.intermediario, " +
                "cr.email, " +
                "cr.nombre_archivo, " +
                "cr.status, " +
                "cr.record_date " +
                " from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  , deaxs_record.ns_talleres_e2e cr  " +
                " where nrl.responded is null " +
                " and nrl.service_complain ='TALLERES_E2E' " +
                " and ncl.service_complain ='TALLERES_E2E' " +
                " and nrl.recipient_id = ncl.recipient_id   " +
                " and ncl.first_name =cr.email " +
                " and ncl.nombre_archivo =cr.nombre_archivo " +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59') " +
                "                    AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<TallersE2eDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new TallersE2eDb(rs.getString("talleres_e2e_id"),
                            rs.getString("id_proceso"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("id_operador"),
                            rs.getString("servicio"),
                            rs.getString("fecha_contacto"),
                            rs.getString("periodo"),
                            rs.getString("nombre_titular"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getString("placa"),
                            rs.getString("color"),
                            rs.getString("poliza"),//uniqueId
                            rs.getString("intermediario"),
                            rs.getString("email"),
                            rs.getString("talleres_e2e_id"),
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveTallersE2eReminder fail", ex);
        }
        return result;
    }

    public List<CentroRehaOdontoDb> retrieveCentroRehaOdontoReminder(){
        String sqlSelect=" select  " +
                " cr.centro_reha_odonto_id, " +
                " cr.id_proceso,id_rubro, " +
                " cr.id_ciudad,id_operador, " +
                " cr.centro_medico, " +
                " cr.fecha_contacto, " +
                " cr.fecha_salida, " +
                " cr.paciente, " +
                " cr.plan, " +
                " cr.telefono, " +
                " cr.email, " +
                " cr.telefono_alternativo, " +
                " cr.nombre_archivo, " +
                " cr.status, " +
                " cr.record_date " +
                " from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  , deaxs_record.ns_centro_reha_odontologia cr  " +
                " where nrl.responded is null " +
                " and nrl.service_complain ='CENTRO_REHA_ODONTOLOGICA' " +
                " and ncl.service_complain ='CENTRO_REHA_ODONTOLOGICA' " +
                " and nrl.recipient_id = ncl.recipient_id   " +
                " and ncl.first_name =cr.email " +
                " and ncl.nombre_archivo =cr.nombre_archivo " +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59') " +
                "                    AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<CentroRehaOdontoDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new CentroRehaOdontoDb(rs.getString("centro_reha_odonto_id"),
                            rs.getString("id_proceso"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("id_operador"),
                            rs.getString("centro_medico"),
                            rs.getString("fecha_contacto"),
                            rs.getString("fecha_salida"),
                            rs.getString("paciente"),
                            rs.getString("plan"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                             rs.getString("telefono_alternativo"),
                            rs.getString("centro_reha_odonto_id"),//uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveCentroRehaOdontoReminder fail", ex);
        }
        return result;
    }
    public List<ProvServicioMedicoDb> retrieveProvServMedicoReminder(){
        String sqlSelect="select " +
                " cr.prov_ser_med_id, " +
                " cr.id_proveedor, " +
                " cr.id_rubro, cr.id_ciudad, " +
                " cr.producto, " +
                " cr.servicio, " +
                " cr.fecha_denuncia, " +
                " cr.fecha_contacto, " +
                " cr.id_ejecutivo, " +
                " cr.nombre_asegurado, " +
                " cr.nombre_solicitante, " +
                " cr.telefono, " +
                " cr.email, " +
                " cr.poliza, " +
                " cr.dignostico, " +
                " cr.intermediario, " +
                " cr.nombre_archivo, " +
                " cr.status, " +
                " cr.record_date " +
                " from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  , deaxs_record.ns_prov_servicio_medico cr " +
                " where nrl.responded is null" +
                " and nrl.service_complain ='PROV_SERV_MEDICO'" +
                " and ncl.service_complain ='PROV_SERV_MEDICO'" +
                " and nrl.recipient_id = ncl.recipient_id  " +
                " and ncl.first_name =cr.email" +
                " and ncl.nombre_archivo =cr.nombre_archivo" +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59')" +
                "                    AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<ProvServicioMedicoDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new ProvServicioMedicoDb(rs.getString("prov_ser_med_id"),
                            rs.getString("id_proveedor"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("producto"),
                            rs.getString("servicio"),
                            rs.getString("fecha_denuncia"),
                            rs.getString("fecha_contacto"),
                            rs.getString("id_ejecutivo"),
                            rs.getString("nombre_asegurado"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("poliza"),
                            rs.getString("dignostico"),
                            rs.getString("intermediario"),
                            rs.getString("prov_ser_med_id"),//uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveProvServMedicoReminder fail", ex);
        }
        return result;
    }
    public List<ProveedorMedicoDb> retrieveProveedorMedicoReminder(){
        String sqlSelect="select " +
                " cr.prov_med_id, " +
                " cr.id_proveedor, " +
                " cr.id_rubro, " +
                " cr.id_ciudad, " +
                " cr.producto, " +
                " cr.servicio, " +
                " cr.fecha_denuncia, " +
                " cr.fecha_contacto, " +
                " cr.id_ejecutivo, " +
                " cr.nombre_asegurado, " +
                " cr.nombre_solicitante, " +
                " cr.telefono, " +
                " cr.email, " +
                " cr.poliza, " +
                " cr.dignostico, " +
                " cr.intermediario, " +
                " cr.nombre_archivo, " +
                " cr.status, " +
                " cr.record_date " +
                " from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  , deaxs_record.ns_proveedor_medico cr " +
                " where nrl.responded is null" +
                " and nrl.service_complain ='PROVEEDOR_MEDICO'" +
                " and ncl.service_complain ='PROVEEDOR_MEDICO'" +
                " and nrl.recipient_id = ncl.recipient_id  " +
                " and ncl.first_name =cr.email" +
                " and ncl.nombre_archivo =cr.nombre_archivo" +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59')" +
                "                    AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<ProveedorMedicoDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new ProveedorMedicoDb(rs.getString("prov_med_id"),
                            rs.getString("id_proveedor"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("producto"),
                            rs.getString("servicio"),
                            rs.getString("fecha_denuncia"),
                            rs.getString("fecha_contacto"),
                            rs.getString("id_ejecutivo"),
                            rs.getString("nombre_asegurado"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("poliza"),
                            rs.getString("dignostico"),
                            rs.getString("intermediario"),
                            rs.getString("prov_med_id"),//uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveProveedorMedicoReminder fail", ex);
        }
        return result;
    }

    public List<ProveedorFarmaDb> retrieveProveedorFarmaReminder(){
        String sqlSelect="select " +
                " cr.prov_far_id, " +
                " cr.id_proveedor, " +
                " cr.id_rubro, " +
                " cr.id_ciudad, " +
                " cr.producto, " +
                " cr.servicio, " +
                " cr.fecha_denuncia, " +
                " cr.fecha_contacto, " +
                " cr.id_ejecutivo, " +
                " cr.nombre_asegurado, " +
                " cr.nombre_solicitante, " +
                " cr.telefono, " +
                " cr.email, " +
                " cr.poliza, " +
                " cr.dignostico, " +
                " cr.intermediario, " +
                " cr.nombre_archivo, " +
                " cr.status, " +
                " cr.record_date" +
                " from deaxs_record.ns_recipient_log nrl,deaxs_record.ns_contact_log ncl  , deaxs_record.ns_proveedor_farmaceutica cr " +
                " where nrl.responded is null" +
                " and nrl.service_complain ='PROVEEDOR_FARMACIA'" +
                " and ncl.service_complain ='PROVEEDOR_FARMACIA'" +
                " and nrl.recipient_id = ncl.recipient_id  " +
                " and ncl.first_name =cr.email" +
                " and ncl.nombre_archivo =cr.nombre_archivo" +
                " and cr.record_date BETWEEN CONCAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY), ' 23:59:59')" +
                "                    AND CONCAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY), ' 23:59:59') ";
        List<ProveedorFarmaDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new ProveedorFarmaDb(rs.getString("prov_far_id"),
                            rs.getString("id_proveedor"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("producto"),
                            rs.getString("servicio"),
                            rs.getString("fecha_denuncia"),
                            rs.getString("fecha_contacto"),
                            rs.getString("id_ejecutivo"),
                            rs.getString("nombre_asegurado"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("poliza"),
                            rs.getString("dignostico"),
                            rs.getString("intermediario"),
                            rs.getString("prov_far_id"),//uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveProveedorFarmaReminder fail", ex);
        }
        return result;
    }
}
