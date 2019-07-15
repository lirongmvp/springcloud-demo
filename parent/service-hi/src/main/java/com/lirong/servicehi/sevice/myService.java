package com.lirong.servicehi.sevice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * Title: myService <br>
 * Description: myService <br>
 * Date: 2019年04月24日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
//@Component
@ConditionalOnClass(name = "com.lirong.servicehi.dao.User")
public class myService implements CommandLineRunner, ApplicationRunner {

    private TestService testService;

    public myService(TestService testService) {
        System.out.println("注入============");
        this.testService = testService;
    }

    public void test() throws Exception {
        try {

            System.out.println("创建myservice");
            throw  new Exception("自定义异常");
        }catch (Exception e ){
            throw e;

        }

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner initialize ...");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner initialize ...");
    }

}
