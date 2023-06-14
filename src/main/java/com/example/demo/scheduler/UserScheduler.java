package com.example.demo.scheduler;


import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service

public class UserScheduler {

    private final UserService userService;

    public UserScheduler(UserService userService) {
        this.userService = userService;
    }

    //run every minute, if zone not set, user UTC timezone
    @Scheduled(cron = "0 * * * * * ", zone = "Asia/Bangkok")//second minute hour day month year > run at second 0 of every minute of every ...
    public void testEveryMinute() {
        log.info("Test every minute");
    }
    //run everyday at 00.00
    @Scheduled(cron= "0 0 0 * * *", zone = "Asia/Bangkok")
    public void testEveryMidnight(){
        log.info("Test midnight");
    }

    //run everyday 9.00  cron = 0 0 9 * * *
}
