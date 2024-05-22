package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.OrientacionMedTelef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrientacionMedTelefDb extends OrientacionMedTelef {
    private String orienMedTelId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
