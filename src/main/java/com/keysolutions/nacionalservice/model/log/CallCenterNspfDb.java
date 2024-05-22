package com.keysolutions.nacionalservice.model.log;

import com.keysolutions.nacionalservice.model.CallCenterNspf;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CallCenterNspfDb extends CallCenterNspf {
    private String callCenterNspfId;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}
