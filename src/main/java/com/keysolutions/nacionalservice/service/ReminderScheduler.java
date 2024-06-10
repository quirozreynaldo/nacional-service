package com.keysolutions.nacionalservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ReminderScheduler {
@Autowired
private ReminderService reminderService;

    @Value("${loaderservice.reminder}")
    private String reminder;

    @Scheduled(cron = "${loaderservice.reminder}")
    public void scheduleTask()
    {
        reminderService.executeReminder();
    }

    @PostConstruct
    public void init(){
        log.info("+++++++++++REMINDER CRON +++++++++++++");
    }
    public void setReminder(String reminder) {
        this.reminder = reminder;
    }
}
