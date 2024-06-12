package com.keysolutions.nacionalservice.processor;

import com.keysolutions.nacionalservice.model.*;
import com.keysolutions.nacionalservice.service.*;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.util.Constant;
import com.keysolutions.nacionalservice.util.Utils;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class CsvProcessor {
    private int timesInvoked = 0;
    private Boolean isfirstTime = true;
    private Map<String, Integer> headerToIndexMap = null;
    @Autowired
    private ManageLog manageLog;
    @Value("${loaderservice.testmode}")
    private Boolean testMode;
    //===========================================
    @Autowired
    private ConsultasReclamosService consultasReclamosService;
    @Autowired
    private AsistenciaVialService asistenciaVialService;
    @Autowired
    private Nsvs800Service nsvs800Service;
    @Autowired
    private AtencionInicialService atencionInicialService;
    @Autowired
    private AccidentesPersonalesService accidentesPersonalesService;
    @Autowired
    private CallCenterNspfService callCenterNspfService;
    @Autowired
    private InmedicalAtuMedidaService inmedicalAtuMedidaService;
    @Autowired
    private InmedicalBancoGanaderoService inmedicalBancoGanaderoService;
    @Autowired
    private TallersE2eService tallersE2eService;
    @Autowired
    private CentroRehaOdontoService centroRehaOdontoService;
    @Autowired
    private ProvServicioMedicosServicio provServicioMedicosServicio;
    @Autowired
    private ProveedorMedicoServicio proveedorMedicoServicio;
    @Autowired
    private ProveedorFarmaService proveedorFarmaService;
    @Autowired
    private AsistenciaMedDomiService asistenciaMedDomiService;
    @Autowired
    private AsistenciaMedDomiAmbuService asistenciaMedDomiAmbuService;
    @Autowired
    private OrientacionMedTelefService orientacionMedTelefService;
    @Autowired
    private AtenAsisProdemVidaPlusService atenAsisProdemVidaPlusService;
    @Autowired
    private VagonetaSeguraService vagonetaSeguraService;
    @Autowired
    private SegurosMasivoService segurosMasivoService;
    //===========================================
    @PostConstruct
   public void excutionMode(){
       log.info("=======================");
       if (testMode){
           log.info("TEST MODE RUNNING");
       }else{
           log.info("PRODUCTION MODE RUNNING");
       }
       log.info("=======================");
   }
    public void asistenciaVialProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            AsistenciaVial asistenciaVial = new AsistenciaVial();
            asistenciaVial.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            asistenciaVial.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            asistenciaVial.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            asistenciaVial.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            asistenciaVial.setIdSiniestro(csvRows.get(0).get(headerToIndexMap.get("ID SINIESTRO")));
            asistenciaVial.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            asistenciaVial.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            asistenciaVial.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            asistenciaVial.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            asistenciaVial.setTelefonoAlternativo(csvRows.get(0).get(headerToIndexMap.get("TELEFONO ALTERNATIVO")));
            asistenciaVial.setPlaca(csvRows.get(0).get(headerToIndexMap.get("PLACA")));
            manageLog.recordAstenciaVial(Utils.convertAsistenciaVialDb(asistenciaVial, additionalParam));
            log.info("{}" + asistenciaVial);
            if (!testMode){
               asistenciaVialService.addAsistenciaVial(asistenciaVial);
               asistenciaVialService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
               asistenciaVialService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_ASISTENCIA_VIAL);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
        // log.info(">>> " + csvRows);
    }

    public void nSvs800Process(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            Nsvs800 nsvs800 = new Nsvs800();
            nsvs800.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            nsvs800.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            nsvs800.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            nsvs800.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            nsvs800.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            nsvs800.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            nsvs800.setIdObjeto(csvRows.get(0).get(headerToIndexMap.get("ID OJBETO")));
            nsvs800.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            nsvs800.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            nsvs800.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            nsvs800.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            nsvs800.setIdPoliza(csvRows.get(0).get(headerToIndexMap.get("ID POLIZA")));
            manageLog.recordNsvs800(Utils.convertNsvs800Db(nsvs800, additionalParam));
            log.info("{}" , nsvs800);
            if (!testMode){
                nsvs800Service.addNsvs800(nsvs800);
                nsvs800Service.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
               nsvs800Service.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_NSVS_800);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
        // log.info(">>> " + csvRows);
    }

    public void consultasReclamosProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            ConsultasReclamos consultasReclamos = new ConsultasReclamos();
            consultasReclamos.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            consultasReclamos.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            consultasReclamos.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            consultasReclamos.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            consultasReclamos.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            consultasReclamos.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            consultasReclamos.setFechaCierreFreshDesk(csvRows.get(0).get(headerToIndexMap.get("FF HH CIERRE TICKET FRESHDESK")));
            consultasReclamos.setIdObjeto(csvRows.get(0).get(headerToIndexMap.get("ID OBJETO")));
            consultasReclamos.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            consultasReclamos.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            consultasReclamos.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            consultasReclamos.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            consultasReclamos.setIdPoliza(csvRows.get(0).get(headerToIndexMap.get("ID POLIZA")));
            consultasReclamos.setAsunto(csvRows.get(0).get(headerToIndexMap.get("ASUNTO")));
            manageLog.recordConsutasReclamos(Utils.convertConsultaReclamo(consultasReclamos, additionalParam));
            if (!testMode){
               consultasReclamosService.addConsultaReclamo(consultasReclamos);
               consultasReclamosService.setFileName(additionalParam);
            }
            log.info("{}" , consultasReclamos);
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
               consultasReclamosService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_CONSULTAS_RECLAMOS);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void atencionInicialProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            AtencionInicial atencionInicial = new AtencionInicial();
            atencionInicial.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            atencionInicial.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            atencionInicial.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            atencionInicial.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            atencionInicial.setIdEjecutivo(csvRows.get(0).get(headerToIndexMap.get("ID EJECUTIVO")));
            atencionInicial.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            atencionInicial.setPlaca(csvRows.get(0).get(headerToIndexMap.get("PLACA")));
            atencionInicial.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            atencionInicial.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            atencionInicial.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            atencionInicial.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            atencionInicial.setIdPoliza(csvRows.get(0).get(headerToIndexMap.get("ID POLIZA")));
            manageLog.recordAtencionInicial(Utils.convertAtencionInicialDb(atencionInicial, additionalParam));
            log.info("{}" , atencionInicial);
            if (!testMode){
                atencionInicialService.addAtencionInicial(atencionInicial);
                atencionInicialService.setFileName(additionalParam);
            }

        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                atencionInicialService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_ATENCION_INICIAL);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }
    public void accidentePersonalProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            AccidentesPersonales accidentesPersonales = new AccidentesPersonales();
            accidentesPersonales.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            accidentesPersonales.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            accidentesPersonales.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            accidentesPersonales.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            accidentesPersonales.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            accidentesPersonales.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            accidentesPersonales.setPlaca(csvRows.get(0).get(headerToIndexMap.get("PLACA")));
            accidentesPersonales.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            accidentesPersonales.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            accidentesPersonales.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            accidentesPersonales.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            manageLog.recordAccidentePersonal(Utils.convertAccidentePersonalDB(accidentesPersonales, additionalParam));
            log.info("{}" , accidentesPersonales);
            if (!testMode){
                accidentesPersonalesService.addAccidentesPersonales(accidentesPersonales);
                accidentesPersonalesService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                accidentesPersonalesService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_ACCIDENTES_PERSONALES);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }
    public void callCenterNfspfProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            CallCenterNspf callCenterNspf = new CallCenterNspf();
            callCenterNspf.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            callCenterNspf.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            callCenterNspf.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            callCenterNspf.setIdSiniestro(csvRows.get(0).get(headerToIndexMap.get("ID SINIESTRO")));
            callCenterNspf.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            callCenterNspf.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            callCenterNspf.setIdCliente(csvRows.get(0).get(headerToIndexMap.get("ID CLIENTE")));
            manageLog.recordCallCenterNspf(Utils.convertCallCenterNspfDb(callCenterNspf, additionalParam));
            log.info("{}" , callCenterNspf);
            if (!testMode){
                callCenterNspfService.addCallCenterNspf(callCenterNspf);
                callCenterNspfService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
              callCenterNspfService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_CALLCENTER_NSPF);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void inmedicalAtuMedidaProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            InmedicalAtuMedida inmedicalAtuMedida = new InmedicalAtuMedida();
            inmedicalAtuMedida.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            inmedicalAtuMedida.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            inmedicalAtuMedida.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            inmedicalAtuMedida.setProveedor(csvRows.get(0).get(headerToIndexMap.get("PROVEEDOR")));
            inmedicalAtuMedida.setIdCentro(csvRows.get(0).get(headerToIndexMap.get("ID_CENTRO")));
            inmedicalAtuMedida.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            inmedicalAtuMedida.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            inmedicalAtuMedida.setIdDocumento(csvRows.get(0).get(headerToIndexMap.get("ID_DOCUMENTO")));
            inmedicalAtuMedida.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            inmedicalAtuMedida.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            inmedicalAtuMedida.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            inmedicalAtuMedida.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            manageLog.recordInmedicalAtuMedida(Utils.convertInmedicalAtuMedidaDb(inmedicalAtuMedida, additionalParam));
            log.info("{}" , inmedicalAtuMedida);
            if (!testMode){
                inmedicalAtuMedidaService.addInmedicalAtuMedida(inmedicalAtuMedida);
                inmedicalAtuMedidaService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                inmedicalAtuMedidaService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_INMEDICAL_ATU_MEDIDA);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void inmedicalBancoGanaderoProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            InmedicalBancoGanadero inmedicalBancoGanadero = new InmedicalBancoGanadero();
            inmedicalBancoGanadero.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            inmedicalBancoGanadero.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            inmedicalBancoGanadero.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            inmedicalBancoGanadero.setProveedor(csvRows.get(0).get(headerToIndexMap.get("PROVEEDOR")));
            inmedicalBancoGanadero.setIdCentro(csvRows.get(0).get(headerToIndexMap.get("ID_CENTRO")));
            inmedicalBancoGanadero.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            inmedicalBancoGanadero.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            inmedicalBancoGanadero.setIdDocumento(csvRows.get(0).get(headerToIndexMap.get("ID_DOCUMENTO")));
            inmedicalBancoGanadero.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            inmedicalBancoGanadero.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            inmedicalBancoGanadero.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            inmedicalBancoGanadero.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            manageLog.recordInmedicalBancoGanadero(Utils.convertInmedicalBancoGanaderoDb(inmedicalBancoGanadero, additionalParam));
            log.info("{}" , inmedicalBancoGanadero);
            if (!testMode){
                inmedicalBancoGanaderoService.addInmedicaBancoGandero(inmedicalBancoGanadero);
                inmedicalBancoGanaderoService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                inmedicalBancoGanaderoService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_INMEDICAL_BANCO_GANADERO);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void talleresE2eProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            TallersE2e tallersE2e = new TallersE2e();
            tallersE2e.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            tallersE2e.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            tallersE2e.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            tallersE2e.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            tallersE2e.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            tallersE2e.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            tallersE2e.setPeriodo(csvRows.get(0).get(headerToIndexMap.get("PERIODO")));
            tallersE2e.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            tallersE2e.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            tallersE2e.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            tallersE2e.setMarca(csvRows.get(0).get(headerToIndexMap.get("MARCA")));
            tallersE2e.setModelo(csvRows.get(0).get(headerToIndexMap.get("MODELO")));
            tallersE2e.setPlaca(csvRows.get(0).get(headerToIndexMap.get("PLACA")));
            tallersE2e.setColor(csvRows.get(0).get(headerToIndexMap.get("COLOR")));
            tallersE2e.setIdPoliza(csvRows.get(0).get(headerToIndexMap.get("ID POLIZA")));
            tallersE2e.setIntermediario(csvRows.get(0).get(headerToIndexMap.get("INTERMEDIARIO")));
            tallersE2e.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            manageLog.recordTalleresE2e(Utils.convertTallersE2eDb(tallersE2e, additionalParam));
            log.info("{}" , tallersE2e);
            if (!testMode){
                tallersE2eService.addTalleresE2e(tallersE2e);
                tallersE2eService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                tallersE2eService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_TALLERES_E2E);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void centroRehaOndotoProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            CentroRehaOdonto centroRehaOdonto = new CentroRehaOdonto();
            centroRehaOdonto.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            centroRehaOdonto.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            centroRehaOdonto.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            centroRehaOdonto.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            centroRehaOdonto.setCentroMedico(csvRows.get(0).get(headerToIndexMap.get("CENTRO MEDICO")));
            centroRehaOdonto.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            centroRehaOdonto.setFechaSalida(csvRows.get(0).get(headerToIndexMap.get("FF HH SALIDA CON CC")));
            centroRehaOdonto.setPaciente(csvRows.get(0).get(headerToIndexMap.get("PACIENTE")));
            centroRehaOdonto.setPlan(csvRows.get(0).get(headerToIndexMap.get("PLAN")));
            centroRehaOdonto.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            centroRehaOdonto.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            centroRehaOdonto.setTelefonoAdicional(csvRows.get(0).get(headerToIndexMap.get("TELEFONO ADICIONAL")));
            manageLog.recordCentroRehaOdonto(Utils.convertCentroRehaOdontoDb(centroRehaOdonto, additionalParam));
            log.info("{}" , centroRehaOdonto);
            if (!testMode){
                centroRehaOdontoService.addCentroRehaOdonto(centroRehaOdonto);
                centroRehaOdontoService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                centroRehaOdontoService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_CENTRO_REHA_ODONTOLOGICA);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void provServMedicoProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
       if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            ProvServicioMedico provServicioMedico = new ProvServicioMedico();
           provServicioMedico.setIdProveedor(csvRows.get(0).get(headerToIndexMap.get("ID PROVEEDOR")));
           provServicioMedico.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
           provServicioMedico.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
           provServicioMedico.setProducto(csvRows.get(0).get(headerToIndexMap.get("PRODUCTO")));
           provServicioMedico.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
           provServicioMedico.setFechaDenuncia(csvRows.get(0).get(headerToIndexMap.get("FF HH DENUNCIA")));
           provServicioMedico.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
           provServicioMedico.setIdEjecutivo(csvRows.get(0).get(headerToIndexMap.get("ID EJECUTIVO")));
           provServicioMedico.setNombreAsegurado(csvRows.get(0).get(headerToIndexMap.get("NOMBRE ASEGURADO")));
           provServicioMedico.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
           provServicioMedico.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
           provServicioMedico.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
           provServicioMedico.setPoliza(csvRows.get(0).get(headerToIndexMap.get("POLIZA")));
           provServicioMedico.setDignostico(csvRows.get(0).get(headerToIndexMap.get("DIAGNOSTICO")));
           provServicioMedico.setIntermediario(csvRows.get(0).get(headerToIndexMap.get("INTERMEDIARIO")));
            manageLog.recordProvServMedico(Utils.convertProvServMedicoDb(provServicioMedico, additionalParam));
            log.info("{}" , provServicioMedico);
            if (!testMode){
                provServicioMedicosServicio.addProvServicioMedico(provServicioMedico);
                provServicioMedicosServicio.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                provServicioMedicosServicio.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_PROV_SERV_MEDICO);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void proveedorMedicoProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            ProveedorMedico proveedorMedico = new ProveedorMedico();
            proveedorMedico.setIdProveedor(csvRows.get(0).get(headerToIndexMap.get("ID PROVEEDOR")));
            proveedorMedico.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            proveedorMedico.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            proveedorMedico.setProducto(csvRows.get(0).get(headerToIndexMap.get("PRODUCTO")));
            proveedorMedico.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            proveedorMedico.setFechaDenuncia(csvRows.get(0).get(headerToIndexMap.get("FF HH DENUNCIA")));
            proveedorMedico.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            proveedorMedico.setIdEjecutivo(csvRows.get(0).get(headerToIndexMap.get("ID EJECUTIVO")));
            proveedorMedico.setNombreAsegurado(csvRows.get(0).get(headerToIndexMap.get("NOMBRE ASEGURADO")));
            proveedorMedico.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            proveedorMedico.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            proveedorMedico.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            proveedorMedico.setPoliza(csvRows.get(0).get(headerToIndexMap.get("POLIZA")));
            proveedorMedico.setDignostico(csvRows.get(0).get(headerToIndexMap.get("DIAGNOSTICO")));
            proveedorMedico.setIntermediario(csvRows.get(0).get(headerToIndexMap.get("INTERMEDIARIO")));
            manageLog.recordProveedorMedico(Utils.convertProveedorMedicoDb(proveedorMedico, additionalParam));
            log.info("{}" , proveedorMedico);
            if (!testMode){
                proveedorMedicoServicio.addProveedorMedico(proveedorMedico);
                proveedorMedicoServicio.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                proveedorMedicoServicio.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_PROVEEDOR_MEDICO);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }
    public void proveedorFarmaProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            ProveedorFarma proveedorFarma = new ProveedorFarma();
            proveedorFarma.setIdProveedor(csvRows.get(0).get(headerToIndexMap.get("ID PROVEEDOR")));
            proveedorFarma.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            proveedorFarma.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            proveedorFarma.setProducto(csvRows.get(0).get(headerToIndexMap.get("PRODUCTO")));
            proveedorFarma.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            proveedorFarma.setFechaDenuncia(csvRows.get(0).get(headerToIndexMap.get("FF HH DENUNCIA")));
            proveedorFarma.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            proveedorFarma.setIdEjecutivo(csvRows.get(0).get(headerToIndexMap.get("ID EJECUTIVO")));
            proveedorFarma.setNombreAsegurado(csvRows.get(0).get(headerToIndexMap.get("NOMBRE ASEGURADO")));
            proveedorFarma.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            proveedorFarma.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            proveedorFarma.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            proveedorFarma.setPoliza(csvRows.get(0).get(headerToIndexMap.get("POLIZA")));
            proveedorFarma.setDignostico(csvRows.get(0).get(headerToIndexMap.get("DIAGNOSTICO")));
            proveedorFarma.setIntermediario(csvRows.get(0).get(headerToIndexMap.get("INTERMEDIARIO")));
            manageLog.recordProveedorFarma(Utils.convertProveedorFarmaDb(proveedorFarma, additionalParam));
            log.info("{}" , proveedorFarma);
            if (!testMode){
                proveedorFarmaService.addProveedorFarma(proveedorFarma);
                proveedorFarmaService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                proveedorFarmaService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_PROVEEDOR_FARMA);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void asistenciaMedDomiProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            AsistenciaMedDomi asistenciaMedDomi = new AsistenciaMedDomi();
            asistenciaMedDomi.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            asistenciaMedDomi.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            asistenciaMedDomi.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            asistenciaMedDomi.setEdad(csvRows.get(0).get(headerToIndexMap.get("EDAD")));
            asistenciaMedDomi.setMotivoLlamada(csvRows.get(0).get(headerToIndexMap.get("MOTIVO LLAMADA")));
            asistenciaMedDomi.setProducto(csvRows.get(0).get(headerToIndexMap.get("PRODUCTO")));
            asistenciaMedDomi.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            asistenciaMedDomi.setDesenlace(csvRows.get(0).get(headerToIndexMap.get("DESENLACE")));
            asistenciaMedDomi.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            asistenciaMedDomi.setFechaArribo(csvRows.get(0).get(headerToIndexMap.get("FF HH ARRIBO")));
            asistenciaMedDomi.setIncidencia(csvRows.get(0).get(headerToIndexMap.get("INCIDENCIA")));
            asistenciaMedDomi.setUnidadAsignada(csvRows.get(0).get(headerToIndexMap.get("UNIDAD ASIGNADA")));
            asistenciaMedDomi.setPaciente(csvRows.get(0).get(headerToIndexMap.get("PACIENTE")));
            asistenciaMedDomi.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            asistenciaMedDomi.setTelefonoAlternativo(csvRows.get(0).get(headerToIndexMap.get("TELEFONO ALTERNATIVO")));
            asistenciaMedDomi.setDireccionAtencion(csvRows.get(0).get(headerToIndexMap.get("DIRECCION ATENCION")));
            asistenciaMedDomi.setAclaraciones(csvRows.get(0).get(headerToIndexMap.get("ACLARACIONES")));
            manageLog.recordAsistenciaMedDormi(Utils.convertAsistenciaMedDomiDb(asistenciaMedDomi, additionalParam));
            log.info("{}" , asistenciaMedDomi);
            if (!testMode){
                asistenciaMedDomiService.addAsistenciaMedDomi(asistenciaMedDomi);
                asistenciaMedDomiService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                asistenciaMedDomiService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_ASISTENCIA_MED_DOM);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void asistenciaMedDomiAmbuProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            AsistenciaMedDomiAmbu asistenciaMedDomiAmbu = new AsistenciaMedDomiAmbu();
            asistenciaMedDomiAmbu.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            asistenciaMedDomiAmbu.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            asistenciaMedDomiAmbu.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            asistenciaMedDomiAmbu.setEdad(csvRows.get(0).get(headerToIndexMap.get("EDAD")));
            asistenciaMedDomiAmbu.setMotivoLlamada(csvRows.get(0).get(headerToIndexMap.get("MOTIVO LLAMADA")));
            asistenciaMedDomiAmbu.setProducto(csvRows.get(0).get(headerToIndexMap.get("PRODUCTO")));
            asistenciaMedDomiAmbu.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            asistenciaMedDomiAmbu.setDesenlace(csvRows.get(0).get(headerToIndexMap.get("DESENLACE")));
            asistenciaMedDomiAmbu.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            asistenciaMedDomiAmbu.setFechaArribo(csvRows.get(0).get(headerToIndexMap.get("FF HH ARRIBO")));
            asistenciaMedDomiAmbu.setIncidencia(csvRows.get(0).get(headerToIndexMap.get("INCIDENCIA")));
            asistenciaMedDomiAmbu.setUnidadAsignada(csvRows.get(0).get(headerToIndexMap.get("UNIDAD ASIGNADA")));
            asistenciaMedDomiAmbu.setPaciente(csvRows.get(0).get(headerToIndexMap.get("PACIENTE")));
            asistenciaMedDomiAmbu.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            asistenciaMedDomiAmbu.setTelefonoAlternativo(csvRows.get(0).get(headerToIndexMap.get("TELEFONO ALTERNATIVO")));
            asistenciaMedDomiAmbu.setDireccionAtencion(csvRows.get(0).get(headerToIndexMap.get("DIRECCION ATENCION")));
            asistenciaMedDomiAmbu.setAclaraciones(csvRows.get(0).get(headerToIndexMap.get("ACLARACIONES")));
            manageLog.recordAsistenciaMedDormiAmbu(Utils.convertAsistenciaMedDomiAmbuDb(asistenciaMedDomiAmbu, additionalParam));
            log.info("{}" , asistenciaMedDomiAmbu);
            if (!testMode){
                asistenciaMedDomiAmbuService.addAsistenciaMedDomiAmbu(asistenciaMedDomiAmbu);
                asistenciaMedDomiAmbuService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                asistenciaMedDomiAmbuService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_ASISTENCIA_MED_DOM_AMBU);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void orientaMedTelefProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            OrientacionMedTelef orientacionMedTelef = new OrientacionMedTelef();
            orientacionMedTelef.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            orientacionMedTelef.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            orientacionMedTelef.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            orientacionMedTelef.setEdad(csvRows.get(0).get(headerToIndexMap.get("EDAD")));
            orientacionMedTelef.setMotivoLlamada(csvRows.get(0).get(headerToIndexMap.get("MOTIVO LLAMADA")));
            orientacionMedTelef.setContactoCovid(csvRows.get(0).get(headerToIndexMap.get("CONTACTO COVID")));
            orientacionMedTelef.setDiagnosticoPresuntivo(csvRows.get(0).get(headerToIndexMap.get("DIAGNOSTICO PRESUNTIVO")));
            orientacionMedTelef.setConducta(csvRows.get(0).get(headerToIndexMap.get("CONDUCTA")));
            orientacionMedTelef.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            orientacionMedTelef.setMedico(csvRows.get(0).get(headerToIndexMap.get("MEDICO")));
            orientacionMedTelef.setPaciente(csvRows.get(0).get(headerToIndexMap.get("PACIENTE")));
            orientacionMedTelef.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            orientacionMedTelef.setTelefonoAlternativo(csvRows.get(0).get(headerToIndexMap.get("TELEFONO ALTERNATIVO")));
            orientacionMedTelef.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            orientacionMedTelef.setObservaciones(csvRows.get(0).get(headerToIndexMap.get("OBSERVACIONES")));
            orientacionMedTelef.setAclaraciones(csvRows.get(0).get(headerToIndexMap.get("ACLARACIONES")));
            manageLog.recordOrientaMedicaTelf(Utils.convertOrientacionMedTelefDb(orientacionMedTelef, additionalParam));
            log.info("{}" , orientacionMedTelef);
            if (!testMode){
                orientacionMedTelefService.addOrientacionMedTelef(orientacionMedTelef);
                orientacionMedTelefService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                orientacionMedTelefService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_ASISTENCIA_MED_DOM_AMBU);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void atenAsisProdemVidaPlusProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            AtenAsisProdemVidaPlus atenAsisProdemVidaPlus = new AtenAsisProdemVidaPlus();
            atenAsisProdemVidaPlus.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            atenAsisProdemVidaPlus.setProveedor(csvRows.get(0).get(headerToIndexMap.get("PROVEEDOR")));
            atenAsisProdemVidaPlus.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            atenAsisProdemVidaPlus.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            atenAsisProdemVidaPlus.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            atenAsisProdemVidaPlus.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            atenAsisProdemVidaPlus.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            atenAsisProdemVidaPlus.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            atenAsisProdemVidaPlus.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            atenAsisProdemVidaPlus.setIdDocumento(csvRows.get(0).get(headerToIndexMap.get("ID DOCUMENTO")));
            atenAsisProdemVidaPlus.setEspecialidad(csvRows.get(0).get(headerToIndexMap.get("ESPECIALIDAD")));
            manageLog.recordAtenAsisProdemVidaPlus(Utils.convertAtenAsisProdemVidaPlusDb(atenAsisProdemVidaPlus, additionalParam));
            log.info("{}" , atenAsisProdemVidaPlus);
            if (!testMode){
                atenAsisProdemVidaPlusService.addAtenAsisProdemVidaPlus(atenAsisProdemVidaPlus);
                atenAsisProdemVidaPlusService.setFileName(additionalParam);
            }
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                atenAsisProdemVidaPlusService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_ATEN_ASIS_PROD_VIDA_PLUS);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }
    public void vagonetaSeguraProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            VagonetaSegura vagonetaSegura = new VagonetaSegura();
            vagonetaSegura.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            vagonetaSegura.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            vagonetaSegura.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            vagonetaSegura.setIdOperador(csvRows.get(0).get(headerToIndexMap.get("ID OPERADOR")));
            vagonetaSegura.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            vagonetaSegura.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            vagonetaSegura.setFechaServicio(csvRows.get(0).get(headerToIndexMap.get("FF HH SERVICIO")));
            vagonetaSegura.setNombreTitular(csvRows.get(0).get(headerToIndexMap.get("NOMBRE TITULAR")));
            vagonetaSegura.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            vagonetaSegura.setTelefonoAlternativo(csvRows.get(0).get(headerToIndexMap.get("TELEFONO ALTERNATIVO")));
            vagonetaSegura.setPlaca(csvRows.get(0).get(headerToIndexMap.get("PLACA")));
            manageLog.recordVagonetaSegura(Utils.convertVagonetaSegura(vagonetaSegura, additionalParam));
            if (!testMode){
                vagonetaSeguraService.addVagonetaSegura(vagonetaSegura);
                vagonetaSeguraService.setFileName(additionalParam);
            }
            log.info("{}" , vagonetaSegura);
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                vagonetaSeguraService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_VAGONETA_SEGURA);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }

    public void segurosMasivoProcess(List<List<String>> csvRows, @Header("additionalParam") String additionalParam,@Header("totalRows") int totalRows) {
        if (isfirstTime) {
            headerToIndexMap = createHeaderToIndexMap(csvRows.get(0));
            isfirstTime = false;
            log.info("{}" , headerToIndexMap);
        } else {
            SegurosMasivo segurosMasivo = new SegurosMasivo();
            segurosMasivo.setIdProceso(csvRows.get(0).get(headerToIndexMap.get("ID PROCESO")));
            segurosMasivo.setIdRubro(csvRows.get(0).get(headerToIndexMap.get("ID RUBRO")));
            segurosMasivo.setSponsor(csvRows.get(0).get(headerToIndexMap.get("SPONSOR")));
            segurosMasivo.setIdCiudad(csvRows.get(0).get(headerToIndexMap.get("ID CIUDAD")));
            segurosMasivo.setProducto(csvRows.get(0).get(headerToIndexMap.get("PRODUCTO")));
            segurosMasivo.setProveedorDeAsistencia(csvRows.get(0).get(headerToIndexMap.get("PROVEEDOR DE ASISTENCIA")));
            segurosMasivo.setServicio(csvRows.get(0).get(headerToIndexMap.get("SERVICIO")));
            segurosMasivo.setFechaContacto(csvRows.get(0).get(headerToIndexMap.get("FF HH CONTACTO CON CC")));
            segurosMasivo.setFechaUsoServicio(csvRows.get(0).get(headerToIndexMap.get("FF HH USO DEL SERVICIO")));
            segurosMasivo.setIdDocumento(csvRows.get(0).get(headerToIndexMap.get("ID_DOCUMENTO")));
            segurosMasivo.setNombreSolicitante(csvRows.get(0).get(headerToIndexMap.get("NOMBRE SOLICITANTE")));
            segurosMasivo.setTelefono(csvRows.get(0).get(headerToIndexMap.get("TELEFONO")));
            segurosMasivo.setEmail(csvRows.get(0).get(headerToIndexMap.get("EMAIL")));
            manageLog.recordSegurosMasivo(Utils.convertSegurosMasivo(segurosMasivo, additionalParam));
            if (!testMode){
                segurosMasivoService.addSegurosMasivo(segurosMasivo);
                segurosMasivoService.setFileName(additionalParam);
            }
            log.info("{}" , segurosMasivo);
        }
        timesInvoked++;
        if (timesInvoked==totalRows){
            if (!testMode){
                segurosMasivoService.createJiraSurvey();
            }
            manageLog.recorArchivoCargado(additionalParam,totalRows-1,Constant.CONFIG_SEGUROS_MASIVO);
            log.info("____________________FIN DE ARCHIVO_______________________________");
        }
    }
    private Map<String, Integer> createHeaderToIndexMap(List<String> headerRow) {
        Map<String, Integer> headerToIndexMap = new HashMap<>();
        for (int i = 0; i < headerRow.size(); i++) {
            String header = headerRow.get(i).trim();
            // Eliminar los caracteres ï»¿ si están presentes
            header = header.replace("ï»¿", "");
            headerToIndexMap.put(header.toUpperCase(), i);
        }
        return headerToIndexMap;
    }

    public int getTimesInvoked() {
        return timesInvoked;
    }

    public void init() {
        timesInvoked = 0;
        isfirstTime = true;
        headerToIndexMap = null;
        log.info("===========================================init=========================================");
    }
    public void endProcess(){
        log.info("-------------------------------------------endProcess-----------------------------------");
    }
}
