package com.keysolutions.nacionalservice.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keysolutions.nacionalservice.model.*;
import com.keysolutions.nacionalservice.model.jira.RequestFieldValues;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.model.log.*;
import com.keysolutions.nacionalservice.model.survey.Contact;

public class Utils {
    public static AsistenciaVialDb convertAsistenciaVialDb(AsistenciaVial asistenciaVial, String nombreArchivo) {
        AsistenciaVialDb asistenciaVialDb = new AsistenciaVialDb();
        asistenciaVialDb.setIdProceso(asistenciaVial.getIdProceso());
        asistenciaVialDb.setIdRubro(asistenciaVial.getIdRubro());
        asistenciaVialDb.setIdCiudad(asistenciaVial.getIdCiudad());
        asistenciaVialDb.setIdOperador(asistenciaVial.getIdOperador());
        asistenciaVialDb.setIdSiniestro(asistenciaVial.getIdSiniestro());
        asistenciaVialDb.setFechaContacto(stringToDate(asistenciaVial.getFechaContacto()));
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
        nsvs800Db.setIdProceso(nsvs800.getIdProceso());
        nsvs800Db.setIdRubro(nsvs800.getIdRubro());
        nsvs800Db.setIdCiudad(nsvs800.getIdCiudad());
        nsvs800Db.setIdOperador(convertCharset(nsvs800.getIdOperador()));
        nsvs800Db.setServicio(convertCharset(nsvs800.getServicio()));
        nsvs800Db.setFechaContacto(stringToDate(nsvs800.getFechaContacto()));
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
        consultasReclamosDb.setIdProceso(consultasReclamos.getIdProceso());
        consultasReclamosDb.setIdRubro(consultasReclamos.getIdRubro());
        consultasReclamosDb.setIdCiudad(consultasReclamos.getIdCiudad());
        consultasReclamosDb.setIdOperador(convertCharset(consultasReclamos.getIdOperador()));
        consultasReclamosDb.setServicio(convertCharset(consultasReclamos.getServicio()));
        consultasReclamosDb.setFechaContacto(stringToDate(consultasReclamos.getFechaContacto()));
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

    public static AtencionInicialDb convertAtencionInicialDb(AtencionInicial atencionInicial, String nombreArchivo) {
        AtencionInicialDb atencionInicialDb = new AtencionInicialDb();
        atencionInicialDb.setIdProceso(atencionInicial.getIdProceso());
        atencionInicialDb.setIdRubro(atencionInicial.getIdRubro());
        atencionInicialDb.setIdCiudad(atencionInicial.getIdCiudad());
        atencionInicialDb.setIdOperador(convertCharset(atencionInicial.getIdOperador()));
        atencionInicialDb.setIdEjecutivo(convertCharset(atencionInicial.getIdEjecutivo()));
        atencionInicialDb.setFechaContacto(stringToDate(atencionInicial.getFechaContacto()));
        atencionInicialDb.setPlaca(atencionInicial.getPlaca());
        atencionInicialDb.setNombreTitular(convertCharset(atencionInicial.getNombreTitular()));
        atencionInicialDb.setNombreSolicitante(atencionInicial.getNombreSolicitante());
        atencionInicialDb.setTelefono(atencionInicial.getTelefono());
        atencionInicialDb.setEmail(atencionInicial.getEmail());
        atencionInicialDb.setIdPoliza(atencionInicial.getIdPoliza());
        atencionInicialDb.setNombreArchivo(nombreArchivo);
        return atencionInicialDb;
    }

    public static AccidentesPersonalesDb convertAccidentePersonalDB(AccidentesPersonales accidentesPersonales,
            String nombreArchivo) {
        AccidentesPersonalesDb accidentesPersonalesDb = new AccidentesPersonalesDb();
        accidentesPersonalesDb.setIdProceso(accidentesPersonales.getIdProceso());
        accidentesPersonalesDb.setIdRubro(accidentesPersonales.getIdRubro());
        accidentesPersonalesDb.setIdCiudad(accidentesPersonales.getIdCiudad());
        accidentesPersonalesDb.setIdOperador(convertCharset(accidentesPersonales.getIdOperador()));
        accidentesPersonalesDb.setServicio(convertCharset(accidentesPersonales.getServicio()));
        accidentesPersonalesDb.setFechaContacto(stringToDate(accidentesPersonales.getFechaContacto()));
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
        callCenterNspfDb.setIdProceso(callCenterNspf.getIdProceso());
        callCenterNspfDb.setIdRubro(callCenterNspf.getIdRubro());
        callCenterNspfDb.setIdCiudad(callCenterNspf.getIdCiudad());
        callCenterNspfDb.setIdSiniestro(convertCharset(callCenterNspf.getIdSiniestro()));
        callCenterNspfDb.setIdOperador(convertCharset(callCenterNspf.getIdOperador()));
        callCenterNspfDb.setFechaContacto(stringToDate(callCenterNspf.getFechaContacto()));
        callCenterNspfDb.setIdCliente(callCenterNspf.getIdCliente());
        callCenterNspfDb.setNombreArchivo(nombreArchivo);
        return callCenterNspfDb;
    }
    public static InmedicalAtuMedidaDb convertInmedicalAtuMedidaDb(InmedicalAtuMedida inmedicalAtuMedida, String nombreArchivo) {
        InmedicalAtuMedidaDb inmedicalAtuMedidaDb = new InmedicalAtuMedidaDb();
        inmedicalAtuMedidaDb.setIdProceso(inmedicalAtuMedida.getIdProceso());
        inmedicalAtuMedidaDb.setIdRubro(inmedicalAtuMedida.getIdRubro());
        inmedicalAtuMedidaDb.setIdCiudad(inmedicalAtuMedida.getIdCiudad());
        inmedicalAtuMedidaDb.setProveedor(convertCharset(inmedicalAtuMedida.getProveedor()));
        inmedicalAtuMedidaDb.setIdCentro(convertCharset(inmedicalAtuMedida.getIdCentro()));
        inmedicalAtuMedidaDb.setServicio(convertCharset(inmedicalAtuMedida.getServicio()));
        inmedicalAtuMedidaDb.setFechaContacto(stringToDate(inmedicalAtuMedida.getFechaContacto()));
        inmedicalAtuMedidaDb.setIdDocumento(inmedicalAtuMedida.getIdDocumento());
        inmedicalAtuMedidaDb.setNombreTitular(convertCharset(inmedicalAtuMedida.getNombreTitular()));
        inmedicalAtuMedidaDb.setNombreSolicitante(convertCharset(inmedicalAtuMedida.getNombreSolicitante()));
        inmedicalAtuMedidaDb.setTelefono(inmedicalAtuMedida.getTelefono());
        inmedicalAtuMedidaDb.setEmail(inmedicalAtuMedida.getEmail());
        inmedicalAtuMedidaDb.setNombreArchivo(nombreArchivo);
        return inmedicalAtuMedidaDb;
    }

    public static InmedicalBancoGanaderoDb convertInmedicalBancoGanaderoDb(InmedicalBancoGanadero inmedicalBancoGanadero, String nombreArchivo) {
        InmedicalBancoGanaderoDb inmedicalBancoGanaderoDb = new InmedicalBancoGanaderoDb();
        inmedicalBancoGanaderoDb.setIdProceso(inmedicalBancoGanadero.getIdProceso());
        inmedicalBancoGanaderoDb.setIdRubro(inmedicalBancoGanadero.getIdRubro());
        inmedicalBancoGanaderoDb.setIdCiudad(inmedicalBancoGanadero.getIdCiudad());
        inmedicalBancoGanaderoDb.setProveedor(convertCharset(inmedicalBancoGanadero.getProveedor()));
        inmedicalBancoGanaderoDb.setIdCentro(convertCharset(inmedicalBancoGanadero.getIdCentro()));
        inmedicalBancoGanaderoDb.setServicio(convertCharset(inmedicalBancoGanadero.getServicio()));
        inmedicalBancoGanaderoDb.setFechaContacto(stringToDate(inmedicalBancoGanadero.getFechaContacto()));
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
        tallersE2eDb.setIdProceso(tallersE2e.getIdProceso());
        tallersE2eDb.setIdRubro(tallersE2e.getIdRubro());
        tallersE2eDb.setIdCiudad(tallersE2e.getIdCiudad());
        tallersE2eDb.setIdOperador(convertCharset(tallersE2e.getIdOperador()));
        tallersE2eDb.setServicio(convertCharset(tallersE2e.getServicio()));
        tallersE2eDb.setFechaContacto(stringToDate(tallersE2e.getFechaContacto()));
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

    public static CentroRehaOdontoDb convertCentroRehaOdontoDb(CentroRehaOdonto centroRehaOdonto, String nombreArchivo) {
        CentroRehaOdontoDb centroRehaOdontoDb = new CentroRehaOdontoDb();
        centroRehaOdontoDb.setIdProceso(centroRehaOdonto.getIdProceso());
        centroRehaOdontoDb.setIdRubro(centroRehaOdonto.getIdRubro());
        centroRehaOdontoDb.setIdCiudad(centroRehaOdonto.getIdCiudad());
        centroRehaOdontoDb.setIdOperador(convertCharset(centroRehaOdonto.getIdOperador()));
        centroRehaOdontoDb.setCentroMedico(convertCharset(centroRehaOdonto.getCentroMedico()));
        centroRehaOdontoDb.setFechaContacto(stringToDate(centroRehaOdonto.getFechaContacto()));
        centroRehaOdontoDb.setFechaSalida(stringToDate(centroRehaOdonto.getFechaSalida()));
        centroRehaOdontoDb.setPaciente(convertCharset(centroRehaOdonto.getPaciente()));
        centroRehaOdontoDb.setPlan(convertCharset(centroRehaOdonto.getPlan()));
        centroRehaOdontoDb.setTelefono(centroRehaOdonto.getTelefono());
        centroRehaOdontoDb.setEmail(centroRehaOdonto.getEmail());
        centroRehaOdontoDb.setTelefonoAdicional(centroRehaOdonto.getTelefonoAdicional());
        centroRehaOdontoDb.setNombreArchivo(nombreArchivo);
        return centroRehaOdontoDb;
    }
    public static ProvServicioMedicoDb convertProvServMedicoDb(ProvServicioMedico provServicioMedico, String nombreArchivo) {
        ProvServicioMedicoDb provServicioMedicoDb = new ProvServicioMedicoDb();
        provServicioMedicoDb.setIdProveedor(provServicioMedico.getIdProveedor());
        provServicioMedicoDb.setIdRubro(provServicioMedico.getIdRubro());
        provServicioMedicoDb.setIdCiudad(provServicioMedico.getIdCiudad());
        provServicioMedicoDb.setProducto(convertCharset(provServicioMedico.getProducto()));
        provServicioMedicoDb.setServicio(convertCharset(provServicioMedico.getServicio()));
        provServicioMedicoDb.setFechaDenuncia(stringToDate(provServicioMedico.getFechaDenuncia()));
        provServicioMedicoDb.setFechaContacto(stringToDate(provServicioMedico.getFechaContacto()));
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
    public static ProveedorMedicoDb convertProveedorMedicoDb(ProveedorMedico proveedorMedico, String nombreArchivo) {
        ProveedorMedicoDb proveedorMedicoDb = new ProveedorMedicoDb();
        proveedorMedicoDb.setIdProveedor(proveedorMedico.getIdProveedor());
        proveedorMedicoDb.setIdRubro(proveedorMedico.getIdRubro());
        proveedorMedicoDb.setIdCiudad(proveedorMedico.getIdCiudad());
        proveedorMedicoDb.setProducto(convertCharset(proveedorMedico.getProducto()));
        proveedorMedicoDb.setServicio(convertCharset(proveedorMedico.getServicio()));
        proveedorMedicoDb.setFechaDenuncia(stringToDate(proveedorMedico.getFechaDenuncia()));
        proveedorMedicoDb.setFechaContacto(stringToDate(proveedorMedico.getFechaContacto()));
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
    public static ProveedorFarmaDb convertProveedorFarmaDb(ProveedorFarma proveedorFarma, String nombreArchivo) {
        ProveedorFarmaDb proveedorFarmaDb = new ProveedorFarmaDb();
        proveedorFarmaDb.setIdProveedor(proveedorFarma.getIdProveedor());
        proveedorFarmaDb.setIdRubro(proveedorFarma.getIdRubro());
        proveedorFarmaDb.setIdCiudad(proveedorFarma.getIdCiudad());
        proveedorFarmaDb.setProducto(convertCharset(proveedorFarma.getProducto()));
        proveedorFarmaDb.setServicio(convertCharset(proveedorFarma.getServicio()));
        proveedorFarmaDb.setFechaDenuncia(stringToDate(proveedorFarma.getFechaDenuncia()));
        proveedorFarmaDb.setFechaContacto(stringToDate(proveedorFarma.getFechaContacto()));
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
    public static AsistenciaMedDomiDb convertAsistenciaMedDomiDb(AsistenciaMedDomi asistenciaMedDomi, String nombreArchivo) {
        AsistenciaMedDomiDb asistenciaMedDomiDb = new AsistenciaMedDomiDb();
        asistenciaMedDomiDb.setIdProceso(asistenciaMedDomi.getIdProceso());
        asistenciaMedDomiDb.setIdRubro(asistenciaMedDomi.getIdRubro());
        asistenciaMedDomiDb.setIdCiudad(asistenciaMedDomi.getIdCiudad());
        asistenciaMedDomiDb.setEdad(asistenciaMedDomi.getEdad());
        asistenciaMedDomiDb.setMotivoLlamada(convertCharset(asistenciaMedDomi.getMotivoLlamada()));
        asistenciaMedDomiDb.setProducto(asistenciaMedDomi.getProducto());
        asistenciaMedDomiDb.setServicio(convertCharset(asistenciaMedDomi.getServicio()));
        asistenciaMedDomiDb.setDesenlace(convertCharset(asistenciaMedDomi.getDesenlace()));
        asistenciaMedDomiDb.setFechaContacto(stringToDate(asistenciaMedDomi.getFechaContacto()));
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
        asistenciaMedDomiAmbuDb.setIdProceso(asistenciaMedDomiAmbu.getIdProceso());
        asistenciaMedDomiAmbuDb.setIdRubro(asistenciaMedDomiAmbu.getIdRubro());
        asistenciaMedDomiAmbuDb.setIdCiudad(asistenciaMedDomiAmbu.getIdCiudad());
        asistenciaMedDomiAmbuDb.setEdad(asistenciaMedDomiAmbu.getEdad());
        asistenciaMedDomiAmbuDb.setMotivoLlamada(convertCharset(asistenciaMedDomiAmbu.getMotivoLlamada()));
        asistenciaMedDomiAmbuDb.setProducto(asistenciaMedDomiAmbu.getProducto());
        asistenciaMedDomiAmbuDb.setServicio(convertCharset(asistenciaMedDomiAmbu.getServicio()));
        asistenciaMedDomiAmbuDb.setDesenlace(convertCharset(asistenciaMedDomiAmbu.getDesenlace()));
        asistenciaMedDomiAmbuDb.setFechaContacto(stringToDate(asistenciaMedDomiAmbu.getFechaContacto()));
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
        orientacionMedTelefDb.setIdProceso(orientacionMedTelef.getIdProceso());
        orientacionMedTelefDb.setIdRubro(orientacionMedTelef.getIdRubro());
        orientacionMedTelefDb.setIdCiudad(orientacionMedTelef.getIdCiudad());
        orientacionMedTelefDb.setEdad(orientacionMedTelef.getEdad());
        orientacionMedTelefDb.setMotivoLlamada(convertCharset(orientacionMedTelef.getMotivoLlamada()));
        orientacionMedTelefDb.setContactoCovid(orientacionMedTelef.getContactoCovid());
        orientacionMedTelefDb.setDiagnosticoPresuntivo(convertCharset(orientacionMedTelef.getDiagnosticoPresuntivo()));
        orientacionMedTelefDb.setConducta(convertCharset(orientacionMedTelef.getConducta()));
        orientacionMedTelefDb.setFechaContacto(stringToDate(orientacionMedTelef.getFechaContacto()));
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
        atenAsisProdemVidaPlusDb.setIdProceso(atenAsisProdemVidaPlus.getIdProceso());
        atenAsisProdemVidaPlusDb.setProveedor(atenAsisProdemVidaPlus.getProveedor());
        atenAsisProdemVidaPlusDb.setIdRubro(atenAsisProdemVidaPlus.getIdRubro());
        atenAsisProdemVidaPlusDb.setIdCiudad(atenAsisProdemVidaPlus.getIdCiudad());
        atenAsisProdemVidaPlusDb.setIdOperador(convertCharset(atenAsisProdemVidaPlus.getIdOperador()));
        atenAsisProdemVidaPlusDb.setServicio(convertCharset(atenAsisProdemVidaPlus.getServicio()));
        atenAsisProdemVidaPlusDb.setFechaContacto(stringToDate(atenAsisProdemVidaPlus.getFechaContacto()));
        atenAsisProdemVidaPlusDb.setNombreTitular(convertCharset(atenAsisProdemVidaPlus.getNombreTitular()));
        atenAsisProdemVidaPlusDb.setTelefono(atenAsisProdemVidaPlus.getTelefono());
        atenAsisProdemVidaPlusDb.setIdDocumento(convertCharset(atenAsisProdemVidaPlus.getIdDocumento()));
        atenAsisProdemVidaPlusDb.setEspecialidad(convertCharset(atenAsisProdemVidaPlus.getEspecialidad()));
        atenAsisProdemVidaPlusDb.setNombreArchivo(nombreArchivo);
        return atenAsisProdemVidaPlusDb;
    }

    public static VagonetaSeguraDb convertVagonetaSegura(VagonetaSegura vagonetaSegura,String nombreArchivo) {
        VagonetaSeguraDb vagonetaSeguraDb = new VagonetaSeguraDb();
        vagonetaSeguraDb.setIdProceso(vagonetaSegura.getIdProceso());
        vagonetaSeguraDb.setIdRubro(vagonetaSegura.getIdRubro());
        vagonetaSeguraDb.setIdCiudad(vagonetaSegura.getIdCiudad());
        vagonetaSeguraDb.setIdOperador(convertCharset(vagonetaSegura.getIdOperador()));
        vagonetaSeguraDb.setServicio(convertCharset(vagonetaSegura.getServicio()));
        vagonetaSeguraDb.setFechaContacto(stringToDate(vagonetaSegura.getFechaContacto()));
        vagonetaSeguraDb.setFechaServicio(stringToDate(vagonetaSegura.getFechaServicio()));
        vagonetaSeguraDb.setNombreTitular(convertCharset(vagonetaSegura.getNombreTitular()));
        vagonetaSeguraDb.setTelefono(vagonetaSegura.getTelefono());
        vagonetaSeguraDb.setTelefonoAlternativo(vagonetaSegura.getTelefonoAlternativo());
        vagonetaSeguraDb.setPlaca(vagonetaSegura.getPlaca());
        vagonetaSeguraDb.setNombreArchivo(nombreArchivo);
        return vagonetaSeguraDb;
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
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
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

    public static JiraLog createJiraLog(String issueId,String issueKey,String requestTypeId, String serviceDeskId,String serviceComplain,String fileName ){
        JiraLog jiraLog = new JiraLog();
        jiraLog.setIssueId(issueId);
        jiraLog.setIssueKey(issueKey);
        jiraLog.setRequestTypeId(requestTypeId);
        jiraLog.setServiceDeskId(serviceDeskId);
        jiraLog.setServiceComplain(serviceComplain);
        jiraLog.setNombreArchivo(fileName);
        return jiraLog;
    }

    public static String getCodigoLink(String code,String fechaContacto,String telefono) {
        String codigo = "NA";
        String fechaContact=Utils.toStringDateNoSigns(fechaContacto)!=null?Utils.toStringDateNoSigns(fechaContacto):getCurrentDateTimeFormatted();
        codigo = code+"."+fechaContact+"."+telefono;
        return codigo;
    }
}
