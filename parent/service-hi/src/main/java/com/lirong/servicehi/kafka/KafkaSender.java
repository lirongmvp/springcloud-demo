package com.lirong.servicehi.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Title: KafkaSender <br>
 * Description: KafkaSender <br>
 * Date: 2019年06月04日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@EnableBinding(Source.class)
public class KafkaSender {

    @Autowired
    private Source source;

    public boolean send(String conveyMsg) {
        System.out.println("打印："+conveyMsg);
        return source.output().send(MessageBuilder.withPayload(conveyMsg).build());
    }

}
