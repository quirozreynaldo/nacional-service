package com.keysolutions.nacionalservice.model.db;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConfigValue {
    private Integer nsConfigValueId;
    private String type;
    private String code;
    private String smValue;
    private Timestamp timestamp;
}

