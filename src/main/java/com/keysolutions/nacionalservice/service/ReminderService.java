package com.keysolutions.nacionalservice.service;

import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageNacionalData;
import com.keysolutions.nacionalservice.database.ManageReminder;
import com.keysolutions.nacionalservice.model.*;
import com.keysolutions.nacionalservice.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ReminderService {
    @Autowired
    private ManageLog manageLog;
    @Autowired
    private ManageReminder manageReminder;
    @Autowired
    private ConsultasReclamosService consultasReclamosService;
    @Autowired
    private AtencionInicialService atencionInicialService;
    @Autowired
    private InmedicalAtuMedidaService inmedicalAtuMedidaService;
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
    private ManageNacionalData manageNacionalData;

    public void executeReminder() {
        try {
            manageReminder.retrieveConsultasReclamosReminder().stream().forEach(registro -> {
                List<ConsultasReclamos> lista = new ArrayList<>();
                ConsultasReclamos consultasReclamos = Utils.convertConsultaReclamoReminder(registro);
                log.info(registro.toString());
                log.info(consultasReclamos.toString());
                consultasReclamosService.setFileName("REENVIADO");
                lista.add(consultasReclamos);
                consultasReclamosService.sendJira(lista);
            });
        }catch (Exception ex){
            log.info("retrieveConsultasReclamosReminder: {}",ex.getMessage());
        }
       try{
        log.info("===================1=====================");
        manageReminder.retrieveAtencionInicialReminder().stream().forEach(registro -> {
            List<AtencionInicial> lista = new ArrayList<>();
            AtencionInicial atencionInicial = Utils.convertAtencionInicialReminder(registro);
            log.info(registro.toString());
            log.info(atencionInicial.toString());
            atencionInicialService.setFileName("REENVIADO");
            lista.add(atencionInicial);
            atencionInicialService.sendJira(lista);
        });
       }catch (Exception ex){
           log.info("retrieveAtencionInicialReminder: {}",ex.getMessage());
       }
       try{
        log.info("===================2=====================");
        manageReminder.retrieveInmedialAtuMedidaReminder().stream().forEach(registro -> {
            List<InmedicalAtuMedida> lista = new ArrayList<>();
            InmedicalAtuMedida inmedicalAtuMedida = Utils.convertInmedicalAtuMedidaRemainder(registro);
            log.info(registro.toString());
            log.info(inmedicalAtuMedida.toString());
            inmedicalAtuMedidaService.setFileName("REENVIADO");
            lista.add(inmedicalAtuMedida);
            inmedicalAtuMedidaService.sendJira(lista);

        });
       }catch (Exception ex){
           log.info("retrieveInmedialAtuMedidaReminder: {}",ex.getMessage());
       }
       try{
        log.info("===================3=====================");
        manageReminder.retrieveTallersE2eReminder().stream().forEach(registro -> {
            List<TallersE2e> lista = new ArrayList<>();
            TallersE2e tallersE2e = Utils.convertTallersE2eReminder(registro);
            log.info(registro.toString());
            log.info(tallersE2e.toString());
            tallersE2eService.setFileName("REENVIADO");
            lista.add(tallersE2e);
            tallersE2eService.sendJira(lista);
        });
       }catch (Exception ex){
           log.info("retrieveTallersE2eReminder: {}",ex.getMessage());
       }
       try {
           log.info("===================4=====================");
           manageReminder.retrieveCentroRehaOdontoReminder().stream().forEach(registro -> {
               List<CentroRehaOdonto> lista = new ArrayList<>();
               CentroRehaOdonto centroRehaOdonto = Utils.convertCentroRehaOdontoReminder(registro);
               log.info(registro.toString());
               log.info(centroRehaOdonto.toString());
               centroRehaOdontoService.setFileName("REENVIADO");
               lista.add(centroRehaOdonto);
               centroRehaOdontoService.sendJira(lista);
           });
       }catch (Exception ex){
           log.info("retrieveCentroRehaOdontoReminder: {}",ex.getMessage());
       }
       try{
        log.info("===================5=====================");
        manageReminder.retrieveProvServMedicoReminder().stream().forEach(registro -> {
            List<ProvServicioMedico> lista = new ArrayList<>();
            ProvServicioMedico provServicioMedico = Utils.convertProvServMedicoReminder(registro);
            log.info(registro.toString());
            log.info(provServicioMedico.toString());
            provServicioMedicosServicio.setFileName("REENVIADO");
            lista.add(provServicioMedico);
            provServicioMedicosServicio.sendJira(lista);
        });
       }catch (Exception ex){
           log.info("retrieveProvServMedicoReminder: {}",ex.getMessage());
       }
       try{
        log.info("===================6=====================");
        manageReminder.retrieveProveedorMedicoReminder().stream().forEach(registro -> {
            List<ProveedorMedico> lista = new ArrayList<>();
            ProveedorMedico proveedorMedico = Utils.convertProveedorMedicoReminder(registro);
            log.info(registro.toString());
            log.info(proveedorMedico.toString());
            proveedorMedicoServicio.setFileName("REENVIADO");
            lista.add(proveedorMedico);
            proveedorMedicoServicio.sendJira(lista);
        });
       }catch (Exception ex){
           log.info("retrieveProveedorMedicoReminder: {}",ex.getMessage());
       }
       try{
        log.info("===================7=====================");
        manageReminder.retrieveProveedorFarmaReminder().stream().forEach(registro -> {
            List<ProveedorFarma> lista = new ArrayList<>();
            ProveedorFarma proveedorFarma = Utils.convertProveedorFarmaReminder(registro);
            log.info(registro.toString());
            log.info(proveedorFarma.toString());
            proveedorFarmaService.setFileName("REENVIADO");
            lista.add(proveedorFarma);
            proveedorFarmaService.sendJira(lista);
        });
       }catch (Exception ex){
           log.info("retrieveProveedorFarmaReminder: {}",ex.getMessage());
       }
    }
}
