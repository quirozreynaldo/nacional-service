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
public class AsistenciaMedDomiAmbu {
    private String idProceso;
    private String idRubro;
    private String idCiudad;
    private String edad;
    private String motivoLlamada;
    private String producto;
    private String servicio;
    private String desenlace;
    private String fechaContacto;
    private String fechaArribo;
    private String incidencia;
    private String unidadAsignada;
    private String paciente;
    private String telefono;
    private String telefonoAlternativo;
    private String direccionAtencion;
    private String aclaraciones;
    private String uniqueId;
}
