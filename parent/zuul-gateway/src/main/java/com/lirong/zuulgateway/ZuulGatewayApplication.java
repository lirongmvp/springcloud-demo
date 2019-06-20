package com.lirong.zuulgateway;

import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ZuulGatewayApplication.class, args);
        Map<String, ZuulFilter> type = applicationContext.getBeansOfType(ZuulFilter.class);
        for (String s : type.keySet()) {
            System.out.println(s + "=" + type.get(s));
        }

    }


}
