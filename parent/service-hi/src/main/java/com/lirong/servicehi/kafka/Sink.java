package com.lirong.servicehi.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Title: Sink <br>
 * Description: Sink <br>
 * Date: 2019年06月03日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public interface Sink {

    String INPUT = "input";

    @Input(Sink.INPUT)
    SubscribableChannel input();
}
