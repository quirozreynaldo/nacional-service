package com.keysolutions.nacionalservice.controller;

import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.database.ManageReminder;
import com.keysolutions.nacionalservice.model.AtencionInicial;
import com.keysolutions.nacionalservice.model.ConsultasReclamos;
import com.keysolutions.nacionalservice.model.log.ArchivoCargadoDb;
import com.keysolutions.nacionalservice.model.log.ConsultasReclamosDb;
import com.keysolutions.nacionalservice.service.ConsultasReclamosService;
import com.keysolutions.nacionalservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.service.JiraService;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
public class LoaderServiceController {
@Autowired
private JiraService jiraService;
    @Autowired
    ManageLog manageLog;
    @Autowired
    ManageReminder manageReminder;
    @Autowired
    ConsultasReclamosService consultasReclamosService;
    @GetMapping("/archivos")
    public ResponseEntity<List<ArchivoCargadoDb>> retrieveCantidadArchivos() {
         List<ArchivoCargadoDb> archivos= manageLog.retrieveArchivosCargados();
        return ResponseEntity.status(200).body(archivos);

    }

    @GetMapping("/reminder")
    public ResponseEntity<String> retrieveReminder() {
       /*   manageReminder.retrieveConsultasReclamosReminder().stream().forEach(registro->{
              List<ConsultasReclamos> lista = new ArrayList<>();
              ConsultasReclamos consultasReclamos = Utils.convertConsultaReclamoReminder(registro);
              System.out.println(registro.toString());
              System.out.println(consultasReclamos.toString());
              consultasReclamosService.setFileName("REENVIADO");
              lista.add(consultasReclamos);
              consultasReclamosService.sendJira(lista);
          });

        */
        System.out.println("===================1=====================");
        manageReminder.retrieveAtencionInicialReminder().stream().forEach(registro->{
            List<AtencionInicial> lista = new ArrayList<>();
            AtencionInicial atencionInicial = Utils.convertAtencionInicialReminder(registro);
            System.out.println(registro.toString());
            System.out.println(atencionInicial.toString());
        });
        System.out.println("===================2=====================");
        manageReminder.retrieveInmedialAtuMedidaReminder().stream().forEach(registro->{
            System.out.println(registro.toString());
        });
        System.out.println("===================3=====================");
        manageReminder.retrieveTallersE2eReminder().stream().forEach(registro->{
            System.out.println(registro.toString());
        });
        System.out.println("===================4=====================");
        manageReminder.retrieveCentroRehaOdontoReminder().stream().forEach(registro->{
            System.out.println(registro.toString());
        });
        System.out.println("===================5=====================");
        manageReminder.retrieveProvServMedicoReminder().stream().forEach(registro->{
            System.out.println(registro.toString());
        });
        System.out.println("===================6=====================");
        manageReminder.retrieveProveedorMedicoReminder().stream().forEach(registro->{
            System.out.println(registro.toString());
        });
        System.out.println("===================7=====================");
        manageReminder.retrieveProveedorFarmaReminder().stream().forEach(registro->{
            System.out.println(registro.toString());
        });
        return ResponseEntity.status(200).body("OK");

    }
}
