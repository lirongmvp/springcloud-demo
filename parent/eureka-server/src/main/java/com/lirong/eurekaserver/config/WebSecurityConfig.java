package com.lirong.eurekaserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Title: WebSecurityConfig <br>
 * Description: WebSecurityConfig <br>
 * Date: 2019年04月17日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启认证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        //Spring Security 默认开启了所有 CSRF 攻击防御，需要禁用 /eureka 的防御
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
