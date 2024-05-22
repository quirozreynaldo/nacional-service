package com.keysolutions.nacionalservice.model.log;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AsistenciaVialDb {
    private String asistenciaVialId;
    private String idProceso;
    private String idRubro;
    private String idCiudad;
    private String idOperador;
    private String idSiniestro;
    private String fechaContacto;
    private String nombreTitular;
    private String nombreSolicitante;
    private String telefono;
    private String telefonoAlternativo;
    private String placa;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
