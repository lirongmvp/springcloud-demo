package com.lirong.servicehi.kafka;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Title: Receive <br>
 * Description: Receive <br>
 * Date: 2019年06月04日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@EnableBinding(Sink.class)
public class Receive {

    @StreamListener(Sink.INPUT)
    public void messageSink(Object payload) {
        System.out.println("Received: " + payload);
    }

}
