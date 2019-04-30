package com.lirong.servicehi.distributedlock.mylock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

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
    /**
     * TODO 疑问？ （因为是多个线程，没有影响，只不过ThreadLocalMap中的key是同一个对象而已（感觉这样不太容易理解））
     *
     * ThreadLocal<String> threadLocal = new ThreadLocal<>()
     * 例如：Thread1:map(threadLocal,requestId_1)
     *      Thread2:map(threadLocal,requestId_2)
     *
     *
     * ThreadLocal放入属性中，如果注入一次（单例），因为ThreadLocalMap的key是ThreadLocal，
     * 所以可能会有多个请求去set(value),使用放入最后要remove.防止内存溢出
     *
     * 可以把请求标识作为参数传入requestId
     */
    //可以这样使用
//    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public MyRedisDistributedLock(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean getLock(String key, String requestId) {
        return super.getLock(key, requestId);
    }

    @Override
    public boolean getLock(String key, long expireTime, String requestId) {
        return super.getLock(key, expireTime, requestId);
    }

    @Override
    public boolean getLock(String key, int retryTimes, String requestId) {
        return super.getLock(key, retryTimes, requestId);
    }

    @Override
    public boolean getLock(String key, long expireTime, int retryTimes, String requestId) {
        return super.getLock(key, expireTime, retryTimes, requestId);
    }

    @Override
    public boolean getLock(String key, long expireTime, long sleepTime, String requestId) {
        return super.getLock(key, expireTime, sleepTime, requestId);
    }

    @Override
    public boolean getLock(String key, int retryTimes, long sleepTime, String requestId) {
        return super.getLock(key, retryTimes, sleepTime, requestId);
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
    public boolean getLock(String key, long expireTime, int retryTimes, long sleepTime,String requestId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

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
    public Boolean releaseLock(String key,String requestId) {
        try {
            Boolean b = redisTemplate.execute((RedisConnection connection) -> connection.eval(UNLOCK_LUA.getBytes(),
                    ReturnType.INTEGER,
                    1,
                    key.getBytes(),
                    requestId.getBytes()
                    ));
            return b!=null && b;
        }catch (Exception e){
            LOGGER.error("release lock occured an exception", e);
            return false;
        }
    }
}
