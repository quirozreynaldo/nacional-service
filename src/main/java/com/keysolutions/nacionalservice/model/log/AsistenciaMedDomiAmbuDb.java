package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.AsistenciaMedDomiAmbu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AsistenciaMedDomiAmbuDb extends AsistenciaMedDomiAmbu {
    private String asisMedDoAmbuId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
