package com.lirong.servicehi.sevice;

import com.lirong.servicehi.annotation.UserAnno;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    //AOP
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<TestService> testService = TestService.class;
        Method[] methods = testService.getMethods();
        for (Method method : methods) {
            UserAnno annotation = method.getAnnotation(UserAnno.class);
            if (annotation != null) {
                String name = annotation.name();
                method.setAccessible(true);
                method.invoke(testService.newInstance(), name);
            }
        }
    }
}
