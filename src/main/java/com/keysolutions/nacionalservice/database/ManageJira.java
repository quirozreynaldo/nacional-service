package com.keysolutions.nacionalservice.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.keysolutions.nacionalservice.model.jira.ConfigJira;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ManageJira {
  @Autowired
    @Qualifier("digitalTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<ConfigJira> retrieveConfigJira(){
        String sqlSelect="SELECT config_jira_id, service_desk_id, request_type_id, issue_type_Id, code, status, "+
        " record_date FROM deaxs_config.ns_config_jira WHERE status !='I'";
        List<ConfigJira> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query(sqlSelect, (rs, row) ->
                    new ConfigJira(rs.getInt("config_jira_id"),
                            rs.getString("service_desk_id"),
                            rs.getString("request_type_id"),
                            rs.getString("issue_type_Id"),
                            rs.getString("code"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{});
        } catch (Exception ex) {
            log.error("retrieveConfigJira fail", ex);
        }
        return result;
    }
}
