package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.TallersE2e;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TallersE2eDb extends TallersE2e {
    private String talleresE2eId;
    private String idProceso;
    private String idRubro;
    private String idCiudad;
    private String idOperador;
    private String servicio;
    private String fechaContacto;
    private String periodo;
    private String nombreTitular;
    private String nombreSolicitante;
    private String telefono;
    private String marca;
    private String modelo;
    private String placa;
    private String color;
    private String idPoliza;
    private String intermediario;
    private String email;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
