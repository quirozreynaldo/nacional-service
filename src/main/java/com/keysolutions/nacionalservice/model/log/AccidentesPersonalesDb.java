package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.AccidentesPersonales;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AccidentesPersonalesDb extends AccidentesPersonales {
    private String accidentePersonalId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;

}
