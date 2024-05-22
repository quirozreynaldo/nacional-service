package com.keysolutions.nacionalservice.model.log;

import java.sql.Timestamp;

import com.keysolutions.nacionalservice.model.InmedicalBancoGanadero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class InmedicalBancoGanaderoDb extends InmedicalBancoGanadero {
    private String inmediacalBancoId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
