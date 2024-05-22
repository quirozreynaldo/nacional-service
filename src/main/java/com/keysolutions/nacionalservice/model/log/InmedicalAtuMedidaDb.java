package com.keysolutions.nacionalservice.model.log;
import java.sql.Timestamp;

import com.keysolutions.nacionalservice.model.InmedicalAtuMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class InmedicalAtuMedidaDb extends InmedicalAtuMedida {
    private String inmediacalAtuId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
