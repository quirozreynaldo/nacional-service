package com.keysolutions.nacionalservice.database;

import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keysolutions.nacionalservice.model.jira.ConfigJira;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ManageJiraInMemory {
    @Autowired
    private ManageJira manageJira;
    private Hashtable<String, ConfigJira> jiraConfigHash;

    @PostConstruct
    public String loadConfigJira() {
        jiraConfigHash = new Hashtable<>();
        List<ConfigJira> jiraConfigList = manageJira.retrieveConfigJira();
        for (ConfigJira configJira : jiraConfigList) {
            jiraConfigHash.put(configJira.getCode(), configJira);
        }
        log.info("{}", jiraConfigHash);
        return "OK";
    }

    public ConfigJira getServiceDeskInfo(String code){
        return jiraConfigHash.get(code);
    }
}