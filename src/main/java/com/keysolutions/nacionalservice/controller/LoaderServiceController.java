package com.keysolutions.nacionalservice.controller;

import com.keysolutions.nacionalservice.database.ManageLog;
import com.keysolutions.nacionalservice.model.log.ArchivoCargadoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keysolutions.nacionalservice.model.jira.Issue;
import com.keysolutions.nacionalservice.model.jira.TicketRequest;
import com.keysolutions.nacionalservice.service.JiraService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class LoaderServiceController {
@Autowired
private JiraService jiraService;
    @Autowired
    ManageLog manageLog;
    @GetMapping("/archivos")
    public ResponseEntity<List<ArchivoCargadoDb>> retrieveCantidadArchivos() {
         List<ArchivoCargadoDb> archivos= manageLog.retrieveArchivosCargados();
        return ResponseEntity.status(200).body(archivos);

    }
}
