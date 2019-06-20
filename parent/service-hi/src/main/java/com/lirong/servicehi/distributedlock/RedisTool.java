package com.lirong.servicehi.distributedlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Title: RedisTool <br>
 * Description: RedisTool <br>
 * Date: 2019年04月25日
 * 分布式锁 Redis
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public class RedisTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTool.class);

    //Lua脚本，确保原子性
    private static final String UNLOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * @param redisTemplate 客户端
     * @param lockKey       锁
     * @param requestId     请求标识
     * @param expireTime    超期时间，秒
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(RedisTemplate<String, String> redisTemplate, String lockKey, String requestId, int expireTime) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        //设置有效期，防止死锁
        Boolean aBoolean = ops.setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
        return aBoolean != null && aBoolean;
    }

    /**
     * 释放分布式锁
     *
     * @param redisTemplate Redis客户端
     * @param lockKey       锁
     * @param requestId     请求标识
     * @return 是否释放成功
     * 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
     */
    public static boolean releaseDistributedLock(RedisTemplate<String, String> redisTemplate, String lockKey, String requestId) {
        Boolean b = redisTemplate.execute((RedisConnection connection) -> connection.eval(UNLOCK.getBytes(),
                ReturnType.INTEGER,
                1,
                lockKey.getBytes(),
                requestId.getBytes()
        ));
        return b != null && b;
    }

}
