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
public class Nsvs800 {
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
    private String uniqueId;
}
