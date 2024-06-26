package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.AsistenciaMedDomi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AsistenciaMedDomiDb extends AsistenciaMedDomi {
    private String asisMedDoId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
