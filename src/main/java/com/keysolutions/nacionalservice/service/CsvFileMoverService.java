package com.keysolutions.nacionalservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CsvFileMoverService {
    @Value("${loaderservice.source.directory}")
    private String sourceDirectory;
    @Value("${loaderservice.destination.directory}")
    private String destinationDirectory;
    @Value("${loaderservice.fileprefix}")
    private String filePrefix;
    @Value("${loaderservice.cron}")
    private String loaderserviceCron;

    public void setLoaderserviceCron(String loaderserviceCron) {
        this.loaderserviceCron = loaderserviceCron;
    }

    public void moveCSVFiles() {
        File sourceDir = new File(sourceDirectory);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            log.info("El directorio de origen {} no existe o no es un directorio válido.",sourceDirectory);
            return;
        }

        File[] files = sourceDir.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                try {
                    Thread.sleep(5000);
                    String fileName = file.getName();
                    if (startsWithAnyPrefix(fileName)) {
                        Thread.sleep(1000);
                        String destinationFileName = fileName;
                        String destDir = destinationDirectory;
                        Files.createDirectories(Path.of(destDir));
                        Files.move(file.toPath(), Path.of(destDir, destinationFileName),StandardCopyOption.REPLACE_EXISTING);
                        log.info("Se ha movido el archivo: {}" , file.getName());
                    }
                } catch (IOException | InterruptedException e) {
                    log.error("Error al mover el archivo " + file.getName() + ": " + e.getMessage());
                }
            }
        }

        log.info("Proceso de movimiento de archivos CSV completado.");
    }

    private boolean startsWithAnyPrefix(String fileName) {
        String[] prefixes = filePrefix.split(",");
        for (String prefix : prefixes) {
            if (fileName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Scheduled(cron = "${loaderservice.cron}")
    public void scheduleTask()
    {
        moveCSVFiles();
    }

    @PostConstruct
    public void init(){
        log.info("+++++++++++LOOK FOR NEW CSV FILES+++++++++++++");
    }

}
