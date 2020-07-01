package com.github.spy.sea.core.cache.redis;

import com.alibaba.fastjson.JSON;
import com.github.spy.sea.core.util.SerializeUtil;
import com.github.spy.sea.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.Optional;
import java.util.Set;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
public class RedisManager {
    private static Logger logger = LoggerFactory.getLogger(RedisManager.class);

    // Redis服务器IP
    private String host;
    // Redis的端口号
    private int port;
    // 访问密码
    private String password;
    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private int MAX_IDLE = 100;
    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private int TIME_OUT = 5000;

    private int MAX_TOTAL = 300;
    //redis数据库
    private int database;


    private JedisPool pool;

    public static final String NO_JEDIS_INSTANCE = "there is no available jedis instance from pool";


    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(MAX_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);

        pool = new JedisPool(config, host, port, TIME_OUT, password, database);
        logger.info("redis init success.");
    }

    public void destroy() {
        pool.destroy();
    }

    public Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return null;
            }

            byte[] value = jedis.get(key.getBytes());
            if (null == value) {
                return null;
            }

            return SerializeUtil.unserialize(value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public String set(String key, Object object) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return null;
            }
            return jedis.set(key.getBytes(), SerializeUtil.serialize(object));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    public String set(String key, Object object, int expried) {
        String ret = null;

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return null;
            }

            ret = jedis.set(key.getBytes(), SerializeUtil.serialize(object));
            jedis.expire(key.getBytes(), expried);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return ret;
    }


    /**
     * 将对象序列化成JSON字符串
     *
     * @param key
     * @param obj
     */
    public void setJSON(String key, Object obj) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return;
            }
            jedis.set(key, JSON.toJSONString(obj));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取JSON格式的字符串，同时转成对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Optional<T> getJSON(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return Optional.empty();
            }

            byte[] value = jedis.get(key.getBytes());
            if (null == value) {
                return Optional.empty();
            }

            return Optional.of(JSON.parseObject(value, clazz));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return false;
            }

            return jedis.del(key.getBytes()) > 0;
        } finally {
            if (jedis != null) {

                jedis.close();
            }
        }
    }


    /**
     * 获取锁
     *
     * @param lock
     * @param expired (ms)
     * @return
     */
    public synchronized boolean acquireLock(String lock, int expired) {
        if (expired == 0) {
            expired = 3000;
        }


        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return false;
            }

            long value = System.currentTimeMillis() + expired + 1;
            // 1. 通过SETNX试图获取一个lock
            long acquired = jedis.setnx(lock, String.valueOf(value));

            //SETNX成功，则成功获取一个锁
            if (acquired == 1) {
                return true;
            }

            //SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
            long oldValue = Long.parseLong(jedis.get(lock));

            //超时
            if (oldValue < System.currentTimeMillis()) {
                String getValue = jedis.getSet(lock, String.valueOf(value));
                // 获取锁成功
                if (Long.valueOf(getValue) == oldValue) {
                    return true;
                }
            }


            //未超时，则直接返回失败
        } catch (Exception e) {
            logger.error("acquireLock error ==> ", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;

    }

    /**
     * 释放锁
     */
    public synchronized void releaseLock(String lock) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return;
            }

            jedis.del(lock);

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * keys
     *
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(String pattern) {
        Set<byte[]> keys = null;

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return keys;
            }

            keys = jedis.keys(pattern.getBytes());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return keys;
    }

    public void flushDB() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return;
            }

            jedis.flushDB();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    public long dbSize() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return 0;
            }

            return jedis.dbSize();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * publish
     *
     * @param channel
     * @param message
     * @return
     */
    public Long publish(String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return -1L;
            }
            return jedis.publish(channel, message);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * subcribe
     *
     * @param jedisPubSub
     * @param channels
     */
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return;
            }
            jedis.subscribe(jedisPubSub, channels);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * left push
     *
     * @param key
     * @param msg
     * @return
     */
    public Long lpush(String key, String msg) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return -2L;
            }
            return jedis.lpush(key, msg);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * right pop
     *
     * @param key
     * @return
     */
    public String rpop(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return StringUtil.EMPTY;
            }
            return jedis.rpop(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * list length.
     *
     * @param key
     * @return
     */
    public long listLength(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                logger.error(NO_JEDIS_INSTANCE);
                return 0;
            }
            return jedis.llen(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

}
