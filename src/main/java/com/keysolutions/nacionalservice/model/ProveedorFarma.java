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
public class ProveedorFarma {
    private String idProveedor;
    private String idRubro;
    private String idCiudad;
    private String producto;
    private String servicio;
    private String fechaDenuncia;
    private String fechaContacto;
    private String idEjecutivo;
    private String nombreAsegurado;
    private String nombreSolicitante;
    private String telefono;
    private String email;
    private String poliza;
    private String dignostico;
    private String intermediario;
}
