package com.lirong.servicehi.config;

import com.ctrip.framework.apollo.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Title: ApolloOperation <br>
 * Description: ApolloOperation <br>
 * Date: 2019年01月21日
 *
 * @author 王东旭
 * @version 1.0.0
 * @since jdk8
 */
@Component
@Slf4j
public class ApolloOp {

    /**
     * apollo 配置
     */
    private final Config apolloConfig;

    @Autowired
    public ApolloOp(Config config) {
        this.apolloConfig = config;
    }

    /**
     * 订阅
     * 
     * @param key
     *            key
     * @param consumer
     *            callback
     */
    public void subscribe(String key, Consumer<String> consumer) {
        apolloConfig.addChangeListener(changeEvent -> {
            Set<String> keys = changeEvent.changedKeys();
            log.info("apollo changeEvent: [{}]", keys);
            keys.forEach(s -> {
                if (s.equals(key)) {
                    try {
                        consumer.accept(changeEvent.getChange(key).getNewValue());
                    } catch (Exception e) {
                        log.error("apollo changeEvent [{}] change err.", s);
                    }
                }
            });
        });
    }
}
