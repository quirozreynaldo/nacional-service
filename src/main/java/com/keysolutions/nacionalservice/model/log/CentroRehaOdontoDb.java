package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.CentroRehaOdonto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CentroRehaOdontoDb extends CentroRehaOdonto {
    private String centroRehaOdontoId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
