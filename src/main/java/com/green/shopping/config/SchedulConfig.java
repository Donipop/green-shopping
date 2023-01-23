package com.green.shopping.config;


import com.green.shopping.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@EnableScheduling
public class SchedulConfig {
//    @Autowired
//    IndexService indexService;

    @Scheduled(cron = "0 0 9 * * *")
    public void  insertRandomItem() {
       indexService.insertRandomItem();
    }
}
