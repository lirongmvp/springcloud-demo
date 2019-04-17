package com.lirong.zuulgateway.config;

import com.lirong.zuulgateway.zuul.RewriteSendResponseFilter;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: AppConfig <br>
 * Description: AppConfig <br>
 * Date: 2019年04月16日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
@Configuration
public class AppConfig {

    @Bean
    public RewriteSendResponseFilter rewriteSendResponseFilter(ZuulProperties zuulProperties){
        return new RewriteSendResponseFilter(zuulProperties);
    }
}
