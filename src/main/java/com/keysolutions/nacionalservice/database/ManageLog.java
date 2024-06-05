package com.keysolutions.nacionalservice.database;

import com.keysolutions.nacionalservice.model.AtenAsisProdemVidaPlus;
import com.keysolutions.nacionalservice.model.jira.ConfigJira;
import com.keysolutions.nacionalservice.model.log.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.keysolutions.nacionalservice.model.survey.Recipient;
import com.keysolutions.nacionalservice.model.survey.RecipientResponse;
import com.keysolutions.nacionalservice.util.Constant;
import com.keysolutions.nacionalservice.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class ManageLog {
    @Autowired
    @Qualifier("digitalTemplate")
    private JdbcTemplate jdbcTemplate;

    public void recordAstenciaVial(AsistenciaVialDb asistenciaVialDb) {
        //String asistenciaVialId = UUID.randomUUID().toString().replace("-", "");
        //asistenciaVialDb.setAsistenciaVialId(asistenciaVialId);
        log.debug(">>>>>>>: {}", asistenciaVialDb);
        String insert = "INSERT INTO deaxs_record.ns_asistencia_vial (asistencia_vial_id, id_proceso, id_rubro, id_ciudad, id_operador, id_siniestro,"
                +
                " fecha_contacto, nombre_titular, nombre_solicitante, telefono, telefono_alternativo, placa, nombre_archivo) "
                +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, asistenciaVialDb.getAsistenciaVialId(), asistenciaVialDb.getIdProceso(),
                    asistenciaVialDb.getIdRubro(), asistenciaVialDb.getIdCiudad(),
                    asistenciaVialDb.getIdOperador(), asistenciaVialDb.getIdSiniestro(),
                    asistenciaVialDb.getFechaContacto(), asistenciaVialDb.getNombreTitular(),
                    asistenciaVialDb.getNombreSolicitante(), asistenciaVialDb.getTelefono(),
                    asistenciaVialDb.getTelefonoAlternativo(), asistenciaVialDb.getPlaca(),
                    asistenciaVialDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordAstenciaVial db exception", ex);
        }
    }

    public void recordNsvs800(Nsvs800Db nsvs800Db) {
       // String nSvs800Id = UUID.randomUUID().toString().replace("-", "");
       // nsvs800Db.setNSvs800Id(nSvs800Id);
        log.debug(">>>>>>>: {}", nsvs800Db);
        String insert = "INSERT INTO deaxs_record.ns_nsvs_800 (nsvs_800_id, id_proceso, id_rubro, id_ciudad, id_operador, "
                +
                " servicio, fecha_contacto, id_objecto, nombre_titular, nombre_solicitante, telefono, email, poliza, nombre_archivo) "
                +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, nsvs800Db.getNSvs800Id(), nsvs800Db.getIdProceso(), nsvs800Db.getIdRubro(),
                    nsvs800Db.getIdCiudad(),
                    nsvs800Db.getIdOperador(), nsvs800Db.getServicio(), nsvs800Db.getFechaContacto(),
                    nsvs800Db.getIdObjeto(), nsvs800Db.getNombreTitular(),
                    nsvs800Db.getNombreSolicitante(), nsvs800Db.getTelefono(), nsvs800Db.getEmail(),
                    nsvs800Db.getIdPoliza(), nsvs800Db.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordNsvs800 db exception", ex);
        }
    }

    public void recordConsutasReclamos(ConsultasReclamosDb consultasReclamosDb) {
       // String consultaReclamoId = UUID.randomUUID().toString().replace("-", "");
       // consultasReclamosDb.setConsultaReclamoId(consultaReclamoId);
        log.debug(">>>>>>>: {}", consultasReclamosDb);
        String insert = "INSERT INTO deaxs_record.ns_consulta_reclamo (consulta_reclamo_id, id_proceso, id_rubro, id_ciudad, id_operador, servicio, fecha_contacto, "
                +
                "fecha_cierre_freshdesk, id_objecto, nombre_titular, nombre_solicitante, telefono, email, poliza, asunto, nombre_archivo) "
                +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, consultasReclamosDb.getConsultaReclamoId(), consultasReclamosDb.getIdProceso(),
                    consultasReclamosDb.getIdRubro(), consultasReclamosDb.getIdCiudad(),
                    consultasReclamosDb.getIdOperador(), consultasReclamosDb.getServicio(),
                    consultasReclamosDb.getFechaContacto(), consultasReclamosDb.getFechaCierreFreshDesk(),
                    consultasReclamosDb.getIdObjeto(),
                    consultasReclamosDb.getNombreTitular(), consultasReclamosDb.getNombreSolicitante(),
                    consultasReclamosDb.getTelefono(), consultasReclamosDb.getEmail(),
                    consultasReclamosDb.getIdPoliza(),
                    consultasReclamosDb.getAsunto(), consultasReclamosDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordConsutasReclamos db exception", ex);
        }
    }

    public void recordAtencionInicial(AtencionInicialDb atencionInicialDb) {
      //  String atencionInicialId = UUID.randomUUID().toString().replace("-", "");
      //  atencionInicialDb.setAtencionInicialId(atencionInicialId);
        log.debug(">>>>>>>: {}", atencionInicialDb);
        String insert = "INSERT INTO deaxs_record.ns_atencion_inicial (atencion_inicial_id, id_proceso, id_rubro, id_ciudad, id_operador, id_ejecutivo, fecha_contacto, "
                +
                " placa, nombre_titular, nombre_solicitante, telefono, email, poliza, nombre_archivo) " +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, atencionInicialDb.getAtencionInicialId(), atencionInicialDb.getIdProceso(),
                    atencionInicialDb.getIdRubro(), atencionInicialDb.getIdCiudad(),
                    atencionInicialDb.getIdOperador(), atencionInicialDb.getIdEjecutivo(),
                    atencionInicialDb.getFechaContacto(), atencionInicialDb.getPlaca(),
                    atencionInicialDb.getNombreTitular(), atencionInicialDb.getNombreSolicitante(),
                    atencionInicialDb.getTelefono(), atencionInicialDb.getEmail(), atencionInicialDb.getIdPoliza(),
                    atencionInicialDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordAtencionInicial db exception", ex);
        }
    }

    public void recordAccidentePersonal(AccidentesPersonalesDb accidentesPersonalesDb) {
     //   String accidentePersonalId = UUID.randomUUID().toString().replace("-", "");
      //  accidentesPersonalesDb.setAccidentePersonalId(accidentePersonalId);
        log.debug(">>>>>>>: {}", accidentesPersonalesDb);
        String insert = "INSERT INTO deaxs_record.ns_accidente_personal (accidente_personal_id, id_proceso, id_rubro, id_ciudad, id_operador, "
                +
                "servicio, fecha_contacto, placa, nombre_titular, nombre_solicitante, telefono, email, nombre_archivo) "
                +
                " VALUES(?, ?, ?,? , ?,? ,? ,? ,? ,? ,? ,? ,?)";
        try {
            jdbcTemplate.update(insert, accidentesPersonalesDb.getAccidentePersonalId(),
                    accidentesPersonalesDb.getIdProceso(), accidentesPersonalesDb.getIdRubro(),
                    accidentesPersonalesDb.getIdCiudad(), accidentesPersonalesDb.getIdOperador(),
                    accidentesPersonalesDb.getServicio(), accidentesPersonalesDb.getFechaContacto(),
                    accidentesPersonalesDb.getPlaca(), accidentesPersonalesDb.getNombreTitular(),
                    accidentesPersonalesDb.getNombreSolicitante(),
                    accidentesPersonalesDb.getTelefono(), accidentesPersonalesDb.getEmail(),
                    accidentesPersonalesDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordAccidentePersonal db exception", ex);
        }
    }

    public void recordCallCenterNspf(CallCenterNspfDb callCenterNspfDb) {
      //  String callCenterNpsfId = UUID.randomUUID().toString().replace("-", "");
      //  callCenterNspfDb.setCallCenterNspfId(callCenterNpsfId);
        log.debug(">>>>>>>: {}", callCenterNspfDb);
        String insert = "INSERT INTO deaxs_record.ns_callcenter_nspf (callcenter_nspf_id, id_proceso, id_rubro, id_ciudad, "+
        " id_siniestro, id_operador, fecha_contacto, id_cliente, nombre_archivo) "+
        " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, callCenterNspfDb.getCallCenterNspfId(),
            callCenterNspfDb.getIdProceso(), callCenterNspfDb.getIdRubro(),
            callCenterNspfDb.getIdCiudad(),callCenterNspfDb.getIdSiniestro() ,callCenterNspfDb.getIdOperador(),
            callCenterNspfDb.getFechaContacto(), callCenterNspfDb.getIdCliente(), 
            callCenterNspfDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordCallCenterNspf db exception", ex);
        }
    }
    public void recordInmedicalAtuMedida(InmedicalAtuMedidaDb inmedicalAtuMedidaDb) {
      //  String inmediacalAtuId = UUID.randomUUID().toString().replace("-", "");
       // inmedicalAtuMedidaDb.setInmediacalAtuId(inmediacalAtuId);
        log.debug(">>>>>>>: {}", inmedicalAtuMedidaDb);
        String insert = "INSERT INTO deaxs_record.ns_inmedical_atu_medida (inmediacal_atu_id, id_proceso, id_rubro, id_ciudad, proveedor, "+
        " id_centro, servicio, fecha_contacto, id_documento, nombre_titular, nombre_solicitante, telefono, email, nombre_archivo) "+
        " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, inmedicalAtuMedidaDb.getInmediacalAtuId(),inmedicalAtuMedidaDb.getIdProceso(),inmedicalAtuMedidaDb.getIdRubro(),inmedicalAtuMedidaDb.getIdCiudad(),inmedicalAtuMedidaDb.getProveedor(),
                    inmedicalAtuMedidaDb.getIdCentro(),inmedicalAtuMedidaDb.getServicio(),inmedicalAtuMedidaDb.getFechaContacto(),inmedicalAtuMedidaDb.getIdDocumento(),
                    inmedicalAtuMedidaDb.getNombreTitular(),inmedicalAtuMedidaDb.getNombreSolicitante(),inmedicalAtuMedidaDb.getTelefono(),inmedicalAtuMedidaDb.getEmail(),
                    inmedicalAtuMedidaDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordInmedicalAtuMedida db exception", ex);
        }
    }
    public void recordInmedicalBancoGanadero(InmedicalBancoGanaderoDb inmedicalBancoGanaderoDb) {
      //  String inmediacalBancoId = UUID.randomUUID().toString().replace("-", "");
      //  inmedicalBancoGanaderoDb.setInmediacalBancoId(inmediacalBancoId);
        log.debug(">>>>>>>: {}", inmedicalBancoGanaderoDb);
        String insert = "INSERT INTO deaxs_record.ns_inmedical_banco_ganadero (inmediacal_banco_id, id_proceso, id_rubro, id_ciudad, proveedor, "+
                " id_centro, servicio, fecha_contacto, id_documento, nombre_titular, nombre_solicitante, telefono, email, nombre_archivo) "+
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, inmedicalBancoGanaderoDb.getInmediacalBancoId(),inmedicalBancoGanaderoDb.getIdProceso(),inmedicalBancoGanaderoDb.getIdRubro(),inmedicalBancoGanaderoDb.getIdCiudad(),inmedicalBancoGanaderoDb.getProveedor(),
                    inmedicalBancoGanaderoDb.getIdCentro(),inmedicalBancoGanaderoDb.getServicio(),inmedicalBancoGanaderoDb.getFechaContacto(),inmedicalBancoGanaderoDb.getIdDocumento(),
                    inmedicalBancoGanaderoDb.getNombreTitular(),inmedicalBancoGanaderoDb.getNombreSolicitante(),inmedicalBancoGanaderoDb.getTelefono(),inmedicalBancoGanaderoDb.getEmail(),
                    inmedicalBancoGanaderoDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordInmedicalBancoGanadero db exception", ex);
        }
    }
    public void recordTalleresE2e(TallersE2eDb tallersE2eDb) {
      //  String talleresE2eId = UUID.randomUUID().toString().replace("-", "");
      //  tallersE2eDb.setTalleresE2eId(talleresE2eId);
        log.debug(">>>>>>>: {}", tallersE2eDb);
        String insert = "INSERT INTO deaxs_record.ns_talleres_e2e (talleres_e2e_id, id_proceso, id_rubro, id_ciudad, "+
        "id_operador, servicio, fecha_contacto, periodo, nombre_titular, nombre_solicitante, telefono, marca, modelo, placa, "+
        " color, poliza, intermediario, email, nombre_archivo) "+
        " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, tallersE2eDb.getTalleresE2eId(),tallersE2eDb.getIdProceso(),tallersE2eDb.getIdRubro(),tallersE2eDb.getIdCiudad(),
                    tallersE2eDb.getIdOperador(),tallersE2eDb.getServicio(),tallersE2eDb.getFechaContacto(),tallersE2eDb.getPeriodo(),tallersE2eDb.getNombreTitular(),tallersE2eDb.getNombreSolicitante(),
                    tallersE2eDb.getTelefono(),tallersE2eDb.getMarca(),tallersE2eDb.getModelo(),tallersE2eDb.getPlaca(),tallersE2eDb.getColor(),
                    tallersE2eDb.getIdPoliza(),tallersE2eDb.getIntermediario(),tallersE2eDb.getEmail(),tallersE2eDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordTalleresE2e db exception", ex);
        }
    }

    public void recordCentroRehaOdonto(CentroRehaOdontoDb centroRehaOdontoDb) {
       // String centroRehaOdontoId = UUID.randomUUID().toString().replace("-", "");
       // centroRehaOdontoDb.setCentroRehaOdontoId(centroRehaOdontoId);
        log.debug(">>>>>>>: {}", centroRehaOdontoDb);
        String insert = "INSERT INTO deaxs_record.ns_centro_reha_odontologia (centro_reha_odonto_id, id_proceso, "+
        " id_rubro, id_ciudad, id_operador, centro_medico, fecha_contacto, fecha_salida, paciente, plan, "+
        " telefono, email, telefono_alternativo, nombre_archivo) "+
                "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, centroRehaOdontoDb.getCentroRehaOdontoId(),centroRehaOdontoDb.getIdProceso(),
                    centroRehaOdontoDb.getIdRubro(),centroRehaOdontoDb.getIdCiudad(),centroRehaOdontoDb.getIdOperador(),
                    centroRehaOdontoDb.getCentroMedico(),centroRehaOdontoDb.getFechaContacto(),centroRehaOdontoDb.getFechaSalida(),
                    centroRehaOdontoDb.getPaciente(),centroRehaOdontoDb.getPlan(),
                    centroRehaOdontoDb.getTelefono(),centroRehaOdontoDb.getEmail(),centroRehaOdontoDb.getTelefonoAdicional(),centroRehaOdontoDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordCentroRehaOdonto db exception", ex);
        }
    }
    public void recordProvServMedico(ProvServicioMedicoDb provServicioMedicoDb) {
      //  String provServMedId = UUID.randomUUID().toString().replace("-", "");
      //  provServicioMedicoDb.setProvSerMedId(provServMedId);
        log.debug(">>>>>>>: {}", provServicioMedicoDb);
        String insert = "INSERT INTO deaxs_record.ns_prov_servicio_medico (prov_ser_med_id, id_proveedor, "+
        " id_rubro, id_ciudad, producto, servicio, fecha_denuncia, fecha_contacto, id_ejecutivo, nombre_asegurado, "+
        " nombre_solicitante, telefono, email, poliza, dignostico, intermediario, nombre_archivo) "+
                " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, provServicioMedicoDb.getProvSerMedId(),provServicioMedicoDb.getIdProveedor(),
                    provServicioMedicoDb.getIdRubro(),provServicioMedicoDb.getIdCiudad(),provServicioMedicoDb.getProducto(),
                    provServicioMedicoDb.getServicio(),provServicioMedicoDb.getFechaDenuncia(),provServicioMedicoDb.getFechaContacto(),
                    provServicioMedicoDb.getIdEjecutivo(),provServicioMedicoDb.getNombreAsegurado(),
                    provServicioMedicoDb.getNombreSolicitante(),provServicioMedicoDb.getTelefono(),provServicioMedicoDb.getEmail(),
                    provServicioMedicoDb.getPoliza(),provServicioMedicoDb.getDignostico(),provServicioMedicoDb.getIntermediario(),provServicioMedicoDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordProvServMedico db exception", ex);
        }
    }

    public void recordProveedorMedico(ProveedorMedicoDb proveedorMedicoDb) {
      //  String provMedId = UUID.randomUUID().toString().replace("-", "");
      //  proveedorMedicoDb.setProvMedId(provMedId);
        log.debug(">>>>>>>: {}", proveedorMedicoDb);
        String insert = "INSERT INTO deaxs_record.ns_proveedor_medico (prov_med_id, id_proveedor, "+
                " id_rubro, id_ciudad, producto, servicio, fecha_denuncia, fecha_contacto, id_ejecutivo, nombre_asegurado, "+
                " nombre_solicitante, telefono, email, poliza, dignostico, intermediario, nombre_archivo) "+
                " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, proveedorMedicoDb.getProvMedId(),proveedorMedicoDb.getIdProveedor(),
                    proveedorMedicoDb.getIdRubro(),proveedorMedicoDb.getIdCiudad(),proveedorMedicoDb.getProducto(),
                    proveedorMedicoDb.getServicio(),proveedorMedicoDb.getFechaDenuncia(),proveedorMedicoDb.getFechaContacto(),
                    proveedorMedicoDb.getIdEjecutivo(),proveedorMedicoDb.getNombreAsegurado(),
                    proveedorMedicoDb.getNombreSolicitante(),proveedorMedicoDb.getTelefono(),proveedorMedicoDb.getEmail(),
                    proveedorMedicoDb.getPoliza(),proveedorMedicoDb.getDignostico(),proveedorMedicoDb.getIntermediario(),proveedorMedicoDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordProveedorMedico db exception", ex);
        }
    }
    public void recordProveedorFarma(ProveedorFarmaDb proveedorFarmaDb) {
     //   String provFarId = UUID.randomUUID().toString().replace("-", "");
     //   proveedorFarmaDb.setProvFarId(provFarId);
        log.debug(">>>>>>>: {}", proveedorFarmaDb);
        String insert = "INSERT INTO deaxs_record.ns_proveedor_farmaceutica (prov_far_id, id_proveedor, "+
                " id_rubro, id_ciudad, producto, servicio, fecha_denuncia, fecha_contacto, id_ejecutivo, nombre_asegurado, "+
                " nombre_solicitante, telefono, email, poliza, dignostico, intermediario, nombre_archivo) "+
                " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert, proveedorFarmaDb.getProvFarId(),proveedorFarmaDb.getIdProveedor(),
                    proveedorFarmaDb.getIdRubro(),proveedorFarmaDb.getIdCiudad(),proveedorFarmaDb.getProducto(),
                    proveedorFarmaDb.getServicio(),proveedorFarmaDb.getFechaDenuncia(),proveedorFarmaDb.getFechaContacto(),
                    proveedorFarmaDb.getIdEjecutivo(),proveedorFarmaDb.getNombreAsegurado(),
                    proveedorFarmaDb.getNombreSolicitante(),proveedorFarmaDb.getTelefono(),proveedorFarmaDb.getEmail(),
                    proveedorFarmaDb.getPoliza(),proveedorFarmaDb.getDignostico(),proveedorFarmaDb.getIntermediario(),proveedorFarmaDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordProveedorFarma db exception", ex);
        }
    }
    public void recordAsistenciaMedDormi(AsistenciaMedDomiDb asistenciaMedDomiDb) {
      //  String asistenciaMedId = UUID.randomUUID().toString().replace("-", "");
      //  asistenciaMedDomiDb.setAsisMedDoId(asistenciaMedId);
        log.debug(">>>>>>>: {}", asistenciaMedDomiDb);
        String insert = "INSERT INTO deaxs_record.ns_asistencia_med_dom (asistencia_med_id, id_proceso, id_rubro, id_ciudad, "+
        " edad, motivo_llamada, producto, servicio, desenlace, fecha_contacto, fecha_arribo, incidencia, unidad_asignada, paciente, "+
        " telefono, telefono_alternativo, direccion_atencion, aclaraciones, nombre_archivo) "+
                " VALUES(? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert,asistenciaMedDomiDb.getAsisMedDoId(),asistenciaMedDomiDb.getIdProceso(),asistenciaMedDomiDb.getIdRubro(),asistenciaMedDomiDb.getIdCiudad(),
                    asistenciaMedDomiDb.getEdad(),asistenciaMedDomiDb.getMotivoLlamada(),asistenciaMedDomiDb.getProducto(),asistenciaMedDomiDb.getServicio(),
                    asistenciaMedDomiDb.getDesenlace(),asistenciaMedDomiDb.getFechaContacto(),asistenciaMedDomiDb.getFechaArribo(),asistenciaMedDomiDb.getIncidencia(),
                    asistenciaMedDomiDb.getUnidadAsignada(),asistenciaMedDomiDb.getPaciente(),asistenciaMedDomiDb.getTelefono(),asistenciaMedDomiDb.getTelefonoAlternativo(),
                    asistenciaMedDomiDb.getDireccionAtencion(),asistenciaMedDomiDb.getAclaraciones(),asistenciaMedDomiDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordAsistenciaMedDormi db exception", ex);
        }
    }
    public void recordAsistenciaMedDormiAmbu(AsistenciaMedDomiAmbuDb asistenciaMedDomiAmbuDb) {
      //  String asistenciaMedAmbuId = UUID.randomUUID().toString().replace("-", "");
      //  asistenciaMedDomiAmbuDb.setAsisMedDoAmbuId(asistenciaMedAmbuId);
        log.debug(">>>>>>>: {}", asistenciaMedDomiAmbuDb);
        String insert = "INSERT INTO deaxs_record.ns_asistencia_med_dom_ambu (asistencia_med_ambu_id, id_proceso, id_rubro, id_ciudad, "+
                " edad, motivo_llamada, producto, servicio, desenlace, fecha_contacto, fecha_arribo, incidencia, unidad_asignada, paciente, "+
                " telefono, telefono_alternativo, direccion_atencion, aclaraciones, nombre_archivo) "+
                " VALUES(? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert,asistenciaMedDomiAmbuDb.getAsisMedDoAmbuId(),asistenciaMedDomiAmbuDb.getIdProceso(),asistenciaMedDomiAmbuDb.getIdRubro(),asistenciaMedDomiAmbuDb.getIdCiudad(),
                    asistenciaMedDomiAmbuDb.getEdad(),asistenciaMedDomiAmbuDb.getMotivoLlamada(),asistenciaMedDomiAmbuDb.getProducto(),asistenciaMedDomiAmbuDb.getServicio(),
                    asistenciaMedDomiAmbuDb.getDesenlace(),asistenciaMedDomiAmbuDb.getFechaContacto(),asistenciaMedDomiAmbuDb.getFechaArribo(),asistenciaMedDomiAmbuDb.getIncidencia(),
                    asistenciaMedDomiAmbuDb.getUnidadAsignada(),asistenciaMedDomiAmbuDb.getPaciente(),asistenciaMedDomiAmbuDb.getTelefono(),asistenciaMedDomiAmbuDb.getTelefonoAlternativo(),
                    asistenciaMedDomiAmbuDb.getDireccionAtencion(),asistenciaMedDomiAmbuDb.getAclaraciones(),asistenciaMedDomiAmbuDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordAsistenciaMedDormi db exception", ex);
        }
    }
    public void recordOrientaMedicaTelf(OrientacionMedTelefDb orientacionMedTelefDb) {
      //  String orienMedTelId = UUID.randomUUID().toString().replace("-", "");
      //  orientacionMedTelefDb.setOrienMedTelId(orienMedTelId);
        log.debug(">>>>>>>: {}", orientacionMedTelefDb);
        String insert = "INSERT INTO deaxs_record.ns_orienta_medica_telefono (orien_med_tel_id, id_proceso, id_rubro, id_ciudad, "+
        " edad, motivo_llamada, contacto_covid, diagnostico_presuntivo, conducta, fecha_contacto, medico, paciente, telefono, "+
        " telefono_alternativo, email, observaciones, aclaraciones, nombre_archivo) "+
                "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert,orientacionMedTelefDb.getOrienMedTelId(),orientacionMedTelefDb.getIdProceso(),orientacionMedTelefDb.getIdRubro(),orientacionMedTelefDb.getIdCiudad(),
                    orientacionMedTelefDb.getEdad(),orientacionMedTelefDb.getMotivoLlamada(),orientacionMedTelefDb.getContactoCovid(),orientacionMedTelefDb.getDiagnosticoPresuntivo(),
                    orientacionMedTelefDb.getConducta(),orientacionMedTelefDb.getFechaContacto(),orientacionMedTelefDb.getMedico(),orientacionMedTelefDb.getPaciente(),
                    orientacionMedTelefDb.getTelefono(),orientacionMedTelefDb.getTelefonoAlternativo(),orientacionMedTelefDb.getEmail(),orientacionMedTelefDb.getObservaciones(),
                    orientacionMedTelefDb.getAclaraciones(),orientacionMedTelefDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordAsistenciaMedDormi db exception", ex);
        }
    }

    public void recordAtenAsisProdemVidaPlus(AtenAsisProdemVidaPlusDb atenAsisProdemVidaPlusDb) {
      //  String atenAsisProId = UUID.randomUUID().toString().replace("-", "");
      //  atenAsisProdemVidaPlusDb.setAtenAsisProId(atenAsisProId);
        log.debug(">>>>>>>: {}", atenAsisProdemVidaPlusDb);
        String insert = "INSERT INTO deaxs_record.ns_aten_asiten_prodem_vida (aten_asis_pro_id, id_proceso, proveedor, id_rubro, "+
        " id_ciudad, id_operador, servicio, fecha_contacto, nombre_titular, telefono, id_documento, especialidad, nombre_archivo) "+
                " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert,atenAsisProdemVidaPlusDb.getAtenAsisProId(),atenAsisProdemVidaPlusDb.getIdProceso(),atenAsisProdemVidaPlusDb.getProveedor(),
                    atenAsisProdemVidaPlusDb.getIdRubro(),atenAsisProdemVidaPlusDb.getIdCiudad(),atenAsisProdemVidaPlusDb.getIdOperador(),
                    atenAsisProdemVidaPlusDb.getServicio(),atenAsisProdemVidaPlusDb.getFechaContacto(),atenAsisProdemVidaPlusDb.getNombreTitular(),
                    atenAsisProdemVidaPlusDb.getTelefono(),atenAsisProdemVidaPlusDb.getIdDocumento(),atenAsisProdemVidaPlusDb.getEspecialidad(),atenAsisProdemVidaPlusDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordAtenAsisProdemVidaPlus db exception", ex);
        }
    }

    public void recordVagonetaSegura(VagonetaSeguraDb vagonetaSeguraDb) {
      //  String vagonataSeguraId = UUID.randomUUID().toString().replace("-", "");
      //  vagonetaSeguraDb.setVagonetaSeguraId(vagonataSeguraId);
        log.debug(">>>>>>>: {}", vagonetaSeguraDb);
        String insert = "INSERT INTO deaxs_record.ns_vagoneta_segura (vagoneta_segura_id, id_proceso, id_rubro, id_ciudad, "+
        "id_operador, servicio, fecha_contacto, fecha_servicio, nombre_titular, telefono, telefono_alternativo, placa,"+
        " nombre_archivo) "+
                "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert,vagonetaSeguraDb.getVagonetaSeguraId(),vagonetaSeguraDb.getIdProceso(),vagonetaSeguraDb.getIdRubro(),vagonetaSeguraDb.getIdCiudad(),
                    vagonetaSeguraDb.getIdOperador(),vagonetaSeguraDb.getServicio(),vagonetaSeguraDb.getFechaContacto(),vagonetaSeguraDb.getFechaServicio(),
                    vagonetaSeguraDb.getNombreTitular(),vagonetaSeguraDb.getTelefono(),vagonetaSeguraDb.getTelefonoAlternativo(),vagonetaSeguraDb.getPlaca(),
                    vagonetaSeguraDb.getNombreArchivo());
        } catch (Exception ex) {
            log.error("recordVagonetaSegura db exception", ex);
        }


    }

    //===============
        private void recipientLog(RecipientLog  recipientLog){
        log.debug(">>>>>>>: {}", recipientLog);
        String insert = "INSERT INTO deaxs_record.ns_recipient_log( recipient_id, email, phone_number, href,"+
        " bounced, existing, duplicate, invalids, opted_out, collector_id, message_id, service_complain, status, sent, responded) "+
        " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        try {
            jdbcTemplate.update(insert, recipientLog.getRecipientId(),recipientLog.getEmail(),recipientLog.getPhoneNumber(),
            recipientLog.getHref(),recipientLog.getBounced(),recipientLog.getExisting(),recipientLog.getDuplicate(),recipientLog.getInvalids(),
            recipientLog.getOptedOut(),recipientLog.getCollectorId(),recipientLog.getMessageId(), recipientLog.getServiceComplain(),recipientLog.getStatus(),
            recipientLog.getSent(),recipientLog.getResponded());
        } catch (Exception ex){
            log.error("recordRecipientLog log exception", ex);
        }
    }

    public void recordRecipientLog(RecipientResponse recipientResponse,String collectorId,String messageId,String serviceComplain){
        List<Recipient> succeededs = recipientResponse.getSucceeded();
        for(Recipient recipient:succeededs){
            RecipientLog  recipientLog = new RecipientLog();
            recipientLog.setRecipientId(recipient.getId()); 
            recipientLog.setEmail(recipient.getEmail());
            recipientLog.setPhoneNumber(recipient.getPhone_number());
            recipientLog.setHref(recipient.getHref());
            recipientLog.setCollectorId(collectorId);
            recipientLog.setMessageId(messageId);
            recipientLog.setServiceComplain(serviceComplain);
            recipientLog.setStatus(Constant.ACTIVE_STATUS);
            recipientLog.setBounced(Utils.stringJson(recipientResponse.getBounced()));
            recipientLog.setExisting(Utils.stringJson(recipientResponse.getExisting()));
            recipientLog.setDuplicate(Utils.stringJson(recipientResponse.getDuplicate()));
            recipientLog.setInvalids(Utils.stringJson(recipientResponse.getInvalids()));
            recipientLog.setOptedOut(Utils.stringJson(recipientResponse.getOpted_out()));
            recipientLog.setSent(Constant.SENT_YES);
            recipientLog(recipientLog);
            updateContactRecipient(recipient.getId(),collectorId,messageId,recipient.getEmail());
        }

    }

    public void recordContactLog(ContactLog  contactLog){
        log.debug(">>>>>>>: {}", contactLog);
        String insert = "INSERT INTO deaxs_record.ns_contact_log ( first_name, last_name, email, custom_field_1, "+
                        "custom_field_2, custom_field_3, custom_field_4, custom_field_5, custom_field_6, custom_field_7, "+
                        "custom_field_8, custom_field_9, custom_field_10, custom_field_11, custom_field_12, custom_field_13,"+
                        "custom_field_14, custom_field_15, custom_field_16, custom_field_17, custom_field_18, custom_field_19,"+
                        "custom_field_20, custom_field_21, custom_field_22, custom_field_23, custom_field_24, custom_field_25,"+
                        "custom_field_26, custom_field_27, custom_field_28, custom_field_29, custom_field_30, "+
                        "custom_field_31, custom_field_32, custom_field_33, custom_field_34, custom_field_35, custom_field_36,"+
                        "custom_field_37, custom_field_38, custom_field_39, custom_field_40, custom_field_41, custom_field_42,"+
                        "custom_field_43, custom_field_44, custom_field_45, custom_field_46, custom_field_47, custom_field_48,"+
                        "custom_field_49, custom_field_50,  contact_status, contact_id, contact_href, service_complain"+
                        ",recipient_id,collector_id,message_id ,status, nombre_archivo, unique_id) "+
               "  VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ? "+
               ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        try {
            jdbcTemplate.update(insert, contactLog.getFirstName(),contactLog.getLastName(),contactLog.getEmail(),contactLog.getCustomField1()
            ,contactLog.getCustomField2(),contactLog.getCustomField3(),contactLog.getCustomField4(),contactLog.getCustomField5(),contactLog.getCustomField6()
            ,contactLog.getCustomField7(),contactLog.getCustomField8(),contactLog.getCustomField9(),contactLog.getCustomField10(),contactLog.getCustomField11()
            ,contactLog.getCustomField12(),contactLog.getCustomField13(),contactLog.getCustomField14(),contactLog.getCustomField15(),contactLog.getCustomField16()
            ,contactLog.getCustomField17(),contactLog.getCustomField18(),contactLog.getCustomField19(),contactLog.getCustomField20(),contactLog.getCustomField21()
            ,contactLog.getCustomField22(),contactLog.getCustomField23(),contactLog.getCustomField24(),contactLog.getCustomField25(),contactLog.getCustomField26()
            ,contactLog.getCustomField27(),contactLog.getCustomField28(),contactLog.getCustomField29(),contactLog.getCustomField30()
            ,contactLog.getCustomField31(),contactLog.getCustomField32(),contactLog.getCustomField33(),contactLog.getCustomField34(),contactLog.getCustomField35()
            ,contactLog.getCustomField36(),contactLog.getCustomField37(),contactLog.getCustomField38(),contactLog.getCustomField39(),contactLog.getCustomField40()
            ,contactLog.getCustomField41(),contactLog.getCustomField42(),contactLog.getCustomField43(),contactLog.getCustomField44(),contactLog.getCustomField45()
            ,contactLog.getCustomField46(),contactLog.getCustomField47(),contactLog.getCustomField48(),contactLog.getCustomField49(),contactLog.getCustomField50()
            , contactLog.getContactStatus(),contactLog.getContactId(),contactLog.getContactHref(),contactLog.getServiceComplain()
            ,contactLog.getRecipientId(),contactLog.getCollectorId(),contactLog.getMessageId(),contactLog.getStatus(),contactLog.getNombreArchivo(),contactLog.getUniqueId());
        } catch (Exception ex){
            log.error("recordContactLog log exception", ex);
        }
    }
    public void recordInvalidRepetedContactLog(ContactLog  contactLog){
        log.debug(">>>>>>>: {}", contactLog);
        String insert = "INSERT INTO deaxs_record.ns_invalid_repeted_contact_log ( first_name, last_name, email, custom_field_1, "+
                "custom_field_2, custom_field_3, custom_field_4, custom_field_5, custom_field_6, custom_field_7, "+
                "custom_field_8, custom_field_9, custom_field_10, custom_field_11, custom_field_12, custom_field_13,"+
                "custom_field_14, custom_field_15, custom_field_16, custom_field_17, custom_field_18, custom_field_19,"+
                "custom_field_20, custom_field_21, custom_field_22, custom_field_23, custom_field_24, custom_field_25,"+
                "custom_field_26, custom_field_27, custom_field_28, custom_field_29, custom_field_30, "+
                "custom_field_31, custom_field_32, custom_field_33, custom_field_34, custom_field_35, custom_field_36,"+
                "custom_field_37, custom_field_38, custom_field_39, custom_field_40, custom_field_41, custom_field_42,"+
                "custom_field_43, custom_field_44, custom_field_45, custom_field_46, custom_field_47, custom_field_48,"+
                "custom_field_49, custom_field_50,  contact_status, contact_id, contact_href, service_complain"+
                ",recipient_id,collector_id,message_id ,status, nombre_archivo, unique_id,email_status) "+
                "  VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ? "+
                ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(insert, contactLog.getFirstName(),contactLog.getLastName(),contactLog.getEmail(),contactLog.getCustomField1()
                    ,contactLog.getCustomField2(),contactLog.getCustomField3(),contactLog.getCustomField4(),contactLog.getCustomField5(),contactLog.getCustomField6()
                    ,contactLog.getCustomField7(),contactLog.getCustomField8(),contactLog.getCustomField9(),contactLog.getCustomField10(),contactLog.getCustomField11()
                    ,contactLog.getCustomField12(),contactLog.getCustomField13(),contactLog.getCustomField14(),contactLog.getCustomField15(),contactLog.getCustomField16()
                    ,contactLog.getCustomField17(),contactLog.getCustomField18(),contactLog.getCustomField19(),contactLog.getCustomField20(),contactLog.getCustomField21()
                    ,contactLog.getCustomField22(),contactLog.getCustomField23(),contactLog.getCustomField24(),contactLog.getCustomField25(),contactLog.getCustomField26()
                    ,contactLog.getCustomField27(),contactLog.getCustomField28(),contactLog.getCustomField29(),contactLog.getCustomField30()
                    ,contactLog.getCustomField31(),contactLog.getCustomField32(),contactLog.getCustomField33(),contactLog.getCustomField34(),contactLog.getCustomField35()
                    ,contactLog.getCustomField36(),contactLog.getCustomField37(),contactLog.getCustomField38(),contactLog.getCustomField39(),contactLog.getCustomField40()
                    ,contactLog.getCustomField41(),contactLog.getCustomField42(),contactLog.getCustomField43(),contactLog.getCustomField44(),contactLog.getCustomField45()
                    ,contactLog.getCustomField46(),contactLog.getCustomField47(),contactLog.getCustomField48(),contactLog.getCustomField49(),contactLog.getCustomField50()
                    , contactLog.getContactStatus(),contactLog.getContactId(),contactLog.getContactHref(),contactLog.getServiceComplain()
                    ,contactLog.getRecipientId(),contactLog.getCollectorId(),contactLog.getMessageId(),contactLog.getStatus(),contactLog.getNombreArchivo(),contactLog.getUniqueId(),contactLog.getEmailStatus());
        } catch (Exception ex){
            log.error("recordInvalidRepetedContactLog log exception", ex);
        }
    }


    public void recorErrorlog(ErrorLog errorLog) {
        log.debug("{}", errorLog);
        String insert = "INSERT INTO deaxs_record.ns_error_log ( error_code, error_description, remarks, error_detail) "+
         " VALUES( ?, ?, ?, ?);";
        try {
            jdbcTemplate.update(insert, errorLog.getErrorCode(),errorLog.getErrorDescription(),errorLog.getRemarks(),errorLog.getErrorDetail());
        } catch (Exception ex) {
            log.error("recorErrorlog db exception", ex);
        }
    }
    public void recorJiralog(JiraLog jiraLog) {
        log.debug("{}", jiraLog);
        String insert = "INSERT INTO deaxs_record.ns_jira_log (issue_id, issue_key, request_type_id, service_desk_Id,service_complain, nombre_archivo, unique_id) "+
        " VALUES( ?, ?, ?, ?,?,?,?)";
        try {
            jdbcTemplate.update(insert, jiraLog.getIssueId(),jiraLog.getIssueKey(),jiraLog.getRequestTypeId(),jiraLog.getServiceDeskId(),jiraLog.getServiceComplain(),jiraLog.getNombreArchivo(),jiraLog.getUniqueId());
        } catch (Exception ex) {
            log.error("recorJiralog db exception", ex);
        }
    }

    public void updateContactRecipient(String recipientId,String collectorId,String messageId,String email) {
        log.debug("recipientId {},collectorId {},messageId {},email {} ", recipientId,collectorId,messageId,email);
        String update = "UPDATE deaxs_record.ns_contact_log SET  recipient_id=?, collector_id=?, message_id=? WHERE email=?";
        try {
            jdbcTemplate.update(update,recipientId,collectorId,messageId,email );
        } catch (Exception ex) {
            log.error("updateContactRecipient db exception", ex);
        }
    }

    public void recorArchivoCargado(String nombreArchivo,int cantidadFilasArchivo,String serviceComplain) {
        log.debug("ArchivoCargado {} , {} , {}",  nombreArchivo,cantidadFilasArchivo,serviceComplain);
        String insert = "INSERT INTO deaxs_record.ns_archivos_cargados ( nombre_archivo, cantidad_fila_archivo, "+
        " service_complain) VALUES( ?, ?, ? )";
        try {
            jdbcTemplate.update(insert, nombreArchivo,cantidadFilasArchivo,serviceComplain);
        } catch (Exception ex) {
            log.error("recorArchivoCargado db exception", ex);
        }
    }

        public void recordWebhook(WebhookLog  webhookLog){
        log.debug(">>>>>>>: {}", webhookLog);
        String insert = "INSERT INTO deaxs_record.ns_webhook_log (name, filter_type, filter_id, event_type, event_id, event_datetime, object_type, "+
        " `object_id`, respondent_id, recipient_id, survey_id, user_id, collector_id, status, contact_updated) "+
        " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insert,webhookLog.getName(),webhookLog.getFilterType(),webhookLog.getFilterId(),webhookLog.getEventType(),
            webhookLog.getEventId(),webhookLog.getEventDatetime(),webhookLog.getObjectType(),webhookLog.getObjectId(),webhookLog.getRespondentId(),
            webhookLog.getRecipientId(),webhookLog.getSurveyId(),webhookLog.getUserId(),webhookLog.getCollectorId(),webhookLog.getStatus(),webhookLog.getContactUpdated());
        } catch (Exception ex){
            log.error("recordWebhook log exception", ex);
        }
    }

    public int updateRecipientResponded(String collectorId, String recipientId,String responded){
        int updateRows =0;
        String update="UPDATE deaxs_record.ns_recipient_log SET responded= ? WHERE collector_id = ? AND recipient_id = ?";
        try {
             updateRows= jdbcTemplate.update(update, responded,collectorId,recipientId);
        }catch (Exception ex){
            log.error("updateRecipientResponded log exception",ex);
        }
        return updateRows;
    }

    public List<ArchivoCargadoDb> retrieveArchivosCargados(){
        String sqlSelect="SELECT ns_archivos_cargos_id, nombre_archivo, cantidad_fila_archivo, service_complain, status, record_date " +
                " FROM deaxs_record.ns_archivos_cargados " +
                " WHERE record_date >= CURRENT_DATE - INTERVAL 10 DAY " +
                " AND status!= 'I' "+
                " ORDER BY record_date DESC";
        List<ArchivoCargadoDb> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new ArchivoCargadoDb(rs.getInt("ns_archivos_cargos_id"),
                            rs.getString("nombre_archivo"),
                            rs.getInt("cantidad_fila_archivo"),
                            rs.getString("service_complain"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveArchivosCargados fail", ex);
        }
        return result;
    }
}
