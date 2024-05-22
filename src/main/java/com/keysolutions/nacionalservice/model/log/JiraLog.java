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
public class JiraLog {
    private int jiraLogId;
    private String issueId;
    private String issueKey;
    private String requestTypeId;
    private String serviceDeskId;
    private String serviceComplain;
    private String nombreArchivo;
    private String status;
    private Timestamp recordDate;
}