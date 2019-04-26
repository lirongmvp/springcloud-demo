package com.lirong.servicehi.distributedlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
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

    private static final Long RELEASE_LOCK_SUCCESS_RESULT = 1L;

    /**
     * @param redisTemplate 客户端
     * @param lockKey       锁
     * @param requestId     请求标识
     * @param expireTime    超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(RedisTemplate<String, String> redisTemplate, String lockKey, String requestId, int expireTime) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        //设置有效期，防止死锁
        Boolean aBoolean = ops.setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
        if (aBoolean != null && aBoolean) {
            return true;
        }
        return false;
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
        //Lua脚本，确保原子性
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        LOGGER.info("lockKey:{},requestId:{}",lockKey,requestId);
        return redisTemplate.execute((RedisCallback<Long>) connection-> {
            Object nativeConnection = connection.getNativeConnection();
            // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
            // 集群模式
            if (nativeConnection instanceof JedisCluster) {
                return (Long)((JedisCluster)nativeConnection).eval(script,
                        Collections.singletonList(lockKey),
                        Collections.singletonList(requestId)
                );
            }
            //单机模式
            else if (nativeConnection instanceof Jedis){
                return (Long)((Jedis)nativeConnection).eval(script,
                        Collections.singletonList(lockKey),
                        Collections.singletonList(requestId)
                );
            }
            return 0L;
        }).equals(RELEASE_LOCK_SUCCESS_RESULT);
    }

}
