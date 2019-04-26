package com.lirong.servicehi.distributedlock.mylock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Title: MyRedisDistributedLock <br>
 * Description: MyRedisDistributedLock <br>
 * Date: 2019年04月25日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public class MyRedisDistributedLock extends AbstractMyDistributedLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRedisDistributedLock.class);

    /**
     * lua脚本  意思：如果有指定的key且值相等，则删除锁返回1，否则返回0
     */
    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 解锁成功
     */
    private static final Long RELEASE_LOCK_SUCCESS_RESULT =1L;
    private RedisTemplate<String, String> redisTemplate;
    //ThreadLocal是一个Map，key:当前线程，value是放入线程中的值，ThreadLocal对象并不代表一个线程
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public MyRedisDistributedLock(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean getLock(String key) {
        return super.getLock(key);
    }

    @Override
    public boolean getLock(String key, long expireTime) {
        return super.getLock(key, expireTime);
    }

    @Override
    public boolean getLock(String key, int retryTimes) {
        return super.getLock(key, retryTimes);
    }

    @Override
    public boolean getLock(String key, long expireTime, int retryTimes) {
        return super.getLock(key, expireTime, retryTimes);
    }

    @Override
    public boolean getLock(String key, long expireTime, long sleepTime) {
        return super.getLock(key, expireTime, sleepTime);
    }

    @Override
    public boolean getLock(String key, int retryTimes, long sleepTime) {
        return super.getLock(key, retryTimes, sleepTime);
    }

    /**
     * 获取锁
     *
     * @param key        锁
     * @param expireTime 过期时间
     * @param retryTimes 有几次机会
     * @param sleepTime  睡眠时间
     * @return true:拿到锁 false:重试以后依然没有拿到锁
     */
    @Override
    public boolean getLock(String key, long expireTime, int retryTimes, long sleepTime) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String requestId = UUID.randomUUID().toString();
        threadLocal.set(requestId);
        while (retryTimes-- > 0) {
            LOGGER.info("retryTimes:{}",retryTimes);
            Boolean aBoolean = ops.setIfAbsent(key, requestId, expireTime, TimeUnit.MILLISECONDS);
            if(aBoolean != null && aBoolean){
                return true;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        try {
            String requestId = threadLocal.get();
            return redisTemplate.execute((RedisCallback<Long>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA,
                            Collections.singletonList(key),
                            Collections.singletonList(requestId)
                    );
                }
                //单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA,
                            Collections.singletonList(key),
                            Collections.singletonList(requestId)
                    );
                }
                return 0L;
            }).equals(RELEASE_LOCK_SUCCESS_RESULT);
        }catch (Exception e){
            LOGGER.error("release lock occured an exception", e);
            return false;
        }finally {
            // 清除掉ThreadLocal中的数据，避免内存溢出
            threadLocal.remove();
        }
    }
}
