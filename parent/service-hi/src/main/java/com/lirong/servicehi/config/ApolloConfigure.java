package com.lirong.servicehi.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: <br>
 * Description: <br>
 * Date:2019年04月12日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
@Configuration
@Slf4j
public class ApolloConfigure {

    @Value("${apollo.plugin.namespace:application}")
    private String namespace;

    @Bean
    public Config config() {
        return ConfigService.getConfig(namespace);
    }
}
