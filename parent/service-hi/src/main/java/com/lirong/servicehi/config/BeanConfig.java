package com.lirong.servicehi.config;

import com.ctrip.framework.apollo.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: <br>
 * Description: <br>
 * Date:2019年02月22日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
@Configuration
@Slf4j
public class BeanConfig {

    @Autowired
    private Config config;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public LimitFieldConfig limitFieldConfig() {
        String property = config.getProperty("limitField-config", "{}");
        try {
            LimitFieldConfig limitFieldConfig = objectMapper.readValue(property, LimitFieldConfig.class);
            log.info("limitField-config:{}", property);
            return limitFieldConfig;
        } catch (Exception e) {
            log.error("limitField-config  err:{} " + property, e);
            return new LimitFieldConfig();
        }
    }

}
