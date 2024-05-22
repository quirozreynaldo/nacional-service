package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.VagonetaSegura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class VagonetaSeguraDb extends VagonetaSegura {
    private String vagonetaSeguraId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
