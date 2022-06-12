package com.github.seaxlab.core.cache.redis.domain;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * redis reentrant lock, but it not support reentrant.
 * plz use sea-ulock instead.
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
@Slf4j
@Deprecated
public class RedisReentrantLock implements Lock {
    //TODO 替换成redisson

    private JedisPool pool;
    private String lockKey;
    private static final long DEFAULT_LOCK_TIMEOUT = 30 * 1000;


    public RedisReentrantLock(JedisPool pool, String lockKey) {
        this.pool = pool;
        this.lockKey = lockKey;
    }

    @Override
    public void lock() {
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("interrupted exception.", e);
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long expired = TimeUnit.MILLISECONDS.convert(time, unit);

        if (expired == 0) {
            expired = DEFAULT_LOCK_TIMEOUT;
        }

        try (Jedis jedis = pool.getResource()) {
            long value = System.currentTimeMillis() + expired + 1;
            // 1. 通过SETNX试图获取一个lock
            long acquired = jedis.setnx(lockKey, String.valueOf(value));

            //SETNX成功，则成功获取一个锁
            if (acquired == 1) {
                return true;
            }

            //SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
            long oldValue = Long.parseLong(jedis.get(lockKey));

            //超时
            if (oldValue < System.currentTimeMillis()) {
                String getValue = jedis.getSet(lockKey, String.valueOf(value));
                // 获取锁成功
                if (Long.valueOf(getValue) == oldValue) {
                    return true;
                }
            }
            //未超时，则直接返回失败
        } catch (Exception e) {
            log.error("acquireLock error ==> ", e);
        }

        return false;
    }

    @Override
    public void unlock() {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(lockKey);
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
