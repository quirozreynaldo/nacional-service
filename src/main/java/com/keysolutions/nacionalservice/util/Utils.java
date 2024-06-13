package com.keysolutions.nacionalservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keysolutions.nacionalservice.model.*;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.model.log.*;
import com.keysolutions.nacionalservice.model.survey.Contact;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Utils {
    public static AsistenciaVialDb convertAsistenciaVialDb(AsistenciaVial asistenciaVial, String nombreArchivo) {
        AsistenciaVialDb asistenciaVialDb = new AsistenciaVialDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        asistenciaVial.setUniqueId(uniqueId);
        asistenciaVialDb.setAsistenciaVialId(uniqueId);
        asistenciaVialDb.setIdProceso(asistenciaVial.getIdProceso());
        asistenciaVialDb.setIdRubro(asistenciaVial.getIdRubro());
        asistenciaVialDb.setIdCiudad(asistenciaVial.getIdCiudad());
        asistenciaVialDb.setIdOperador(asistenciaVial.getIdOperador());
        asistenciaVialDb.setIdSiniestro(asistenciaVial.getIdSiniestro());
        if (asistenciaVial.getFechaContacto()!=null && asistenciaVial.getFechaContacto().trim().length() > 0) {
            asistenciaVialDb.setFechaContacto(stringToDate(asistenciaVial.getFechaContacto()));
        }else{
            asistenciaVialDb.setFechaContacto(getCurrentDate());
        }
        asistenciaVialDb.setNombreTitular(convertCharset(asistenciaVial.getNombreTitular()));
        asistenciaVialDb.setNombreSolicitante(convertCharset(asistenciaVial.getNombreSolicitante()));
        asistenciaVialDb.setTelefono(asistenciaVial.getTelefono());
        asistenciaVialDb.setTelefonoAlternativo(asistenciaVial.getTelefonoAlternativo());
        asistenciaVialDb.setPlaca(asistenciaVial.getPlaca());
        asistenciaVialDb.setNombreArchivo(nombreArchivo);
        return asistenciaVialDb;
    }

    public static Nsvs800Db convertNsvs800Db(Nsvs800 nsvs800, String nombreArchivo) {
        Nsvs800Db nsvs800Db = new Nsvs800Db();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        nsvs800.setUniqueId(uniqueId);
        nsvs800Db.setNSvs800Id(uniqueId);
        nsvs800Db.setIdProceso(nsvs800.getIdProceso());
        nsvs800Db.setIdRubro(nsvs800.getIdRubro());
        nsvs800Db.setIdCiudad(nsvs800.getIdCiudad());
        nsvs800Db.setIdOperador(convertCharset(nsvs800.getIdOperador()));
        nsvs800Db.setServicio(convertCharset(nsvs800.getServicio()));
        if (nsvs800Db.getFechaContacto()!=null && nsvs800Db.getFechaContacto().trim().length() > 0) {
            nsvs800Db.setFechaContacto(stringToDate(nsvs800.getFechaContacto()));
        }else{
            nsvs800Db.setFechaContacto(getCurrentDate());
        }
        nsvs800Db.setIdObjeto(nsvs800.getIdObjeto());
        nsvs800Db.setNombreTitular(convertCharset(nsvs800.getNombreTitular()));
        nsvs800Db.setNombreSolicitante(convertCharset(nsvs800.getNombreSolicitante()));
        nsvs800Db.setTelefono(nsvs800.getTelefono());
        nsvs800Db.setEmail(nsvs800.getEmail());
        nsvs800Db.setIdPoliza(nsvs800.getIdPoliza());
        nsvs800Db.setNombreArchivo(nombreArchivo);
        return nsvs800Db;
    }

    public static ConsultasReclamosDb convertConsultaReclamo(ConsultasReclamos consultasReclamos,String nombreArchivo) {
        ConsultasReclamosDb consultasReclamosDb = new ConsultasReclamosDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        consultasReclamos.setUniqueId(uniqueId);
        consultasReclamosDb.setConsultaReclamoId(uniqueId);
        consultasReclamosDb.setIdProceso(consultasReclamos.getIdProceso());
        consultasReclamosDb.setIdRubro(consultasReclamos.getIdRubro());
        consultasReclamosDb.setIdCiudad(consultasReclamos.getIdCiudad());
        consultasReclamosDb.setIdOperador(convertCharset(consultasReclamos.getIdOperador()));
        consultasReclamosDb.setServicio(convertCharset(consultasReclamos.getServicio()));
        if (consultasReclamosDb.getFechaContacto()!=null && consultasReclamosDb.getFechaContacto().trim().length() > 0) {
            consultasReclamosDb.setFechaContacto(stringToDate(consultasReclamos.getFechaContacto()));
        }else{
            consultasReclamosDb.setFechaContacto(getCurrentDate());
        }
        consultasReclamosDb.setFechaCierreFreshDesk(stringToDate(consultasReclamos.getFechaCierreFreshDesk()));
        consultasReclamosDb.setIdObjeto(consultasReclamos.getIdObjeto());
        consultasReclamosDb.setNombreTitular(convertCharset(consultasReclamos.getNombreTitular()));
        consultasReclamosDb.setNombreSolicitante(convertCharset(consultasReclamos.getNombreSolicitante()));
        consultasReclamosDb.setTelefono(consultasReclamos.getTelefono());
        consultasReclamosDb.setEmail(consultasReclamos.getEmail());
        consultasReclamosDb.setIdPoliza(consultasReclamos.getIdPoliza());
        consultasReclamosDb.setAsunto(convertCharset(consultasReclamos.getAsunto()));
        consultasReclamosDb.setNombreArchivo(nombreArchivo);
        return consultasReclamosDb;
    }
    public static ConsultasReclamos convertConsultaReclamoReminder(ConsultasReclamosDb consultasReclamosDb) {
        ConsultasReclamos consultasReclamos = new ConsultasReclamos();
        consultasReclamos.setUniqueId(consultasReclamosDb.getConsultaReclamoId());
        consultasReclamos.setIdProceso(consultasReclamosDb.getIdProceso());
        consultasReclamos.setIdRubro(consultasReclamosDb.getIdRubro());
        consultasReclamos.setIdCiudad(consultasReclamosDb.getIdCiudad());
        consultasReclamos.setIdOperador(convertCharset(consultasReclamosDb.getIdOperador()));
        consultasReclamos.setServicio(convertCharset(consultasReclamosDb.getServicio()));
        consultasReclamos.setFechaContacto(consultasReclamosDb.getFechaContacto());
        consultasReclamos.setFechaCierreFreshDesk(consultasReclamosDb.getFechaCierreFreshDesk());
        consultasReclamos.setIdObjeto(consultasReclamosDb.getIdObjeto());
        consultasReclamos.setNombreTitular(convertCharset(consultasReclamosDb.getNombreTitular()));
        consultasReclamos.setNombreSolicitante(convertCharset(consultasReclamosDb.getNombreSolicitante()));
        consultasReclamos.setTelefono(consultasReclamosDb.getTelefono());
        consultasReclamos.setEmail(consultasReclamosDb.getEmail());
        consultasReclamos.setIdPoliza(consultasReclamosDb.getIdPoliza());
        consultasReclamos.setAsunto(convertCharset(consultasReclamosDb.getAsunto()));
        return consultasReclamos;
    }

    public static AtencionInicialDb convertAtencionInicialDb(AtencionInicial atencionInicial, String nombreArchivo) {
        AtencionInicialDb atencionInicialDb = new AtencionInicialDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        atencionInicial.setUniqueId(uniqueId);
        atencionInicialDb.setAtencionInicialId(uniqueId);
        atencionInicialDb.setIdProceso(atencionInicial.getIdProceso());
        atencionInicialDb.setIdRubro(atencionInicial.getIdRubro());
        atencionInicialDb.setIdCiudad(atencionInicial.getIdCiudad());
        atencionInicialDb.setIdOperador(convertCharset(atencionInicial.getIdOperador()));
        atencionInicialDb.setIdEjecutivo(convertCharset(atencionInicial.getIdEjecutivo()));
        if (atencionInicialDb.getFechaContacto()!=null && atencionInicialDb.getFechaContacto().trim().length() > 0) {
            atencionInicialDb.setFechaContacto(stringToDate(atencionInicial.getFechaContacto()));
        }else{
            atencionInicialDb.setFechaContacto(getCurrentDate());
        }
        atencionInicialDb.setPlaca(atencionInicial.getPlaca());
        atencionInicialDb.setNombreTitular(convertCharset(atencionInicial.getNombreTitular()));
        atencionInicialDb.setNombreSolicitante(atencionInicial.getNombreSolicitante());
        atencionInicialDb.setTelefono(atencionInicial.getTelefono());
        atencionInicialDb.setEmail(atencionInicial.getEmail());
        atencionInicialDb.setIdPoliza(atencionInicial.getIdPoliza());
        atencionInicialDb.setNombreArchivo(nombreArchivo);
        return atencionInicialDb;
    }
    public static AtencionInicial convertAtencionInicialReminder(AtencionInicialDb atencionInicialDb) {
        AtencionInicial atencionInicial = new AtencionInicial();
        atencionInicial.setUniqueId(atencionInicialDb.getUniqueId());
        atencionInicial.setIdProceso(atencionInicialDb.getIdProceso());
        atencionInicial.setIdRubro(atencionInicialDb.getIdRubro());
        atencionInicial.setIdCiudad(atencionInicialDb.getIdCiudad());
        atencionInicial.setIdOperador(convertCharset(atencionInicialDb.getIdOperador()));
        atencionInicial.setIdEjecutivo(convertCharset(atencionInicialDb.getIdEjecutivo()));
        atencionInicial.setFechaContacto(atencionInicialDb.getFechaContacto());
        atencionInicial.setPlaca(atencionInicialDb.getPlaca());
        atencionInicial.setNombreTitular(convertCharset(atencionInicialDb.getNombreTitular()));
        atencionInicial.setNombreSolicitante(atencionInicialDb.getNombreSolicitante());
        atencionInicial.setTelefono(atencionInicialDb.getTelefono());
        atencionInicial.setEmail(atencionInicialDb.getEmail());
        atencionInicial.setIdPoliza(atencionInicialDb.getIdPoliza());
        return atencionInicial;
    }

    public static AccidentesPersonalesDb convertAccidentePersonalDB(AccidentesPersonales accidentesPersonales,
            String nombreArchivo) {
        AccidentesPersonalesDb accidentesPersonalesDb = new AccidentesPersonalesDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        accidentesPersonales.setUniqueId(uniqueId);
        accidentesPersonalesDb.setAccidentePersonalId(uniqueId);
        accidentesPersonalesDb.setIdProceso(accidentesPersonales.getIdProceso());
        accidentesPersonalesDb.setIdRubro(accidentesPersonales.getIdRubro());
        accidentesPersonalesDb.setIdCiudad(accidentesPersonales.getIdCiudad());
        accidentesPersonalesDb.setIdOperador(convertCharset(accidentesPersonales.getIdOperador()));
        accidentesPersonalesDb.setServicio(convertCharset(accidentesPersonales.getServicio()));
        if (accidentesPersonalesDb.getFechaContacto()!=null && accidentesPersonalesDb.getFechaContacto().trim().length() > 0) {
            accidentesPersonalesDb.setFechaContacto(stringToDate(accidentesPersonales.getFechaContacto()));
        }else{
            accidentesPersonalesDb.setFechaContacto(getCurrentDate());
        }
        accidentesPersonalesDb.setPlaca(accidentesPersonales.getPlaca());
        accidentesPersonalesDb.setNombreTitular(convertCharset(accidentesPersonales.getNombreTitular()));
        accidentesPersonalesDb.setNombreSolicitante(convertCharset(accidentesPersonales.getNombreSolicitante()));
        accidentesPersonalesDb.setTelefono(accidentesPersonales.getTelefono());
        accidentesPersonalesDb.setEmail(accidentesPersonales.getEmail());
        accidentesPersonalesDb.setNombreArchivo(nombreArchivo);
        return accidentesPersonalesDb;
    }

    public static CallCenterNspfDb convertCallCenterNspfDb(CallCenterNspf callCenterNspf, String nombreArchivo) {
        CallCenterNspfDb callCenterNspfDb = new CallCenterNspfDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        callCenterNspf.setUniqueId(uniqueId);
        callCenterNspfDb.setCallCenterNspfId(uniqueId);
        callCenterNspfDb.setIdProceso(callCenterNspf.getIdProceso());
        callCenterNspfDb.setIdRubro(callCenterNspf.getIdRubro());
        callCenterNspfDb.setIdCiudad(callCenterNspf.getIdCiudad());
        callCenterNspfDb.setIdSiniestro(convertCharset(callCenterNspf.getIdSiniestro()));
        callCenterNspfDb.setIdOperador(convertCharset(callCenterNspf.getIdOperador()));
        if (callCenterNspfDb.getFechaContacto()!=null && callCenterNspfDb.getFechaContacto().trim().length() > 0) {
            callCenterNspfDb.setFechaContacto(stringToDate(callCenterNspf.getFechaContacto()));
        }else{
            callCenterNspfDb.setFechaContacto(getCurrentDate());
        }
        callCenterNspfDb.setIdCliente(callCenterNspf.getIdCliente());
        callCenterNspfDb.setNombreArchivo(nombreArchivo);
        return callCenterNspfDb;
    }
    public static InmedicalAtuMedidaDb convertInmedicalAtuMedidaDb(InmedicalAtuMedida inmedicalAtuMedida, String nombreArchivo) {
        InmedicalAtuMedidaDb inmedicalAtuMedidaDb = new InmedicalAtuMedidaDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        inmedicalAtuMedida.setUniqueId(uniqueId);
        inmedicalAtuMedidaDb.setInmediacalAtuId(uniqueId);
        inmedicalAtuMedidaDb.setIdProceso(inmedicalAtuMedida.getIdProceso());
        inmedicalAtuMedidaDb.setIdRubro(inmedicalAtuMedida.getIdRubro());
        inmedicalAtuMedidaDb.setIdCiudad(inmedicalAtuMedida.getIdCiudad());
        inmedicalAtuMedidaDb.setProveedor(convertCharset(inmedicalAtuMedida.getProveedor()));
        inmedicalAtuMedidaDb.setIdCentro(convertCharset(inmedicalAtuMedida.getIdCentro()));
        inmedicalAtuMedidaDb.setServicio(convertCharset(inmedicalAtuMedida.getServicio()));
        if (inmedicalAtuMedidaDb.getFechaContacto()!=null && inmedicalAtuMedidaDb.getFechaContacto().trim().length() > 0) {
            inmedicalAtuMedidaDb.setFechaContacto(stringToDate(inmedicalAtuMedida.getFechaContacto()));
        }else{
            inmedicalAtuMedidaDb.setFechaContacto(getCurrentDate());
        }
        inmedicalAtuMedidaDb.setIdDocumento(inmedicalAtuMedida.getIdDocumento());
        inmedicalAtuMedidaDb.setNombreTitular(convertCharset(inmedicalAtuMedida.getNombreTitular()));
        inmedicalAtuMedidaDb.setNombreSolicitante(convertCharset(inmedicalAtuMedida.getNombreSolicitante()));
        inmedicalAtuMedidaDb.setTelefono(inmedicalAtuMedida.getTelefono());
        inmedicalAtuMedidaDb.setEmail(inmedicalAtuMedida.getEmail());
        inmedicalAtuMedidaDb.setNombreArchivo(nombreArchivo);
        return inmedicalAtuMedidaDb;
    }
	public static InmedicalAtuMedida convertInmedicalAtuMedidaRemainder(InmedicalAtuMedidaDb inmedicalAtuMedidaDb) {
        InmedicalAtuMedida inmedicalAtuMedida = new InmedicalAtuMedida();
        inmedicalAtuMedida.setUniqueId(inmedicalAtuMedidaDb.getUniqueId());
        inmedicalAtuMedida.setIdProceso(inmedicalAtuMedidaDb.getIdProceso());
        inmedicalAtuMedida.setIdRubro(inmedicalAtuMedidaDb.getIdRubro());
        inmedicalAtuMedida.setIdCiudad(inmedicalAtuMedidaDb.getIdCiudad());
        inmedicalAtuMedida.setProveedor(convertCharset(inmedicalAtuMedidaDb.getProveedor()));
        inmedicalAtuMedida.setIdCentro(convertCharset(inmedicalAtuMedidaDb.getIdCentro()));
        inmedicalAtuMedida.setServicio(convertCharset(inmedicalAtuMedidaDb.getServicio()));
        inmedicalAtuMedida.setFechaContacto(inmedicalAtuMedidaDb.getFechaContacto());
        inmedicalAtuMedida.setIdDocumento(inmedicalAtuMedidaDb.getIdDocumento());
        inmedicalAtuMedida.setNombreTitular(convertCharset(inmedicalAtuMedidaDb.getNombreTitular()));
        inmedicalAtuMedida.setNombreSolicitante(convertCharset(inmedicalAtuMedidaDb.getNombreSolicitante()));
        inmedicalAtuMedida.setTelefono(inmedicalAtuMedidaDb.getTelefono());
        inmedicalAtuMedida.setEmail(inmedicalAtuMedidaDb.getEmail());
        return inmedicalAtuMedida;
    }
    public static InmedicalBancoGanaderoDb convertInmedicalBancoGanaderoDb(InmedicalBancoGanadero inmedicalBancoGanadero, String nombreArchivo) {
        InmedicalBancoGanaderoDb inmedicalBancoGanaderoDb = new InmedicalBancoGanaderoDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        inmedicalBancoGanadero.setUniqueId(uniqueId);
        inmedicalBancoGanaderoDb.setInmediacalBancoId(uniqueId);
        inmedicalBancoGanaderoDb.setIdProceso(inmedicalBancoGanadero.getIdProceso());
        inmedicalBancoGanaderoDb.setIdRubro(inmedicalBancoGanadero.getIdRubro());
        inmedicalBancoGanaderoDb.setIdCiudad(inmedicalBancoGanadero.getIdCiudad());
        inmedicalBancoGanaderoDb.setProveedor(convertCharset(inmedicalBancoGanadero.getProveedor()));
        inmedicalBancoGanaderoDb.setIdCentro(convertCharset(inmedicalBancoGanadero.getIdCentro()));
        inmedicalBancoGanaderoDb.setServicio(convertCharset(inmedicalBancoGanadero.getServicio()));
        if (inmedicalBancoGanaderoDb.getFechaContacto()!=null && inmedicalBancoGanaderoDb.getFechaContacto().trim().length() > 0) {
            inmedicalBancoGanaderoDb.setFechaContacto(stringToDate(inmedicalBancoGanadero.getFechaContacto()));
        }else{
            inmedicalBancoGanaderoDb.setFechaContacto(getCurrentDate());
        }
        inmedicalBancoGanaderoDb.setIdDocumento(inmedicalBancoGanadero.getIdDocumento());
        inmedicalBancoGanaderoDb.setNombreTitular(convertCharset(inmedicalBancoGanadero.getNombreTitular()));
        inmedicalBancoGanaderoDb.setNombreSolicitante(convertCharset(inmedicalBancoGanadero.getNombreSolicitante()));
        inmedicalBancoGanaderoDb.setTelefono(inmedicalBancoGanadero.getTelefono());
        inmedicalBancoGanaderoDb.setEmail(inmedicalBancoGanadero.getEmail());
        inmedicalBancoGanaderoDb.setNombreArchivo(nombreArchivo);
        return inmedicalBancoGanaderoDb;
    }
    public static TallersE2eDb convertTallersE2eDb(TallersE2e tallersE2e, String nombreArchivo) {
        TallersE2eDb tallersE2eDb = new TallersE2eDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        tallersE2e.setUniqueId(uniqueId);
        tallersE2eDb.setTalleresE2eId(uniqueId);
        tallersE2eDb.setIdProceso(tallersE2e.getIdProceso());
        tallersE2eDb.setIdRubro(tallersE2e.getIdRubro());
        tallersE2eDb.setIdCiudad(tallersE2e.getIdCiudad());
        tallersE2eDb.setIdOperador(convertCharset(tallersE2e.getIdOperador()));
        tallersE2eDb.setServicio(convertCharset(tallersE2e.getServicio()));
        if (tallersE2eDb.getFechaContacto()!=null && tallersE2eDb.getFechaContacto().trim().length() > 0) {
            tallersE2eDb.setFechaContacto(stringToDate(tallersE2e.getFechaContacto()));
        }else{
            tallersE2eDb.setFechaContacto(getCurrentDate());
        }
        tallersE2eDb.setPeriodo(convertCharset(tallersE2e.getPeriodo()));
        tallersE2eDb.setNombreTitular(convertCharset(tallersE2e.getNombreTitular()));
        tallersE2eDb.setNombreSolicitante(convertCharset(tallersE2e.getNombreSolicitante()));
        tallersE2eDb.setTelefono(tallersE2e.getTelefono());
        tallersE2eDb.setMarca(convertCharset(tallersE2e.getMarca()));
        tallersE2eDb.setModelo(convertCharset(tallersE2e.getModelo()));
        tallersE2eDb.setPlaca(convertCharset(tallersE2e.getPlaca()));
        tallersE2eDb.setColor(convertCharset(tallersE2e.getColor()));
        tallersE2eDb.setIdPoliza(convertCharset(tallersE2e.getIdPoliza()));
        tallersE2eDb.setIntermediario(convertCharset(tallersE2e.getIntermediario()));
        tallersE2eDb.setEmail(tallersE2e.getEmail());
        tallersE2eDb.setNombreArchivo(nombreArchivo);
        return tallersE2eDb;
    }
	public static TallersE2e convertTallersE2eReminder(TallersE2eDb tallersE2eDb) {
        TallersE2e tallersE2e = new TallersE2e();
        tallersE2e.setUniqueId(tallersE2eDb.getUniqueId());
        tallersE2e.setIdProceso(tallersE2eDb.getIdProceso());
        tallersE2e.setIdRubro(tallersE2eDb.getIdRubro());
        tallersE2e.setIdCiudad(tallersE2eDb.getIdCiudad());
        tallersE2e.setIdOperador(convertCharset(tallersE2eDb.getIdOperador()));
        tallersE2e.setServicio(convertCharset(tallersE2eDb.getServicio()));
        tallersE2e.setFechaContacto(tallersE2eDb.getFechaContacto());
        tallersE2e.setPeriodo(convertCharset(tallersE2eDb.getPeriodo()));
        tallersE2e.setNombreTitular(convertCharset(tallersE2eDb.getNombreTitular()));
        tallersE2e.setNombreSolicitante(convertCharset(tallersE2eDb.getNombreSolicitante()));
        tallersE2e.setTelefono(tallersE2eDb.getTelefono());
        tallersE2e.setMarca(convertCharset(tallersE2eDb.getMarca()));
        tallersE2e.setModelo(convertCharset(tallersE2eDb.getModelo()));
        tallersE2e.setPlaca(convertCharset(tallersE2eDb.getPlaca()));
        tallersE2e.setColor(convertCharset(tallersE2eDb.getColor()));
        tallersE2e.setIdPoliza(convertCharset(tallersE2eDb.getIdPoliza()));
        tallersE2e.setIntermediario(convertCharset(tallersE2eDb.getIntermediario()));
        tallersE2e.setEmail(tallersE2eDb.getEmail());
        return tallersE2e;
    }
    public static CentroRehaOdontoDb convertCentroRehaOdontoDb(CentroRehaOdonto centroRehaOdonto, String nombreArchivo) {
        CentroRehaOdontoDb centroRehaOdontoDb = new CentroRehaOdontoDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        centroRehaOdonto.setUniqueId(uniqueId);
        centroRehaOdontoDb.setCentroRehaOdontoId(uniqueId);
        centroRehaOdontoDb.setIdProceso(centroRehaOdonto.getIdProceso());
        centroRehaOdontoDb.setIdRubro(centroRehaOdonto.getIdRubro());
        centroRehaOdontoDb.setIdCiudad(centroRehaOdonto.getIdCiudad());
        centroRehaOdontoDb.setIdOperador(convertCharset(centroRehaOdonto.getIdOperador()));
        centroRehaOdontoDb.setCentroMedico(convertCharset(centroRehaOdonto.getCentroMedico()));
        if (centroRehaOdontoDb.getFechaContacto()!=null && centroRehaOdontoDb.getFechaContacto().trim().length() > 0) {
            centroRehaOdontoDb.setFechaContacto(stringToDate(centroRehaOdonto.getFechaContacto()));
        }else{
            centroRehaOdontoDb.setFechaContacto(getCurrentDate());
        }
        centroRehaOdontoDb.setFechaSalida(stringToDate(centroRehaOdonto.getFechaSalida()));
        centroRehaOdontoDb.setPaciente(convertCharset(centroRehaOdonto.getPaciente()));
        centroRehaOdontoDb.setPlan(convertCharset(centroRehaOdonto.getPlan()));
        centroRehaOdontoDb.setTelefono(centroRehaOdonto.getTelefono());
        centroRehaOdontoDb.setEmail(centroRehaOdonto.getEmail());
        centroRehaOdontoDb.setTelefonoAdicional(centroRehaOdonto.getTelefonoAdicional());
        centroRehaOdontoDb.setNombreArchivo(nombreArchivo);
        return centroRehaOdontoDb;
    }
    public static CentroRehaOdonto convertCentroRehaOdontoReminder(CentroRehaOdontoDb centroRehaOdontoDb) {
        CentroRehaOdonto centroRehaOdonto = new CentroRehaOdonto();
        centroRehaOdonto.setUniqueId(centroRehaOdontoDb.getUniqueId());
        centroRehaOdonto.setIdProceso(centroRehaOdontoDb.getIdProceso());
        centroRehaOdonto.setIdRubro(centroRehaOdontoDb.getIdRubro());
        centroRehaOdonto.setIdCiudad(centroRehaOdontoDb.getIdCiudad());
        centroRehaOdonto.setIdOperador(convertCharset(centroRehaOdontoDb.getIdOperador()));
        centroRehaOdonto.setCentroMedico(convertCharset(centroRehaOdontoDb.getCentroMedico()));
        centroRehaOdonto.setFechaContacto(centroRehaOdontoDb.getFechaContacto());
        centroRehaOdonto.setFechaSalida(centroRehaOdontoDb.getFechaSalida());
        centroRehaOdonto.setPaciente(convertCharset(centroRehaOdontoDb.getPaciente()));
        centroRehaOdonto.setPlan(convertCharset(centroRehaOdontoDb.getPlan()));
        centroRehaOdonto.setTelefono(centroRehaOdontoDb.getTelefono());
        centroRehaOdonto.setEmail(centroRehaOdontoDb.getEmail());
        centroRehaOdonto.setTelefonoAdicional(centroRehaOdontoDb.getTelefonoAdicional());
        return centroRehaOdonto;
    }
    public static ProvServicioMedicoDb convertProvServMedicoDb(ProvServicioMedico provServicioMedico, String nombreArchivo) {
        ProvServicioMedicoDb provServicioMedicoDb = new ProvServicioMedicoDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        provServicioMedico.setUniqueId(uniqueId);
        provServicioMedicoDb.setProvSerMedId(uniqueId);
        provServicioMedicoDb.setIdProveedor(provServicioMedico.getIdProveedor());
        provServicioMedicoDb.setIdRubro(provServicioMedico.getIdRubro());
        provServicioMedicoDb.setIdCiudad(provServicioMedico.getIdCiudad());
        provServicioMedicoDb.setProducto(convertCharset(provServicioMedico.getProducto()));
        provServicioMedicoDb.setServicio(convertCharset(provServicioMedico.getServicio()));
        provServicioMedicoDb.setFechaDenuncia(stringToDate(provServicioMedico.getFechaDenuncia()));
        if (provServicioMedicoDb.getFechaContacto()!=null && provServicioMedicoDb.getFechaContacto().trim().length() > 0) {
            provServicioMedicoDb.setFechaContacto(stringToDate(provServicioMedico.getFechaContacto()));
        }else{
            provServicioMedicoDb.setFechaContacto(getCurrentDate());
        }
        provServicioMedicoDb.setIdEjecutivo(convertCharset(provServicioMedico.getIdEjecutivo()));
        provServicioMedicoDb.setNombreAsegurado(convertCharset(provServicioMedico.getNombreAsegurado()));
        provServicioMedicoDb.setNombreSolicitante(convertCharset(provServicioMedico.getNombreSolicitante()));
        provServicioMedicoDb.setTelefono(provServicioMedico.getTelefono());
        provServicioMedicoDb.setEmail(provServicioMedico.getEmail());
        provServicioMedicoDb.setPoliza(convertCharset(provServicioMedico.getPoliza()));
        provServicioMedicoDb.setDignostico(convertCharset(provServicioMedico.getDignostico()));
        provServicioMedicoDb.setIntermediario(convertCharset(provServicioMedico.getIntermediario()));
        provServicioMedicoDb.setNombreArchivo(nombreArchivo);
        return provServicioMedicoDb;
    }
    public static ProvServicioMedico convertProvServMedicoReminder(ProvServicioMedicoDb provServicioMedicoDb) {
        ProvServicioMedico provServicioMedico = new ProvServicioMedico();
        provServicioMedico.setUniqueId(provServicioMedicoDb.getUniqueId());
        provServicioMedico.setIdProveedor(provServicioMedicoDb.getIdProveedor());
        provServicioMedico.setIdRubro(provServicioMedicoDb.getIdRubro());
        provServicioMedico.setIdCiudad(provServicioMedicoDb.getIdCiudad());
        provServicioMedico.setProducto(convertCharset(provServicioMedicoDb.getProducto()));
        provServicioMedico.setServicio(convertCharset(provServicioMedicoDb.getServicio()));
        provServicioMedico.setFechaDenuncia(provServicioMedicoDb.getFechaDenuncia());
        provServicioMedico.setFechaContacto(provServicioMedicoDb.getFechaContacto());
        provServicioMedico.setIdEjecutivo(convertCharset(provServicioMedicoDb.getIdEjecutivo()));
        provServicioMedico.setNombreAsegurado(convertCharset(provServicioMedicoDb.getNombreAsegurado()));
        provServicioMedico.setNombreSolicitante(convertCharset(provServicioMedicoDb.getNombreSolicitante()));
        provServicioMedico.setTelefono(provServicioMedicoDb.getTelefono());
        provServicioMedico.setEmail(provServicioMedicoDb.getEmail());
        provServicioMedico.setPoliza(convertCharset(provServicioMedicoDb.getPoliza()));
        provServicioMedico.setDignostico(convertCharset(provServicioMedicoDb.getDignostico()));
        provServicioMedico.setIntermediario(convertCharset(provServicioMedicoDb.getIntermediario()));
        return provServicioMedico;
    }
    public static ProveedorMedicoDb convertProveedorMedicoDb(ProveedorMedico proveedorMedico, String nombreArchivo) {
        ProveedorMedicoDb proveedorMedicoDb = new ProveedorMedicoDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        proveedorMedico.setUniqueId(uniqueId);
        proveedorMedicoDb.setProvMedId(uniqueId);
        proveedorMedicoDb.setIdProveedor(proveedorMedico.getIdProveedor());
        proveedorMedicoDb.setIdRubro(proveedorMedico.getIdRubro());
        proveedorMedicoDb.setIdCiudad(proveedorMedico.getIdCiudad());
        proveedorMedicoDb.setProducto(convertCharset(proveedorMedico.getProducto()));
        proveedorMedicoDb.setServicio(convertCharset(proveedorMedico.getServicio()));
        proveedorMedicoDb.setFechaDenuncia(stringToDate(proveedorMedico.getFechaDenuncia()));
        if (proveedorMedicoDb.getFechaContacto()!=null && proveedorMedicoDb.getFechaContacto().trim().length() > 0) {
            proveedorMedicoDb.setFechaContacto(stringToDate(proveedorMedico.getFechaContacto()));
        }else{
            proveedorMedicoDb.setFechaContacto(getCurrentDate());
        }
        proveedorMedicoDb.setIdEjecutivo(convertCharset(proveedorMedico.getIdEjecutivo()));
        proveedorMedicoDb.setNombreAsegurado(convertCharset(proveedorMedico.getNombreAsegurado()));
        proveedorMedicoDb.setNombreSolicitante(convertCharset(proveedorMedico.getNombreSolicitante()));
        proveedorMedicoDb.setTelefono(proveedorMedico.getTelefono());
        proveedorMedicoDb.setEmail(proveedorMedico.getEmail());
        proveedorMedicoDb.setPoliza(convertCharset(proveedorMedico.getPoliza()));
        proveedorMedicoDb.setDignostico(convertCharset(proveedorMedico.getDignostico()));
        proveedorMedicoDb.setIntermediario(convertCharset(proveedorMedico.getIntermediario()));
        proveedorMedicoDb.setNombreArchivo(nombreArchivo);
        return proveedorMedicoDb;
    }
    public static ProveedorMedico convertProveedorMedicoReminder(ProveedorMedicoDb proveedorMedicoDb) {
        ProveedorMedico proveedorMedico = new ProveedorMedico();
        proveedorMedico.setUniqueId(proveedorMedicoDb.getUniqueId());
        proveedorMedico.setIdProveedor(proveedorMedicoDb.getIdProveedor());
        proveedorMedico.setIdRubro(proveedorMedicoDb.getIdRubro());
        proveedorMedico.setIdCiudad(proveedorMedicoDb.getIdCiudad());
        proveedorMedico.setProducto(convertCharset(proveedorMedicoDb.getProducto()));
        proveedorMedico.setServicio(convertCharset(proveedorMedicoDb.getServicio()));
        proveedorMedico.setFechaDenuncia(proveedorMedicoDb.getFechaDenuncia());
        proveedorMedico.setFechaContacto(proveedorMedicoDb.getFechaContacto());
        proveedorMedico.setIdEjecutivo(convertCharset(proveedorMedicoDb.getIdEjecutivo()));
        proveedorMedico.setNombreAsegurado(convertCharset(proveedorMedicoDb.getNombreAsegurado()));
        proveedorMedico.setNombreSolicitante(convertCharset(proveedorMedicoDb.getNombreSolicitante()));
        proveedorMedico.setTelefono(proveedorMedicoDb.getTelefono());
        proveedorMedico.setEmail(proveedorMedicoDb.getEmail());
        proveedorMedico.setPoliza(convertCharset(proveedorMedicoDb.getPoliza()));
        proveedorMedico.setDignostico(convertCharset(proveedorMedicoDb.getDignostico()));
        proveedorMedico.setIntermediario(convertCharset(proveedorMedicoDb.getIntermediario()));
        return proveedorMedico;
    }
    public static ProveedorFarmaDb convertProveedorFarmaDb(ProveedorFarma proveedorFarma, String nombreArchivo) {
        ProveedorFarmaDb proveedorFarmaDb = new ProveedorFarmaDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        proveedorFarma.setUniqueId(uniqueId);
        proveedorFarmaDb.setProvFarId(uniqueId);
        proveedorFarmaDb.setIdProveedor(proveedorFarma.getIdProveedor());
        proveedorFarmaDb.setIdRubro(proveedorFarma.getIdRubro());
        proveedorFarmaDb.setIdCiudad(proveedorFarma.getIdCiudad());
        proveedorFarmaDb.setProducto(convertCharset(proveedorFarma.getProducto()));
        proveedorFarmaDb.setServicio(convertCharset(proveedorFarma.getServicio()));
        proveedorFarmaDb.setFechaDenuncia(stringToDate(proveedorFarma.getFechaDenuncia()));
        if (proveedorFarmaDb.getFechaContacto()!=null && proveedorFarmaDb.getFechaContacto().trim().length() > 0) {
            proveedorFarmaDb.setFechaContacto(stringToDate(proveedorFarma.getFechaContacto()));
        }else{
            proveedorFarmaDb.setFechaContacto(getCurrentDate());
        }
        proveedorFarmaDb.setIdEjecutivo(convertCharset(proveedorFarma.getIdEjecutivo()));
        proveedorFarmaDb.setNombreAsegurado(convertCharset(proveedorFarma.getNombreAsegurado()));
        proveedorFarmaDb.setNombreSolicitante(convertCharset(proveedorFarma.getNombreSolicitante()));
        proveedorFarmaDb.setTelefono(proveedorFarma.getTelefono());
        proveedorFarmaDb.setEmail(proveedorFarma.getEmail());
        proveedorFarmaDb.setPoliza(convertCharset(proveedorFarma.getPoliza()));
        proveedorFarmaDb.setDignostico(convertCharset(proveedorFarma.getDignostico()));
        proveedorFarmaDb.setIntermediario(convertCharset(proveedorFarma.getIntermediario()));
        proveedorFarmaDb.setNombreArchivo(nombreArchivo);
        return proveedorFarmaDb;
    }
    public static ProveedorFarma convertProveedorFarmaReminder(ProveedorFarmaDb proveedorFarmaDb) {
        ProveedorFarma proveedorFarma = new ProveedorFarma();
        proveedorFarma.setUniqueId(proveedorFarmaDb.getUniqueId());
        proveedorFarma.setIdProveedor(proveedorFarmaDb.getIdProveedor());
        proveedorFarma.setIdRubro(proveedorFarmaDb.getIdRubro());
        proveedorFarma.setIdCiudad(proveedorFarmaDb.getIdCiudad());
        proveedorFarma.setProducto(convertCharset(proveedorFarmaDb.getProducto()));
        proveedorFarma.setServicio(convertCharset(proveedorFarmaDb.getServicio()));
        proveedorFarma.setFechaDenuncia(proveedorFarmaDb.getFechaDenuncia());
        proveedorFarma.setFechaContacto(proveedorFarmaDb.getFechaContacto());
        proveedorFarma.setIdEjecutivo(convertCharset(proveedorFarmaDb.getIdEjecutivo()));
        proveedorFarma.setNombreAsegurado(convertCharset(proveedorFarmaDb.getNombreAsegurado()));
        proveedorFarma.setNombreSolicitante(convertCharset(proveedorFarmaDb.getNombreSolicitante()));
        proveedorFarma.setTelefono(proveedorFarmaDb.getTelefono());
        proveedorFarma.setEmail(proveedorFarmaDb.getEmail());
        proveedorFarma.setPoliza(convertCharset(proveedorFarmaDb.getPoliza()));
        proveedorFarma.setDignostico(convertCharset(proveedorFarmaDb.getDignostico())); // Corrected typo
        proveedorFarma.setIntermediario(convertCharset(proveedorFarmaDb.getIntermediario()));
        return proveedorFarma;
    }
    public static AsistenciaMedDomiDb convertAsistenciaMedDomiDb(AsistenciaMedDomi asistenciaMedDomi, String nombreArchivo) {
        AsistenciaMedDomiDb asistenciaMedDomiDb = new AsistenciaMedDomiDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        asistenciaMedDomi.setUniqueId(uniqueId);
        asistenciaMedDomiDb.setAsisMedDoId(uniqueId);
        asistenciaMedDomiDb.setIdProceso(asistenciaMedDomi.getIdProceso());
        asistenciaMedDomiDb.setIdRubro(asistenciaMedDomi.getIdRubro());
        asistenciaMedDomiDb.setIdCiudad(asistenciaMedDomi.getIdCiudad());
        asistenciaMedDomiDb.setEdad(asistenciaMedDomi.getEdad());
        asistenciaMedDomiDb.setMotivoLlamada(convertCharset(asistenciaMedDomi.getMotivoLlamada()));
        asistenciaMedDomiDb.setProducto(asistenciaMedDomi.getProducto());
        asistenciaMedDomiDb.setServicio(convertCharset(asistenciaMedDomi.getServicio()));
        asistenciaMedDomiDb.setDesenlace(convertCharset(asistenciaMedDomi.getDesenlace()));
        if (asistenciaMedDomiDb.getFechaContacto()!=null && asistenciaMedDomiDb.getFechaContacto().trim().length() > 0) {
            asistenciaMedDomiDb.setFechaContacto(stringToDate(asistenciaMedDomi.getFechaContacto()));
        }else{
            asistenciaMedDomiDb.setFechaContacto(getCurrentDate());
        }
        asistenciaMedDomiDb.setFechaArribo(stringToDate(asistenciaMedDomi.getFechaArribo()));
        asistenciaMedDomiDb.setIncidencia(asistenciaMedDomi.getIncidencia());
        asistenciaMedDomiDb.setUnidadAsignada(convertCharset(asistenciaMedDomi.getUnidadAsignada()));
        asistenciaMedDomiDb.setPaciente(convertCharset(asistenciaMedDomi.getPaciente()));
        asistenciaMedDomiDb.setTelefono(asistenciaMedDomi.getTelefono());
        asistenciaMedDomiDb.setTelefonoAlternativo(asistenciaMedDomi.getTelefonoAlternativo());
        asistenciaMedDomiDb.setDireccionAtencion(asistenciaMedDomi.getDireccionAtencion());
        asistenciaMedDomiDb.setAclaraciones(asistenciaMedDomi.getAclaraciones());
        asistenciaMedDomiDb.setNombreArchivo(nombreArchivo);
        return asistenciaMedDomiDb;
    }
    public static AsistenciaMedDomiAmbuDb convertAsistenciaMedDomiAmbuDb(AsistenciaMedDomiAmbu asistenciaMedDomiAmbu, String nombreArchivo) {
        AsistenciaMedDomiAmbuDb asistenciaMedDomiAmbuDb = new AsistenciaMedDomiAmbuDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        asistenciaMedDomiAmbu.setUniqueId(uniqueId);
        asistenciaMedDomiAmbuDb.setAsisMedDoAmbuId(uniqueId);
        asistenciaMedDomiAmbuDb.setIdProceso(asistenciaMedDomiAmbu.getIdProceso());
        asistenciaMedDomiAmbuDb.setIdRubro(asistenciaMedDomiAmbu.getIdRubro());
        asistenciaMedDomiAmbuDb.setIdCiudad(asistenciaMedDomiAmbu.getIdCiudad());
        asistenciaMedDomiAmbuDb.setEdad(asistenciaMedDomiAmbu.getEdad());
        asistenciaMedDomiAmbuDb.setMotivoLlamada(convertCharset(asistenciaMedDomiAmbu.getMotivoLlamada()));
        asistenciaMedDomiAmbuDb.setProducto(asistenciaMedDomiAmbu.getProducto());
        asistenciaMedDomiAmbuDb.setServicio(convertCharset(asistenciaMedDomiAmbu.getServicio()));
        asistenciaMedDomiAmbuDb.setDesenlace(convertCharset(asistenciaMedDomiAmbu.getDesenlace()));
        if (asistenciaMedDomiAmbuDb.getFechaContacto()!=null && asistenciaMedDomiAmbuDb.getFechaContacto().trim().length() > 0) {
            asistenciaMedDomiAmbuDb.setFechaContacto(stringToDate(asistenciaMedDomiAmbu.getFechaContacto()));
        }else{
            asistenciaMedDomiAmbuDb.setFechaContacto(getCurrentDate());
        }
        asistenciaMedDomiAmbuDb.setFechaArribo(stringToDate(asistenciaMedDomiAmbu.getFechaArribo()));
        asistenciaMedDomiAmbuDb.setIncidencia(asistenciaMedDomiAmbu.getIncidencia());
        asistenciaMedDomiAmbuDb.setUnidadAsignada(convertCharset(asistenciaMedDomiAmbu.getUnidadAsignada()));
        asistenciaMedDomiAmbuDb.setPaciente(convertCharset(asistenciaMedDomiAmbu.getPaciente()));
        asistenciaMedDomiAmbuDb.setTelefono(asistenciaMedDomiAmbu.getTelefono());
        asistenciaMedDomiAmbuDb.setTelefonoAlternativo(asistenciaMedDomiAmbu.getTelefonoAlternativo());
        asistenciaMedDomiAmbuDb.setDireccionAtencion(asistenciaMedDomiAmbu.getDireccionAtencion());
        asistenciaMedDomiAmbuDb.setAclaraciones(asistenciaMedDomiAmbu.getAclaraciones());
        asistenciaMedDomiAmbuDb.setNombreArchivo(nombreArchivo);
        return asistenciaMedDomiAmbuDb;
    }

    public static OrientacionMedTelefDb convertOrientacionMedTelefDb(OrientacionMedTelef orientacionMedTelef, String nombreArchivo) {
        OrientacionMedTelefDb orientacionMedTelefDb = new OrientacionMedTelefDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        orientacionMedTelef.setUniqueId(uniqueId);
        orientacionMedTelefDb.setOrienMedTelId(uniqueId);
        orientacionMedTelefDb.setIdProceso(orientacionMedTelef.getIdProceso());
        orientacionMedTelefDb.setIdRubro(orientacionMedTelef.getIdRubro());
        orientacionMedTelefDb.setIdCiudad(orientacionMedTelef.getIdCiudad());
        orientacionMedTelefDb.setEdad(orientacionMedTelef.getEdad());
        orientacionMedTelefDb.setMotivoLlamada(convertCharset(orientacionMedTelef.getMotivoLlamada()));
        orientacionMedTelefDb.setContactoCovid(orientacionMedTelef.getContactoCovid());
        orientacionMedTelefDb.setDiagnosticoPresuntivo(convertCharset(orientacionMedTelef.getDiagnosticoPresuntivo()));
        orientacionMedTelefDb.setConducta(convertCharset(orientacionMedTelef.getConducta()));
        if (orientacionMedTelefDb.getFechaContacto()!=null && orientacionMedTelefDb.getFechaContacto().trim().length() > 0) {
            orientacionMedTelefDb.setFechaContacto(stringToDate(orientacionMedTelef.getFechaContacto()));
        }else{
            orientacionMedTelefDb.setFechaContacto(getCurrentDate());
        }
        orientacionMedTelefDb.setMedico(convertCharset(orientacionMedTelef.getMedico()));
        orientacionMedTelefDb.setPaciente(orientacionMedTelef.getPaciente());
        orientacionMedTelefDb.setTelefono(orientacionMedTelef.getTelefono());
        orientacionMedTelefDb.setTelefonoAlternativo(orientacionMedTelef.getTelefonoAlternativo());
        orientacionMedTelefDb.setEmail(orientacionMedTelef.getEmail());
        orientacionMedTelefDb.setObservaciones(convertCharset(orientacionMedTelef.getObservaciones()));
        orientacionMedTelefDb.setAclaraciones(convertCharset(orientacionMedTelef.getAclaraciones()));
        orientacionMedTelefDb.setNombreArchivo(nombreArchivo);
        return orientacionMedTelefDb;
    }

    public static AtenAsisProdemVidaPlusDb convertAtenAsisProdemVidaPlusDb(AtenAsisProdemVidaPlus atenAsisProdemVidaPlus, String nombreArchivo) {
        AtenAsisProdemVidaPlusDb atenAsisProdemVidaPlusDb = new AtenAsisProdemVidaPlusDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        atenAsisProdemVidaPlus.setUniqueId(uniqueId);
        atenAsisProdemVidaPlusDb.setAtenAsisProId(uniqueId);
        atenAsisProdemVidaPlusDb.setIdProceso(atenAsisProdemVidaPlus.getIdProceso());
        atenAsisProdemVidaPlusDb.setProveedor(atenAsisProdemVidaPlus.getProveedor());
        atenAsisProdemVidaPlusDb.setIdRubro(atenAsisProdemVidaPlus.getIdRubro());
        atenAsisProdemVidaPlusDb.setIdCiudad(atenAsisProdemVidaPlus.getIdCiudad());
        atenAsisProdemVidaPlusDb.setIdOperador(convertCharset(atenAsisProdemVidaPlus.getIdOperador()));
        atenAsisProdemVidaPlusDb.setServicio(convertCharset(atenAsisProdemVidaPlus.getServicio()));
        if (atenAsisProdemVidaPlusDb.getFechaContacto()!=null && atenAsisProdemVidaPlusDb.getFechaContacto().trim().length() > 0) {
            atenAsisProdemVidaPlusDb.setFechaContacto(stringToDate(atenAsisProdemVidaPlus.getFechaContacto()));
        }else{
            atenAsisProdemVidaPlusDb.setFechaContacto(getCurrentDate());
        }
        atenAsisProdemVidaPlusDb.setNombreTitular(convertCharset(atenAsisProdemVidaPlus.getNombreTitular()));
        atenAsisProdemVidaPlusDb.setTelefono(atenAsisProdemVidaPlus.getTelefono());
        atenAsisProdemVidaPlusDb.setIdDocumento(convertCharset(atenAsisProdemVidaPlus.getIdDocumento()));
        atenAsisProdemVidaPlusDb.setEspecialidad(convertCharset(atenAsisProdemVidaPlus.getEspecialidad()));
        atenAsisProdemVidaPlusDb.setNombreArchivo(nombreArchivo);
        return atenAsisProdemVidaPlusDb;
    }

    public static VagonetaSeguraDb convertVagonetaSegura(VagonetaSegura vagonetaSegura,String nombreArchivo) {
        VagonetaSeguraDb vagonetaSeguraDb = new VagonetaSeguraDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        vagonetaSegura.setUniqueId(uniqueId);
        vagonetaSeguraDb.setVagonetaSeguraId(uniqueId);
        vagonetaSeguraDb.setIdProceso(vagonetaSegura.getIdProceso());
        vagonetaSeguraDb.setIdRubro(vagonetaSegura.getIdRubro());
        vagonetaSeguraDb.setIdCiudad(vagonetaSegura.getIdCiudad());
        vagonetaSeguraDb.setIdOperador(convertCharset(vagonetaSegura.getIdOperador()));
        vagonetaSeguraDb.setServicio(convertCharset(vagonetaSegura.getServicio()));
        if (vagonetaSeguraDb.getFechaContacto()!=null && vagonetaSeguraDb.getFechaContacto().trim().length() > 0) {
            vagonetaSeguraDb.setFechaContacto(stringToDate(vagonetaSegura.getFechaContacto()));
        }else{
            vagonetaSeguraDb.setFechaContacto(getCurrentDate());
        }
        vagonetaSeguraDb.setFechaServicio(stringToDate(vagonetaSegura.getFechaServicio()));
        vagonetaSeguraDb.setNombreTitular(convertCharset(vagonetaSegura.getNombreTitular()));
        vagonetaSeguraDb.setTelefono(vagonetaSegura.getTelefono());
        vagonetaSeguraDb.setTelefonoAlternativo(vagonetaSegura.getTelefonoAlternativo());
        vagonetaSeguraDb.setPlaca(vagonetaSegura.getPlaca());
        vagonetaSeguraDb.setNombreArchivo(nombreArchivo);
        return vagonetaSeguraDb;
    }

    public static SegurosMasivoDb convertSegurosMasivo(SegurosMasivo segurosMasivo,String nombreArchivo) {
        SegurosMasivoDb segurosMasivoDb = new SegurosMasivoDb();
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        segurosMasivo.setUniqueId(uniqueId);
        segurosMasivoDb.setSegurosMasivoId(uniqueId);
        segurosMasivoDb.setIdProceso(segurosMasivo.getIdProceso());
        segurosMasivoDb.setIdRubro(segurosMasivo.getIdRubro());
        segurosMasivoDb.setSponsor(convertCharset(segurosMasivo.getSponsor()));
        segurosMasivoDb.setIdCiudad(segurosMasivo.getIdCiudad());
        segurosMasivoDb.setProducto(convertCharset(segurosMasivo.getProducto()));
        segurosMasivoDb.setProveedorDeAsistencia(convertCharset(segurosMasivo.getProveedorDeAsistencia()));
        segurosMasivoDb.setServicio(convertCharset(segurosMasivo.getServicio()));
        if (segurosMasivoDb.getFechaContacto()!=null && segurosMasivoDb.getFechaContacto().trim().length() > 0) {
            segurosMasivoDb.setFechaContacto(stringToDate(segurosMasivo.getFechaContacto()));
        }else{
            segurosMasivoDb.setFechaContacto(getCurrentDate());
        }
        segurosMasivoDb.setFechaUsoServicio(stringToDate(segurosMasivo.getFechaUsoServicio()));
        segurosMasivoDb.setIdDocumento(convertCharset(segurosMasivo.getIdDocumento()));
        segurosMasivoDb.setNombreSolicitante(convertCharset(segurosMasivo.getNombreSolicitante()));
        segurosMasivoDb.setTelefono(segurosMasivo.getTelefono());
        segurosMasivoDb.setEmail(segurosMasivo.getEmail());
        segurosMasivoDb.setNombreArchivo(nombreArchivo);
        return segurosMasivoDb;
    }
    public static SegurosMasivo convertSegurosMasivoReminder(SegurosMasivoDb segurosMasivoDb) {
        SegurosMasivo segurosMasivo = new SegurosMasivo();
        segurosMasivo.setUniqueId(segurosMasivoDb.getSegurosMasivoId());
        segurosMasivo.setIdProceso(segurosMasivoDb.getIdProceso());
        segurosMasivo.setIdRubro(segurosMasivoDb.getIdRubro());
        segurosMasivo.setSponsor(convertCharset(segurosMasivoDb.getSponsor()));
        segurosMasivo.setIdCiudad(segurosMasivoDb.getIdCiudad());
        segurosMasivo.setProducto(convertCharset(segurosMasivoDb.getProducto()));
        segurosMasivo.setProveedorDeAsistencia(convertCharset(segurosMasivoDb.getProveedorDeAsistencia()));
        segurosMasivo.setServicio(convertCharset(segurosMasivoDb.getServicio()));
        segurosMasivo.setFechaContacto(segurosMasivoDb.getFechaContacto());
        segurosMasivo.setFechaUsoServicio(stringToDate(segurosMasivoDb.getFechaUsoServicio()));
        segurosMasivo.setIdDocumento(convertCharset(segurosMasivoDb.getIdDocumento()));
        segurosMasivo.setNombreSolicitante(convertCharset(segurosMasivoDb.getNombreSolicitante()));
        segurosMasivo.setTelefono(segurosMasivoDb.getTelefono());
        segurosMasivo.setEmail(segurosMasivoDb.getEmail());
        return segurosMasivo;
    }
    public static String convertirFecha(String fechaOriginal) {
        System.out.println("fecha: " + fechaOriginal);
        if (fechaOriginal != null && fechaOriginal.trim().length() > 0) {
            DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime fecha = LocalDateTime.parse(fechaOriginal, formatoEntrada);
            return fecha.format(formatoSalida);
        } else {
            return null;
        }
    }

    public static String toStringDateNoSigns(String fechaString) {
        String fechaMySQL = stringToDate(fechaString);
        if (fechaMySQL != null) {
            fechaMySQL = fechaMySQL.replace("-", "").replace(" ", "").replace(":", "");
        }
        return fechaMySQL;
    }
    public static String stringToDate(String fechaString) {
        if (fechaString == null || fechaString.isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy[ H:m[:s]]");
            LocalDateTime fecha = LocalDateTime.parse(fechaString, formatter);
            String fechaMySQL = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            return fechaMySQL;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static TicketRequest jiraTicketContent(AsistenciaVial asistenciaVial) {
        TicketRequest ticketRequest = new TicketRequest();
        RequestFieldValues requestFieldValues = new RequestFieldValues();
        requestFieldValues.setSummary(
                asistenciaVial.getIdRubro() + " " + asistenciaVial.getPlaca() + " " + asistenciaVial.getIdCiudad());
        requestFieldValues.setDescription(asistenciaVial.toString());
        ticketRequest.setRequestFieldValues(requestFieldValues);
        ticketRequest.setRequestTypeId(Constant.REQUEST_TYPE_ID);
        ticketRequest.setServiceDeskId(Constant.SERVICE_DESK_ID);
        return ticketRequest;
    }

    public static boolean containsSpecialCharacters(String input) {
        String specialCharacters = "áéíóúñÁÉÍÓÚÑº";
        for (int i = 0; i < input.length(); i++) {
            if (specialCharacters.contains(String.valueOf(input.charAt(i)))) {
                return true;
            }
        }
        return false;
    }
    public static String convertCharset(String input) {
        if (input != null && !containsSpecialCharacters(input)) {
            return new String(input.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
        } else {
            return input;
        }

    }

    public static void waitMilliSeconds(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String getCurrentDateTimeString() {
        // Obtener la fecha y hora actual con el desplazamiento UTC
        OffsetDateTime currentDateTime = OffsetDateTime.now(ZoneOffset.UTC);

        // Formatear la fecha y hora en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        System.out.println("FECHA HORA DE ENVIO: " + currentDateTime.format(formatter).replace("Z", "+00:00"));
        return currentDateTime.format(formatter).replace("Z", "+00:00");
    }

    public static String emailAlias(String email, String alfanumerico) {
        int indiceArroba = email.indexOf('@');
        if (indiceArroba != -1) {
            String parteAntesDeArroba = email.substring(0, indiceArroba);
            String parteDespuesDeArroba = email.substring(indiceArroba);
            return parteAntesDeArroba.trim() + "+" + alfanumerico + parteDespuesDeArroba;
        } else {
            return "";
        }
    }

    public static String getCurrentDateTimeFormatted() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }

    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }

    public static String replaceAccents(String input) {
        if (input == null) {
            return null;
        }
        input=input.replace("%", "%25");
        input=input.replace("º", "%B0");
        return input
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replace('ñ', 'n')
                .replace('Á', 'A')
                .replace('É', 'E')
                .replace('Í', 'I')
                .replace('Ó', 'O')
                .replace('Ú', 'U')
                .replace('Ñ', 'N');
    }

    public static String replaceSpaces(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll(" ", "%20");
    }

    public static String replaceURLParameters(String url, Map<String, String> replacements) {
        if (url == null || replacements == null || replacements.isEmpty()) {
            return url;
        }

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String key = "[" + entry.getKey() + "]";
            String value = entry.getValue();
            String keyWithBrackets = "[" + entry.getKey() + "]";
            url = url.replace(keyWithBrackets, value);
        }

        return url;
    }

    public static String valueOrNA(String valor){
         String result="NA";
         if (valor!=null&&valor.trim().length()>0){
            result=replaceSpaces(replaceAccents(convertCharset(valor)));
         }else{
            return result;
         }
         return result;
    }
        public static String stringJson(List<String> list) {
        String result = "{}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            result = "{}";
        }
        return result;
    }

        public static ContactLog fromContactToContactLog(Contact contact, String serviceComplain,String fileName) {
        ContactLog contactLog = new ContactLog();
        try {
            contactLog.setFirstName(contact.getFirstName());
            contactLog.setLastName(contact.getLastName());
            contactLog.setEmail(contact.getEmail());
            contactLog.setCustomField1(contact.getCustomFields().getField1());
            contactLog.setCustomField2(contact.getCustomFields().getField2());
            contactLog.setCustomField3(contact.getCustomFields().getField3());
            contactLog.setCustomField4(contact.getCustomFields().getField4());
            contactLog.setCustomField5(contact.getCustomFields().getField5());
            contactLog.setCustomField6(contact.getCustomFields().getField6());
            contactLog.setCustomField7(contact.getCustomFields().getField7());
            contactLog.setCustomField8(contact.getCustomFields().getField8());
            contactLog.setCustomField9(contact.getCustomFields().getField9());
            contactLog.setCustomField10(contact.getCustomFields().getField10());
            contactLog.setCustomField11(contact.getCustomFields().getField11());
            contactLog.setCustomField12(contact.getCustomFields().getField12());
            contactLog.setCustomField13(contact.getCustomFields().getField13());
            contactLog.setCustomField14(contact.getCustomFields().getField14());
            contactLog.setCustomField15(contact.getCustomFields().getField15());
            contactLog.setCustomField16(contact.getCustomFields().getField16());
            contactLog.setCustomField17(contact.getCustomFields().getField17());
            contactLog.setCustomField18(contact.getCustomFields().getField18());
            contactLog.setCustomField19(contact.getCustomFields().getField19());
            contactLog.setCustomField20(contact.getCustomFields().getField20());
            contactLog.setCustomField21(contact.getCustomFields().getField21());
            contactLog.setCustomField22(contact.getCustomFields().getField22());
            contactLog.setCustomField23(contact.getCustomFields().getField23());
            contactLog.setCustomField24(contact.getCustomFields().getField24());
            contactLog.setCustomField25(contact.getCustomFields().getField25());
            contactLog.setCustomField26(contact.getCustomFields().getField26());
            contactLog.setCustomField27(contact.getCustomFields().getField27());
            contactLog.setCustomField28(contact.getCustomFields().getField28());
            contactLog.setCustomField29(contact.getCustomFields().getField29());
            contactLog.setCustomField30(contact.getCustomFields().getField30());
            contactLog.setCustomField31(contact.getCustomFields().getField31());
            contactLog.setCustomField32(contact.getCustomFields().getField32());
            contactLog.setCustomField33(contact.getCustomFields().getField33());
            contactLog.setCustomField34(contact.getCustomFields().getField34());
            contactLog.setCustomField35(contact.getCustomFields().getField35());
            contactLog.setCustomField36(contact.getCustomFields().getField36());
            contactLog.setCustomField37(contact.getCustomFields().getField37());
            contactLog.setCustomField38(contact.getCustomFields().getField38());
            contactLog.setCustomField39(contact.getCustomFields().getField39());
            contactLog.setCustomField40(contact.getCustomFields().getField40());   
            contactLog.setCustomField41(contact.getCustomFields().getField41());
            contactLog.setCustomField42(contact.getCustomFields().getField42());
            contactLog.setCustomField43(contact.getCustomFields().getField43());
            contactLog.setCustomField44(contact.getCustomFields().getField44());
            contactLog.setCustomField45(contact.getCustomFields().getField45());
            contactLog.setCustomField46(contact.getCustomFields().getField46());
            contactLog.setCustomField47(contact.getCustomFields().getField47());
            contactLog.setCustomField48(contact.getCustomFields().getField48());
            contactLog.setCustomField49(contact.getCustomFields().getField49());
            contactLog.setCustomField50(contact.getCustomFields().getField50());                      
            contactLog.setContactStatus(contact.getStatus());
            contactLog.setContactId(contact.getId());
            contactLog.setContactHref(contact.getHref());
            contactLog.setServiceComplain(serviceComplain);
            contactLog.setStatus(Constant.ACTIVE_STATUS);
            contactLog.setNombreArchivo(fileName);
            contactLog.setUniqueId(contact.getUniqueId());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return contactLog;
    }
    public static ContactLog fromContactToInvalidRepetedContactLog(Contact contact, String serviceComplain,String fileName,String emailStatus) {
        ContactLog contactLog = new ContactLog();
        try {
            contactLog.setFirstName(contact.getFirstName());
            contactLog.setLastName(contact.getLastName());
            contactLog.setEmail(contact.getEmail());
            contactLog.setCustomField1(contact.getCustomFields().getField1());
            contactLog.setCustomField2(contact.getCustomFields().getField2());
            contactLog.setCustomField3(contact.getCustomFields().getField3());
            contactLog.setCustomField4(contact.getCustomFields().getField4());
            contactLog.setCustomField5(contact.getCustomFields().getField5());
            contactLog.setCustomField6(contact.getCustomFields().getField6());
            contactLog.setCustomField7(contact.getCustomFields().getField7());
            contactLog.setCustomField8(contact.getCustomFields().getField8());
            contactLog.setCustomField9(contact.getCustomFields().getField9());
            contactLog.setCustomField10(contact.getCustomFields().getField10());
            contactLog.setCustomField11(contact.getCustomFields().getField11());
            contactLog.setCustomField12(contact.getCustomFields().getField12());
            contactLog.setCustomField13(contact.getCustomFields().getField13());
            contactLog.setCustomField14(contact.getCustomFields().getField14());
            contactLog.setCustomField15(contact.getCustomFields().getField15());
            contactLog.setCustomField16(contact.getCustomFields().getField16());
            contactLog.setCustomField17(contact.getCustomFields().getField17());
            contactLog.setCustomField18(contact.getCustomFields().getField18());
            contactLog.setCustomField19(contact.getCustomFields().getField19());
            contactLog.setCustomField20(contact.getCustomFields().getField20());
            contactLog.setCustomField21(contact.getCustomFields().getField21());
            contactLog.setCustomField22(contact.getCustomFields().getField22());
            contactLog.setCustomField23(contact.getCustomFields().getField23());
            contactLog.setCustomField24(contact.getCustomFields().getField24());
            contactLog.setCustomField25(contact.getCustomFields().getField25());
            contactLog.setCustomField26(contact.getCustomFields().getField26());
            contactLog.setCustomField27(contact.getCustomFields().getField27());
            contactLog.setCustomField28(contact.getCustomFields().getField28());
            contactLog.setCustomField29(contact.getCustomFields().getField29());
            contactLog.setCustomField30(contact.getCustomFields().getField30());
            contactLog.setCustomField31(contact.getCustomFields().getField31());
            contactLog.setCustomField32(contact.getCustomFields().getField32());
            contactLog.setCustomField33(contact.getCustomFields().getField33());
            contactLog.setCustomField34(contact.getCustomFields().getField34());
            contactLog.setCustomField35(contact.getCustomFields().getField35());
            contactLog.setCustomField36(contact.getCustomFields().getField36());
            contactLog.setCustomField37(contact.getCustomFields().getField37());
            contactLog.setCustomField38(contact.getCustomFields().getField38());
            contactLog.setCustomField39(contact.getCustomFields().getField39());
            contactLog.setCustomField40(contact.getCustomFields().getField40());
            contactLog.setCustomField41(contact.getCustomFields().getField41());
            contactLog.setCustomField42(contact.getCustomFields().getField42());
            contactLog.setCustomField43(contact.getCustomFields().getField43());
            contactLog.setCustomField44(contact.getCustomFields().getField44());
            contactLog.setCustomField45(contact.getCustomFields().getField45());
            contactLog.setCustomField46(contact.getCustomFields().getField46());
            contactLog.setCustomField47(contact.getCustomFields().getField47());
            contactLog.setCustomField48(contact.getCustomFields().getField48());
            contactLog.setCustomField49(contact.getCustomFields().getField49());
            contactLog.setCustomField50(contact.getCustomFields().getField50());
            contactLog.setContactStatus(contact.getStatus());
            contactLog.setContactId(contact.getId());
            contactLog.setContactHref(contact.getHref());
            contactLog.setServiceComplain(serviceComplain);
            contactLog.setStatus(Constant.ACTIVE_STATUS);
            contactLog.setNombreArchivo(fileName);
            contactLog.setUniqueId(contact.getUniqueId());
            contactLog.setEmailStatus(emailStatus);
        } catch (Exception ex) {
            System.out.println("fromContactToInvalidRepetedContactLog Error: " + ex.getMessage());
        }
        return contactLog;
    }
    public static ErrorLog createErrorLog(String errorCode,String errorDescription,String remarks, String errorDetail ){
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorCode(errorCode);
        errorLog.setErrorDescription(errorDescription);
        errorLog.setRemarks(remarks);
        errorLog.setErrorDetail(errorDetail);
        return errorLog;
    }

    public static JiraLog createJiraLog(String issueId,String issueKey,String requestTypeId, String serviceDeskId,String serviceComplain,String fileName,String uniqueId ){
        JiraLog jiraLog = new JiraLog();
        jiraLog.setIssueId(issueId);
        jiraLog.setIssueKey(issueKey);
        jiraLog.setRequestTypeId(requestTypeId);
        jiraLog.setServiceDeskId(serviceDeskId);
        jiraLog.setServiceComplain(serviceComplain);
        jiraLog.setNombreArchivo(fileName);
        jiraLog.setUniqueId(uniqueId);
        return jiraLog;
    }

    public static String getCodigoLink(String code,String fechaContacto,String telefono) {
        String codigo = "NA";
        String fechaContact=Utils.toStringDateNoSigns(fechaContacto)!=null?Utils.toStringDateNoSigns(fechaContacto):getCurrentDateTimeFormatted();
        codigo = code+"."+fechaContact+"."+telefono;
        return codigo;
    }
}
