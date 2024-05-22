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
public class Nsvs800Db {
    private String nSvs800Id;
    private String idProceso;
    private String idRubro;
    private String idCiudad;
    private String idOperador;
    private String servicio;
    private String fechaContacto;
    private String idObjeto;
    private String nombreTitular;
    private String nombreSolicitante;
    private String telefono;
    private String email;
    private String idPoliza;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
