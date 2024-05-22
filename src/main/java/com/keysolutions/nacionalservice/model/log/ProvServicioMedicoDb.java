package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.ProvServicioMedico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProvServicioMedicoDb extends ProvServicioMedico {
    private String provSerMedId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
