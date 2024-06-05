package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.ProveedorFarma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class ProveedorFarmaDb extends ProveedorFarma {
    private String provFarId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;

    public ProveedorFarmaDb(String provFarId, String idProveedor, String idRubro, String idCiudad,
                            String producto, String servicio, String fechaDenuncia, String fechaContacto,
                            String idEjecutivo, String nombreAsegurado, String nombreSolicitante,
                            String telefono, String email, String poliza, String dignostico,
                            String intermediario, String uniqueId, String nombreArchivo,
                            String status, Timestamp recordDate) {
        super(idProveedor, idRubro, idCiudad, producto, servicio, fechaDenuncia, fechaContacto, idEjecutivo,
                nombreAsegurado, nombreSolicitante, telefono, email, poliza, dignostico, intermediario, uniqueId);
        this.provFarId = provFarId;
        this.nombreArchivo = nombreArchivo;
        this.status = status;
        this.recordDate = recordDate;
    }
}
