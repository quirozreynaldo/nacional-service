package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.ConsultasReclamos;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class ConsultasReclamosDb extends ConsultasReclamos{
    private String consultaReclamoId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;

    public ConsultasReclamosDb(String consultaReclamoId, String idProceso, String idRubro, String idCiudad,
                               String idOperador, String servicio, String fechaContacto, String fechaCierreFreshDesk,
                               String idObjeto, String nombreTitular, String nombreSolicitante, String telefono,
                               String email, String idPoliza, String asunto,String uniqueId, String nombreArchivo, String status,
                               Timestamp recordDate) {
        super(idProceso, idRubro, idCiudad, idOperador, servicio, fechaContacto, fechaCierreFreshDesk,
                idObjeto, nombreTitular, nombreSolicitante, telefono, email, idPoliza, asunto, uniqueId);
        this.consultaReclamoId = consultaReclamoId;
        this.nombreArchivo = nombreArchivo;
        this.status = status;
        this.recordDate = recordDate;
        }
}
