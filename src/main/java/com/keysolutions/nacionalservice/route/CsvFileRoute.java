package com.keysolutions.nacionalservice.route;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.keysolutions.nacionalservice.processor.CsvProcessor;

@Component
public class CsvFileRoute extends RouteBuilder {
    private String to;
    private String from;
    private CsvProcessor csvProcessor;

    @Override
    public void configure() throws Exception {
        from(from + "?charset=ISO-8859-1&?move=../out/${date:now:yyyyMMddHHmmss}-${file:name}&moveFailed=../failed/${date:now:yyyyMMddHHmmss}-${file:name}")
                .process(exchange -> {
                    String fileContent = exchange.getIn().getBody(String.class);
                    char delimiter = determineDelimiter(fileContent);
                    exchange.setProperty("DELIMITADOR", delimiter);
                    
                })
                .log("  ${headers.CamelFileName} ")
                .bean(csvProcessor, "init")
                .process(exchange -> {
                    int totalRows = countTotalLines(exchange);
                    //exchange.setProperty("TOTAL_ROWS", totalRows);
                    exchange.getIn().setHeader("totalRows", totalRows);
                    System.out.println("****************TOTAL_ROWS*****************"+totalRows);
                })
                .split(body().tokenize("\n", 1, false)).streaming()
                .choice()
                .when().simple("${exchangeProperty.DELIMITADOR} == ','")
                .unmarshal(new CsvDataFormat().setDelimiter(','))
                .when().simple("${exchangeProperty.DELIMITADOR} == ';'")
                .unmarshal(new CsvDataFormat().setDelimiter(';'))
                .end()
                .choice()
                .when(header("CamelFileName").startsWith("017_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "asistenciaVialProcess")
                .when(header("CamelFileName").startsWith("021_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "nSvs800Process")
                .when(header("CamelFileName").startsWith("012_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "consultasReclamosProcess")
                .when(header("CamelFileName").startsWith("015_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "atencionInicialProcess")
                .when(header("CamelFileName").startsWith("020_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "accidentePersonalProcess")
                .when(header("CamelFileName").startsWith("014_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "callCenterNfspfProcess")
                .when(header("CamelFileName").startsWith("11B_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "inmedicalAtuMedidaProcess")
                .when(header("CamelFileName").startsWith("11A_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "inmedicalBancoGanaderoProcess")
                .when(header("CamelFileName").startsWith("002_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "talleresE2eProcess")
                .when(header("CamelFileName").startsWith("013_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "centroRehaOndotoProcess")
                .when(header("CamelFileName").startsWith("003_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "provServMedicoProcess")
                .when(header("CamelFileName").startsWith("004_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "proveedorMedicoProcess")
                .when(header("CamelFileName").startsWith("005_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "proveedorFarmaProcess")
                .when(header("CamelFileName").startsWith("019_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "asistenciaMedDomiProcess")
                .when(header("CamelFileName").startsWith("018_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "asistenciaMedDomiAmbuProcess")
                .when(header("CamelFileName").startsWith("010_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "orientaMedTelefProcess")
                .when(header("CamelFileName").startsWith("016_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "atenAsisProdemVidaPlusProcess")
                .when(header("CamelFileName").startsWith("001_"))
                    .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                    .setHeader("totalRows", simple("${headers.totalRows}"))
                    .bean(csvProcessor, "vagonetaSeguraProcess")
                .when(header("CamelFileName").startsWith("022_"))
                .setHeader("additionalParam", simple("${headers.CamelFileName}"))
                .setHeader("totalRows", simple("${headers.totalRows}"))
                .bean(csvProcessor, "segurosMasivoProcess")
                .otherwise()
                    .log("quizo procesar un archivo con el prefijo incorrecto, se movio al directorio failed")
                    .to("file:failed?fileName=${date:now:yyyyMMddHHmmss}-${file:name}")
                .end();
    }

    @Value("${camel.ride.app.output.directory}")
    public void setTo(String to) {
        this.to = to;
    }

    @Value("${camel.ride.app.input.directory}")
    public void setFrom(String from) {
        this.from = from;
    }

    @Autowired
    public void setCsvProcessor(CsvProcessor csvProcessor) {
        this.csvProcessor = csvProcessor;
    }

    // Método para determinar dinámicamente el delimitador basado en el contenido
    // del archivo
    private char determineDelimiter(String fileContent) {
        // Lógica para determinar el delimitador basada en el contenido del archivo, por
        // ejemplo:
        if (fileContent.contains(",")) {
            return ',';
        } else if (fileContent.contains(";")) {
            return ';';
        }
        // Valor predeterminado si no se encuentra un delimitador específico en el
        // contenido del archivo
        return ',';
    }

    // Método para contar el número total de líneas del archivo
    private int countTotalLines(Exchange exchange) {
        String fileContent = exchange.getIn().getBody(String.class);
        return fileContent.split("\n").length;
    }
}