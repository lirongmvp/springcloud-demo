package com.lirong.servicehi.time;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Title: myScheduled <br>
 * Description: myScheduled <br>
 * Date: 2019年07月01日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@Component
public class MyScheduled {


    @Scheduled(cron = "0/2 * * * * ? ")
    public void test(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("睡5秒....");
            }
        };
        new Thread(runnable).start();
    }
}
