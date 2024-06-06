package com.keysolutions.nacionalservice.controller;

import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageReminder;
import com.keysolutions.nacionalservice.model.AtencionInicial;
import com.keysolutions.nacionalservice.model.CentroRehaOdonto;
import com.keysolutions.nacionalservice.model.ConsultasReclamos;
import com.keysolutions.nacionalservice.model.InmedicalAtuMedida;
import com.keysolutions.nacionalservice.model.ProvServicioMedico;
import com.keysolutions.nacionalservice.model.ProveedorFarma;
import com.keysolutions.nacionalservice.model.ProveedorMedico;
import com.keysolutions.nacionalservice.model.TallersE2e;
import com.keysolutions.nacionalservice.model.log.ArchivoCargadoDb;
import com.keysolutions.nacionalservice.model.log.ConsultasReclamosDb;
import com.keysolutions.nacionalservice.service.AtencionInicialService;
import com.keysolutions.nacionalservice.service.CentroRehaOdontoService;
import com.keysolutions.nacionalservice.service.ConsultasReclamosService;
import com.keysolutions.nacionalservice.service.InmedicalAtuMedidaService;
import com.keysolutions.nacionalservice.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.service.JiraService;
import com.keysolutions.nacionalservice.service.ProvServicioMedicosServicio;
import com.keysolutions.nacionalservice.service.ProveedorFarmaService;
import com.keysolutions.nacionalservice.service.ProveedorMedicoServicio;
import com.keysolutions.nacionalservice.service.TallersE2eService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
@Slf4j
public class LoaderServiceController {
@Autowired
private JiraService jiraService;
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

    @GetMapping("/archivos")
    public ResponseEntity<List<ArchivoCargadoDb>> retrieveCantidadArchivos() {
         List<ArchivoCargadoDb> archivos= manageLog.retrieveArchivosCargados();
        return ResponseEntity.status(200).body(archivos);

    }

    @GetMapping("/reminder")
    public ResponseEntity<String> retrieveReminder() {
          manageReminder.retrieveConsultasReclamosReminder().stream().forEach(registro->{
              List<ConsultasReclamos> lista = new ArrayList<>();
              ConsultasReclamos consultasReclamos = Utils.convertConsultaReclamoReminder(registro);
              log.info(registro.toString());
              log.info(consultasReclamos.toString());
              consultasReclamosService.setFileName("REENVIADO");
              lista.add(consultasReclamos);
              consultasReclamosService.sendJira(lista);
          });

        
        log.info("===================1=====================");
         manageReminder.retrieveAtencionInicialReminder().stream().forEach(registro->{
            List<AtencionInicial> lista = new ArrayList<>();
            AtencionInicial atencionInicial = Utils.convertAtencionInicialReminder(registro);
            log.info(registro.toString());
            log.info(atencionInicial.toString());
            atencionInicialService.setFileName("REENVIADO");
            lista.add(atencionInicial);
            atencionInicialService.sendJira(lista);
        });

        log.info("===================2=====================");
            manageReminder.retrieveInmedialAtuMedidaReminder().stream().forEach(registro->{
            List<InmedicalAtuMedida> lista = new ArrayList<>();
            InmedicalAtuMedida inmedicalAtuMedida = Utils.convertInmedicalAtuMedidaRemainder(registro);
            log.info(registro.toString());
            log.info(inmedicalAtuMedida.toString());
            inmedicalAtuMedidaService.setFileName("REENVIADO");
            lista.add(inmedicalAtuMedida);
            inmedicalAtuMedidaService.sendJira(lista);
             
        });
        log.info("===================3=====================");
        manageReminder.retrieveTallersE2eReminder().stream().forEach(registro->{
            List<TallersE2e> lista = new ArrayList<>();
            TallersE2e tallersE2e = Utils.convertTallersE2eReminder(registro);
            log.info(registro.toString());
            log.info(tallersE2e.toString());
            tallersE2eService.setFileName("REENVIADO");
            lista.add(tallersE2e);
            tallersE2eService.sendJira(lista);
        });
        log.info("===================4=====================");
        manageReminder.retrieveCentroRehaOdontoReminder().stream().forEach(registro->{
            List<CentroRehaOdonto> lista = new ArrayList<>();
            CentroRehaOdonto centroRehaOdonto = Utils.convertCentroRehaOdontoReminder(registro);
            log.info(registro.toString());
            log.info(centroRehaOdonto.toString());
            centroRehaOdontoService.setFileName("REENVIADO");
            lista.add(centroRehaOdonto);
            centroRehaOdontoService.sendJira(lista);
        });
        log.info("===================5=====================");
        manageReminder.retrieveProvServMedicoReminder().stream().forEach(registro->{
            List<ProvServicioMedico> lista = new ArrayList<>();
            ProvServicioMedico provServicioMedico = Utils.convertProvServMedicoReminder(registro);
            log.info(registro.toString());
            log.info(provServicioMedico.toString());
            provServicioMedicosServicio.setFileName("REENVIADO");
            lista.add(provServicioMedico);
            provServicioMedicosServicio.sendJira(lista);
        });
        
        log.info("===================6=====================");
        manageReminder.retrieveProveedorMedicoReminder().stream().forEach(registro->{
            List<ProveedorMedico> lista = new ArrayList<>();
            ProveedorMedico proveedorMedico = Utils.convertProveedorMedicoReminder(registro);
            log.info(registro.toString());
            log.info(proveedorMedico.toString());
            proveedorMedicoServicio.setFileName("REENVIADO");
            lista.add(proveedorMedico);
            proveedorMedicoServicio.sendJira(lista);
        });
        
        log.info("===================7=====================");
        manageReminder.retrieveProveedorFarmaReminder().stream().forEach(registro->{
            List<ProveedorFarma> lista = new ArrayList<>();
            ProveedorFarma proveedorFarma = Utils.convertProveedorFarmaReminder(registro);
            log.info(registro.toString());
            log.info(proveedorFarma.toString());
            proveedorFarmaService.setFileName("REENVIADO");
            lista.add(proveedorFarma);
            proveedorFarmaService.sendJira(lista);
        });
        
        return ResponseEntity.status(200).body("OK");

    }
}
