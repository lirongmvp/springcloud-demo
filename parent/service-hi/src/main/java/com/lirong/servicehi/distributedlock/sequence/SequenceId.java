package com.lirong.servicehi.distributedlock.sequence;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Title: SequenceId <br>
 * Description: SequenceId <br>
 * Date: 2019年04月29日, 分布式生产递增ID
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
//@Component
public class SequenceId {

    private RedisTemplate<String, String> redisTemplate;

    public static final String SEQUENCEID = "SequenceId";


    public SequenceId(RedisTemplate<String, String> redisTemplate) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.setIfAbsent(SEQUENCEID, "10000");
        this.redisTemplate = redisTemplate;
    }
    public void   setRedisId(String id){

    }
    /**
     * 得到数据库中的数字，然后加1
     *
     * @param key
     * @return
     */
    public Long getId(String key) {
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        return redisAtomicLong.getAndIncrement();
    }
}
