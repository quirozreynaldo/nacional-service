package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.CentroRehaOdonto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class CentroRehaOdontoDb extends CentroRehaOdonto {
    private String centroRehaOdontoId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;

    public CentroRehaOdontoDb(String centroRehaOdontoId, String idProceso, String idRubro, String idCiudad,
                              String idOperador, String centroMedico, String fechaContacto, String fechaSalida,
                              String paciente, String plan, String telefono, String email, String telefonoAdicional,
                              String uniqueId, String nombreArchivo, String status, Timestamp recordDate) {
        super(idProceso, idRubro, idCiudad, idOperador, centroMedico, fechaContacto, fechaSalida, paciente, plan,
                telefono, email, telefonoAdicional, uniqueId);
        this.centroRehaOdontoId = centroRehaOdontoId;
        this.nombreArchivo = nombreArchivo;
        this.status = status;
        this.recordDate = recordDate;
    }
}
