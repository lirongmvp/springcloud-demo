package com.lirong.servicehi.distributedlock.mylock;

/**
 * Title: DistributedLock <br>
 * Description: DistributedLock <br>
 * Date: 2019年04月25日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public interface MyDistributedLock {
    /**
     * 锁过期时间
     */
    long TIMEOUT_MILLIS = 30000;
    /**
     * 获取机会 默认3
     */
    int RETRY_TIMES = 3;
    /**
     * 睡眠时间，用于重试
     */
    long SLEEP_MILLIS = 500;

    boolean getLock(String key);
    boolean getLock(String key,long expireTime);
    boolean getLock(String key,int retryTimes);
    boolean getLock(String key,long expireTime, int retryTimes);
    boolean getLock(String key,long expireTime, long sleepTime);
    boolean getLock(String key,int retryTimes, long sleepTime);
    boolean getLock(String key,long expireTime, int retryTimes,long sleepTime);
    boolean releaseLock(String key);

}
