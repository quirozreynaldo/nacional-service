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
public class OrientacionMedTelef {
    private String idProceso;
    private String idRubro;
    private String idCiudad;
    private String edad;
    private String motivoLlamada;
    private String contactoCovid;
    private String diagnosticoPresuntivo;
    private String conducta;
    private String fechaContacto;
    private String medico;
    private String paciente;
    private String telefono;
    private String telefonoAlternativo;
    private String email;
    private String observaciones;
    private String aclaraciones;
}
