package com.lirong.servicehi.sevice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * Title: myService <br>
 * Description: myService <br>
 * Date: 2019年04月24日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
@Component
@ConditionalOnClass(name = "com.lirong.servicehi.dao.User")
public class myService {

    public void test(){
        System.out.println("创建myservice");
    }
}
