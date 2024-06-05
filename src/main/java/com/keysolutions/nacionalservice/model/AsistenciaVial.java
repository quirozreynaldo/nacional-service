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
public class AsistenciaVial {
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
    private String uniqueId;
}
