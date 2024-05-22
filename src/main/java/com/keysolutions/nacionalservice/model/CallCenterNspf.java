package com.keysolutions.nacionalservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CallCenterNspf {
    private String idProceso;
    private String idRubro;
    private String idCiudad;
    private String idSiniestro;
    private String idOperador;
    private String fechaContacto;
    private String idCliente;

}
