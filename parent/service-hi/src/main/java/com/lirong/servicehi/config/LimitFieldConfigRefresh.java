package com.lirong.servicehi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Title:限制字段刷新 <br>
 * Description:限制字段刷新 <br>
 * Date:2019年02月22日 <br>
 *
 * @author yangpeng
 * @version 1.0.0
 * @since jdk8
 */
@Component
@Slf4j
public class LimitFieldConfigRefresh {

    private final ApolloOp apolloOp;
    private final LimitFieldConfig limitFieldConfig;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public LimitFieldConfigRefresh(LimitFieldConfig limitFieldConfig, ApolloOp apolloOp) {
        this.limitFieldConfig = limitFieldConfig;
        this.apolloOp = apolloOp;
    }

    @PostConstruct
    public void refresh() {
        apolloOp.subscribe("limitField-config", s -> {
            LimitFieldConfig limitFieldConfig;
            try {
                limitFieldConfig = objectMapper.readValue(s, LimitFieldConfig.class);
                BeanUtils.copyProperties(limitFieldConfig, this.limitFieldConfig);
                log.info("limitFieldConfig refresh success : {}", this.limitFieldConfig);
            } catch (Exception e) {
                log.info("limitFieldConfig refresh failed : {}", e);
            }
        });
    }
}
