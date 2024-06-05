package com.keysolutions.nacionalservice.model.log;
import com.keysolutions.nacionalservice.model.AtencionInicial;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class AtencionInicialDb extends AtencionInicial{
    private String atencionInicialId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;

    public AtencionInicialDb(String atencionInicialId, String idProceso, String idRubro, String idCiudad,
                             String idOperador, String idEjecutivo, String fechaContacto, String placa,
                             String nombreTitular, String nombreSolicitante, String telefono, String email,
                             String idPoliza, String uniqueId, String nombreArchivo, String status,
                             Timestamp recordDate) {
        super(idProceso, idRubro, idCiudad, idOperador, idEjecutivo, fechaContacto, placa,
                nombreTitular, nombreSolicitante, telefono, email, idPoliza, uniqueId);
        this.atencionInicialId = atencionInicialId;
        this.nombreArchivo = nombreArchivo;
        this.status = status;
        this.recordDate = recordDate;
    }
}
