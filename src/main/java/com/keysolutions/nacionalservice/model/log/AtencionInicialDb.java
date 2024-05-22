package com.keysolutions.nacionalservice.model.log;
import com.keysolutions.nacionalservice.model.AtencionInicial;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AtencionInicialDb extends AtencionInicial{
    private String atencionInicialId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
