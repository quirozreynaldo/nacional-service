package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.AtenAsisProdemVidaPlus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AtenAsisProdemVidaPlusDb extends AtenAsisProdemVidaPlus {
    private String atenAsisProId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
