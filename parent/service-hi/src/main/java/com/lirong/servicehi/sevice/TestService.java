package com.lirong.servicehi.sevice;

import com.lirong.servicehi.annotation.UserAnno;
import org.springframework.stereotype.Component;

/**
 * Title: TestService <br>
 * Description: TestService <br>
 * Date: 2019年04月29日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@Component
public class TestService {

    @UserAnno(name = "lirong")
    public void t(String name) {
        System.out.println(name);
    }

}
