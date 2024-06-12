package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.SegurosMasivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class SegurosMasivoDb extends SegurosMasivo {
    private String segurosMasivoId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;

    public SegurosMasivoDb(String idProceso, String idRubro, String sponsor, String idCiudad, String producto,
                           String proveedorDeAsistencia, String servicio, String fechaContacto, String fechaUsoServicio,
                           String idDocumento, String nombreSolicitante, String telefono, String email, String uniqueId,
                           String segurosMasivoId, String nombreArchivo, String status, Timestamp recordDate) {
        super(idProceso, idRubro, sponsor, idCiudad, producto, proveedorDeAsistencia, servicio, fechaContacto, fechaUsoServicio,
                idDocumento, nombreSolicitante, telefono, email, uniqueId);
        this.segurosMasivoId = segurosMasivoId;
        this.nombreArchivo = nombreArchivo;
        this.status = status;
        this.recordDate = recordDate;
    }
}
