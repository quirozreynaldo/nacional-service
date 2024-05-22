package com.keysolutions.nacionalservice.model.log;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ErrorLog {
    private int errorId;
    private String errorCode;
    private String errorDescription;
    private String remarks;
    private String errorDetail;
    private String status;
    private Timestamp recordDate;
}
