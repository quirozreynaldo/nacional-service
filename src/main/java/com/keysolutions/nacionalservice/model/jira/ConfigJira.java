package com.keysolutions.nacionalservice.model.jira;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigJira {
    private int configJiraId;
    private String serviceDeskId;
    private String requestTypeId;
    private String issueTypeId;
    private String code;
    private String status;
    private Timestamp recordDate;
}
