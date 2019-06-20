package com.lirong.servicehi.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Title: Source <br>
 * Description: Source <br>
 * Date: 2019年06月03日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public interface Source {

    String OUTPUT = "output";//接口中的

    @Output(Source.OUTPUT)
    MessageChannel output();
}
