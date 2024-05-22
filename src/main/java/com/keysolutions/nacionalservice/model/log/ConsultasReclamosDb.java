package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.ConsultasReclamos;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ConsultasReclamosDb extends ConsultasReclamos{
    private String consultaReclamoId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
