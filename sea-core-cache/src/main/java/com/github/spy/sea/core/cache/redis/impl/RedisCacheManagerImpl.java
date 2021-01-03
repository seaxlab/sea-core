package com.github.spy.sea.core.cache.redis.impl;

import com.alibaba.fastjson.JSON;
import com.github.spy.sea.core.cache.CacheConfig;
import com.github.spy.sea.core.cache.CacheManager;
import com.github.spy.sea.core.cache.redis.domain.RedisReentrantLock;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.util.SerializeUtil;
import com.github.spy.sea.core.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
@Slf4j
public class RedisCacheManagerImpl implements CacheManager {

    private CacheConfig cacheConfig;

    private JedisPool pool;

    @Override
    public void start(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;

        Properties props = UrlUtil.parse(cacheConfig.getUrl());

        String host = props.getProperty("host", "127.0.0.1");
        int port = Integer.valueOf(props.getProperty("port", "6379"));
        String password = props.getProperty("password");
        int database = Integer.valueOf(props.getProperty("database", "0"));


        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(cacheConfig.getMaxIdle());
        config.setMaxTotal(cacheConfig.getPoolSize());
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);

        pool = new JedisPool(config, host, port, cacheConfig.getTimeout(), password, database);
        log.info("redis init success.");
    }

    @Override
    public void stop() {
        if (pool != null) {
            pool.destroy();
        }
    }

    @Override
    public String getType() {
        return cacheConfig.getType();
    }

    @Override
    public CacheConfig getCacheConfig() {
        return cacheConfig;
    }

    @Override
    public Optional<Object> get(String key) {
        try (Jedis jedis = pool.getResource()) {
            return Optional.ofNullable(jedis.get(key));
        }
    }

    @Override
    public boolean add(String key, Object object) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key.getBytes(), SerializeUtil.serialize(object));
            return true;
        } catch (Exception e) {
            log.error("fail to add to cache.", e);
            return false;
        }
    }

    @Override
    public boolean add(String key, Object object, int expired) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key.getBytes(), SerializeUtil.serialize(object));
            jedis.expire(key.getBytes(), expired);
            return true;
        } catch (Exception e) {
            log.error("fail to add to cache.", e);
            return false;
        }
    }

    @Override
    public boolean del(String... keys) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.del(keys) > 0;
        } catch (Exception e) {
            log.error("fail to add to cache.", e);
            return false;
        }
    }

    @Override
    public boolean setJSON(String key, Object obj) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, JSON.toJSONString(obj));
            return true;
        } catch (Exception e) {
            log.error("fail to add to cache.", e);
            return false;
        }
    }

    @Override
    public <T> Optional<T> getJSON(String key, Class<T> clazz) {
        try (Jedis jedis = pool.getResource()) {
            byte[] value = jedis.get(key.getBytes());
            if (null == value) {
                return Optional.empty();
            }

            return Optional.of(JSON.parseObject(value, clazz));

        } catch (Exception e) {
            log.error("fail to add to cache.", e);
            return Optional.empty();
        }
    }

    @Override
    public Lock getLock(String lock) {
        try {
            return new RedisReentrantLock(pool, lock);
        } catch (Exception e) {
            log.error("fail to add to cache.", e);
            ExceptionHandler.publishMsg("fail to create redis reenterlock.");
        }
        return null;
    }
}
