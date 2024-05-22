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
public class TallersE2e {
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
}
