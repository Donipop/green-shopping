package com.green.shopping.config;


import com.green.shopping.service.IndexService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulConfig {
//    @Autowired
    private final IndexService indexService;

    public SchedulConfig(IndexService indexService) {
        this.indexService = indexService;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void  insertRandomItem() {
       indexService.insertRandomItem();
    }
}
