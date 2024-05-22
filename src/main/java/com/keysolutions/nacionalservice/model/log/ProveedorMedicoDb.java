package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.ProveedorMedico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProveedorMedicoDb extends ProveedorMedico {
    private String provMedId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
