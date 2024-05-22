package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.ProveedorFarma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProveedorFarmaDb extends ProveedorFarma {
    private String provFarId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
