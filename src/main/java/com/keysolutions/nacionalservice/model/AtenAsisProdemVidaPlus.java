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
public class AtenAsisProdemVidaPlus {
    private String idProceso;
    private String proveedor;
    private String idRubro;
    private String idCiudad;
    private String idOperador;
    private String servicio;
    private String fechaContacto;
    private String nombreTitular;
    private String telefono;
    private String idDocumento;
    private String especialidad;
}
