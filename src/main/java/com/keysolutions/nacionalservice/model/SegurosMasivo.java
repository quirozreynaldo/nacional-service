package com.keysolutions.nacionalservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public  class SegurosMasivo {
    private String idProceso;
    private String idRubro;
    private String sponsor;
    private String idCiudad;
    private String producto;
    private String proveedorDeAsistencia;
    private String servicio;
    private String fechaContacto;
    private String fechaUsoServicio;
    private String idDocumento;
    private String nombreSolicitante;
    private String telefono;
    private String email;
    private String uniqueId;
}