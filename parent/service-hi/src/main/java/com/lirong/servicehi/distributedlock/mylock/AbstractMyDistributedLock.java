package com.lirong.servicehi.distributedlock.mylock;

/**
 * Title: AbstractMy <br>
 * Description: AbstractMy <br>
 * Date: 2019年04月25日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
public abstract class AbstractMyDistributedLock implements MyDistributedLock {

    @Override
    public boolean getLock(String key) {
        return getLock(key,TIMEOUT_MILLIS,RETRY_TIMES,SLEEP_MILLIS);
    }

    @Override
    public boolean getLock(String key, long expireTime) {
        return getLock(key,expireTime,RETRY_TIMES,SLEEP_MILLIS);
    }

    @Override
    public boolean getLock(String key, int retryTimes) {
        return getLock(key,TIMEOUT_MILLIS,retryTimes,SLEEP_MILLIS);
    }

    @Override
    public boolean getLock(String key, long expireTime, int retryTimes) {
        return getLock(key,expireTime,retryTimes,SLEEP_MILLIS);
    }

    @Override
    public boolean getLock(String key, long expireTime, long sleepTime) {
        return getLock(key,expireTime,RETRY_TIMES,sleepTime);
    }

    @Override
    public boolean getLock(String key, int retryTimes, long sleepTime) {
        return getLock(key,TIMEOUT_MILLIS,retryTimes,sleepTime);
    }


}
