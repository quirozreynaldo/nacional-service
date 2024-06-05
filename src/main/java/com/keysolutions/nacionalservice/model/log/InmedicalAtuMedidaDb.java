package com.keysolutions.nacionalservice.model.log;
import java.sql.Timestamp;

import com.keysolutions.nacionalservice.model.InmedicalAtuMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class InmedicalAtuMedidaDb extends InmedicalAtuMedida {
    private String inmediacalAtuId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;

    public InmedicalAtuMedidaDb(String inmediacalAtuId, String idProceso, String idRubro, String idCiudad,
                                String proveedor, String idCentro, String servicio, String fechaContacto,
                                String idDocumento, String nombreTitular, String nombreSolicitante,
                                String telefono, String email, String uniqueId, String nombreArchivo,
                                String status, Timestamp recordDate) {
        super(idProceso, idRubro, idCiudad, proveedor, idCentro, servicio, fechaContacto,
                idDocumento, nombreTitular, nombreSolicitante, telefono, email, uniqueId);
        this.inmediacalAtuId = inmediacalAtuId;
        this.nombreArchivo = nombreArchivo;
        this.status = status;
        this.recordDate = recordDate;
    }
}
