package com.keysolutions.nacionalservice.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.keysolutions.nacionalservice.model.db.ConfigValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ManageSurvey {
    @Autowired
    @Qualifier("digitalTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<ConfigValue> retrieveConfigValue(String type){
        String sqlSelect="SELECT config_value_id, `type`, code, sm_value, `timestamp` "+
                         " FROM deaxs_config.ns_config_value "+
                         " WHERE  `type` = ?";
        List<ConfigValue> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new ConfigValue(rs.getInt("config_value_id"),
                            rs.getString("type"),
                            rs.getString("code"),
                            rs.getString("sm_value"),
                            rs.getTimestamp("timestamp")
                    ), new String[]{type});
        } catch (Exception ex) {
            log.error("retrieveConfigValue fail", ex);
        }
        return result;
    }

}
