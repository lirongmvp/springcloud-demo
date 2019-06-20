package com.lirong.servicehi.controller;

import com.lirong.servicehi.dao.User;
import com.lirong.servicehi.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: KafkaController <br>
 * Description: KafkaController <br>
 * Date: 2019年06月04日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@RestController
public class KafkaController {
    @Autowired
    private KafkaSender kafkaSender;

    @GetMapping("/send")
    public String send() {
        User lirong = new User("1", "lirong");
        boolean send = kafkaSender.send(lirong.toString());
        System.out.println(send);
        return "success";
    }

}
