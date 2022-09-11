package com.github.seaxlab.core.cache.redis.jedis;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.cache.redis.RedisConst;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis op manager
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
public class JedisManager {

    private static Logger log = LoggerFactory.getLogger(JedisManager.class);

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

    private static final Map<String, String> scriptMap = new ConcurrentHashMap<>();

    public JedisManager() {

    }

    /**
     * constructor
     *
     * @param host
     * @param port
     * @param password
     * @param database
     */
    public JedisManager(String host, int port, String password, int database) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.database = database;
    }


    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(MAX_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);

        pool = new JedisPool(config, host, port, TIME_OUT, password, database);
        log.info("redis init success.");
    }

    public void destroy() {
        pool.destroy();
    }


    /**
     * get jedis <br/>
     * <p>
     * IMPORTANT: if you use this method, you must close jedis instance.
     * <pre>
     *      Jedis jedis = redisManager.getJedis();
     *      // your biz code.
     *      jedis.close();
     * </pre>
     *
     * @return
     */
    public Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 获取redis相关信息
     *
     * @param section
     * @return
     */
    public Map<String, String> info(String section) {
        try (Jedis jedis = pool.getResource()) {
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return MapUtil.empty();
            }
            //# Stats\r\ntotal_connections_received:4\r\ntotal_commands_processed:8...
            String content = jedis.info(section);
            if (StringUtil.isNotEmpty(content)) {
                String[] kv = content.split("\r\n");
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < kv.length; i++) {
                    String item = kv[i];
                    String[] keyValue = item.split(":");
                    if (keyValue.length == 2) {
                        map.put(keyValue[0], keyValue[1]);
                    }
                }

                return map;
            }
        }
        return MapUtil.empty();
    }

    /**
     * get key
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return null;
            }

            byte[] value = jedis.get(key.getBytes());
            if (null == value) {
                return null;
            }

            return SerializeUtil.deserialize(value);
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
                log.error(NO_JEDIS_INSTANCE);
                return null;
            }
            return jedis.set(key.getBytes(), SerializeUtil.serialize(object));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    public String set(String key, Object object, int expired) {
        String ret = null;

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return null;
            }

            ret = jedis.set(key.getBytes(), SerializeUtil.serialize(object));
            jedis.expire(key.getBytes(), expired);
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
                log.error(NO_JEDIS_INSTANCE);
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
                log.error(NO_JEDIS_INSTANCE);
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


    /**
     * delete one key
     *
     * @param key
     * @return
     */
    public boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
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
     * delete more
     *
     * @param keys
     * @return
     */
    public boolean del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return false;
            }
            return jedis.del(keys) > 0;
        } finally {
            if (jedis != null) {

                jedis.close();
            }
        }
    }


    //TODO 建议返回 JUC中Lock类，由Lock类进行try

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
                log.error(NO_JEDIS_INSTANCE);
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
            log.error("acquireLock error ==> ", e);
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
                log.error(NO_JEDIS_INSTANCE);
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
     * try lock multi lock
     *
     * @param keys     resource keys
     * @param timeout  key life time
     * @param timeUnit time unit of timeout,
     * @return
     */
    public boolean tryLock(final Set<String> keys, int timeout, TimeUnit timeUnit) {
        return tryLock(keys, 0, timeUnit.toMillis(timeout));
    }

    /**
     * try lock multi lock.
     *
     * @param keys
     * @param waitTime
     * @param waitTimeUnit
     * @param timeout
     * @param timeoutTimeUnit
     * @return
     */
    public boolean tryLock(final Set<String> keys, int waitTime, TimeUnit waitTimeUnit,
                           int timeout, TimeUnit timeoutTimeUnit) {
        return tryLock(keys, waitTimeUnit.toMillis(waitTime), timeoutTimeUnit.toMillis(timeout));
    }

    /**
     * try lock multi lock
     *
     * @param keys    resource keys.
     * @param maxWait max wait time. unit:ms
     * @param timeout key life. unit:ms
     * @return
     */
    public boolean tryLock(final Set<String> keys, long maxWait, long timeout) {
        try {
            log.info("尝试获取锁：{}", keys);

            // 需要获取的锁
            final List<String> needLocking = new CopyOnWriteArrayList<>();
            needLocking.addAll(keys);

            // 已经获取的锁
            List<String> locked = new CopyOnWriteArrayList<>();

            if (maxWait <= 0) {
                return hasMultiLock(needLocking, locked, timeout);
            } else {
                long expireAt = System.currentTimeMillis() + maxWait;

                // 循环判断锁是否一直存在
                while (System.currentTimeMillis() <= expireAt) {
                    boolean flag = hasMultiLock(needLocking, locked, timeout);
                    if (flag) {
                        return true;
                    }

                    // 部分资源未能锁住，间隔N毫秒后重试
                    Thread.sleep(10);
                }
            }

            // 仍有资源未被锁住（needLocking不为空），释放已锁定的资源，并返回失败false
            if (!CollectionUtils.isEmpty(needLocking)) {
                log.info("can not get the lock, keys:{}", needLocking);
                unLock(locked);
            }
        } catch (Exception e) {
            log.info("尝试获取锁失败：{}", e);
        }

        return false;
    }

    private boolean hasMultiLock(List<String> needLocking, List<String> locked, long timeout) {
        // 通过管道批量获取锁
        log.info("进入tryLock while，needLocking：{}", needLocking);

        Long expire = TimeUnit.MILLISECONDS.toSeconds(timeout);

        try (Jedis jedis = pool.getResource()) {
            Pipeline pipeline = jedis.pipelined();

            String value = System.currentTimeMillis() + "";
            for (String key : needLocking) {
                log.debug("try to setnx {}", key);
                pipeline.setnx(key.getBytes(), value.getBytes());
            }
            List<Object> results = pipeline.syncAndReturnAll();

            // 提交redis执行计数
            log.info("needLocking:{},results:{}", needLocking, results);
            for (int i = 0; i < results.size(); i++) {
                // 锁的KEY
                String key = needLocking.get(i);
                Object result = results.get(i);
                Boolean success = result != null && EqualUtil.isEq(result.toString(), "1");

                // setnx成功，获得锁
                if (success) {
                    // 设置锁的过期时间
                    jedis.expire(key, expire.intValue());
                    locked.add(key);
                }
            }

            // 移除已锁定资源
            needLocking.removeAll(locked);

            // 是否锁住全部资源
            if (CollectionUtils.isEmpty(needLocking)) {
                // 全部资源均已锁住，返回成功true
                return true;
            } else {
                // 补偿处理，防止异常情况下（宕机/重启/连接超时等）导致的锁永不过期
                List<String> exceptionLock = new CopyOnWriteArrayList<>();
                for (String key : needLocking) {
                    String val = jedis.get(key);
                    // 当前时间 > 上锁时间 + 超时时间 + 2秒（经验时间），表示为该过期却未过期的数据，即异常数据
                    if (null != val && System.currentTimeMillis() > Long.parseLong(val) + timeout + 2000) {
                        exceptionLock.add(key);
                    }
                }
                // 删除所有异常的 KEY
                if (CollectionUtils.isNotEmpty(exceptionLock)) {
                    unLock(exceptionLock);
                }
            }
        }


        return false;
    }

    /**
     * unlock multi keys
     *
     * @param keys
     */
    public void unLock(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            log.warn("unlock keys is empty.");
            return;
        }
        try (Jedis jedis = pool.getResource()) {
            jedis.del(ArrayUtil.toArray(keys, String.class));
        } catch (Exception e) {
            log.error("批量释放锁失败!", e);
        }
    }


    /**
     * keys
     * TODO 大数据量下不推荐使用
     *
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(String pattern) {
        try (Jedis jedis = pool.getResource()) {
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return SetUtil.empty();
            }

            return jedis.keys(pattern.getBytes());
        }
    }


    /**
     * 遍历
     *
     * @param cursor
     * @param scanParams
     */
    public ScanResult<String> scan(String cursor, ScanParams scanParams) {
        try (Jedis jedis = pool.getResource()) {
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return null;
            }
            return jedis.scan(cursor, scanParams);
        }
    }

    /**
     * flush db
     */
    public void flushDB() {
        try (Jedis jedis = pool.getResource()) {
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return;
            }

            jedis.flushDB();
        }

    }

    /**
     * query db size
     *
     * @return
     */
    public long dbSize() {
        try (Jedis jedis = pool.getResource()) {
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return 0;
            }

            return jedis.dbSize();
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
                log.error(NO_JEDIS_INSTANCE);
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
                log.error(NO_JEDIS_INSTANCE);
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
     * Insert all the specified values at the head of the list stored at key.
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
                log.error(NO_JEDIS_INSTANCE);
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
     * Insert all the specified values at the tail of the list stored at key
     *
     * @param key
     * @param msg
     * @return
     */
    public Long rpush(String key, String msg) {
        try (Jedis jedis = pool.getResource()) {
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
            }
            return jedis.rpush(key, msg);
        }
    }

    /**
     * Removes and returns the first elements of the list stored at key.
     * <p>
     * By default, the command pops a single element from the beginning of the list.
     * When provided with the optional count argument, the reply will consist of up to count elements,
     * depending on the list's length.
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.lpop(key);
        }
    }

    /**
     * right pop
     * Removes and returns the last elements of the list stored at key.
     * <p>
     * By default, the command pops a single element from the end of the list.
     * When provided with the optional count argument, the reply will consist of up to count elements,
     * depending on the list's length.
     *
     * @param key
     * @return
     */
    public String rpop(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
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
                log.error(NO_JEDIS_INSTANCE);
                return 0;
            }
            return jedis.llen(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param second
     * @return
     */
    public long expire(String key, int second) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            if (jedis == null) {
                log.error(NO_JEDIS_INSTANCE);
                return 0;
            }
            return jedis.expire(key, second);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * expire [key] at [uninxTime]
     *
     * @param key
     * @param unixTime
     * @return
     */
    public long expireAt(String key, long unixTime) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.expireAt(key, unixTime);
        }
    }

    /**
     * persist key
     *
     * @param key
     * @return
     */
    public long persist(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.persist(key);
        }
    }

    /**
     * check key exists
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.exists(key);
        }
    }


    /**
     * get bit
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getBit(String key, long offset) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.getbit(key, offset);
        }
    }

    /**
     * set bit offset max value.
     */
    public static final long BIT_OFFSET_MAX_VALUE = (long) (Math.pow(2, 32)) - 1;

    /**
     * set bit
     * important: offset must be checked.
     *
     * @param key
     * @param offset max value 4294967295// 2^32-1
     * @param value
     * @return the original bit value stored at offset. true:1=1,false:0=1
     */
    public boolean setBit(String key, long offset, String value) {
        if (offset > BIT_OFFSET_MAX_VALUE) {
            log.error("offset is out of range");
            throw new RuntimeException("offset is out of range.");
        }

        try (Jedis jedis = pool.getResource()) {
            return jedis.setbit(key, offset, value);
        }
    }

    /**
     * set bit
     * important: offset must be checked.
     *
     * @param key
     * @param offset max value 4294967295// 2^32-1
     * @param value
     * @return the original bit value stored at offset. true:1=1,false:0=1
     */
    public boolean setBit(String key, long offset, boolean value) {
        if (offset > BIT_OFFSET_MAX_VALUE) {
            log.error("offset is out of range");
            throw new RuntimeException("offset is out of range.");
        }

        try (Jedis jedis = pool.getResource()) {
            return jedis.setbit(key, offset, value);
        }
    }

    public long nextId(String bizType) {
        return nextId(bizType, 1);
    }

    public long nextId(String bizType, int step) {
        try (Jedis jedis = pool.getResource()) {

            List<String> keys = new ArrayList<>();
            keys.add(bizType);
            keys.add(String.valueOf(step));
            Long result = Long.valueOf(jedis.eval(getLuaScript(RedisConst.LUA_GENERATE_ID), keys, ListUtil.empty()).toString());

            return result;
        }
    }

    public List<Long> nextIds(String bizType, int size) {
        return nextIds(bizType, size, 1);
    }

    public List<Long> nextIds(String bizType, int size, int step) {
        try (Jedis jedis = pool.getResource()) {

            List<String> keys = new ArrayList<>();
            keys.add(bizType);
            keys.add(String.valueOf(size));
            keys.add(String.valueOf(step));
            Object obj = jedis.eval(getLuaScript(RedisConst.LUA_GENERATE_ID_BATCH), keys, ListUtil.empty());

            if (obj == null) {
                return ListUtil.empty();
            }
            List<Long> values = (List<Long>) obj;
            if (values.isEmpty()) {
                return ListUtil.empty();
            }

            if (values.size() != 2) {
                ExceptionHandler.publishMsg("lua return value error.");
            }

            long begin = values.get(0);
            long end = values.get(1);

            List<Long> ids = new ArrayList<>();
            for (int i = 1; i <= size; i++) {
                ids.add(begin + i * step);
            }
            if (EqualUtil.isNotEq(end, ids.get(ids.size() - 1))) {
                ExceptionHandler.publishMsg("Consistency check fail.");
            }
            return ids;
        }
    }

    /**
     * 批量增加
     *
     * @param keys
     * @return
     */
    public boolean batchIncr(List<String> keys) {
        return batchIncr(keys, 1);
    }

    /**
     * 批量增加
     *
     * @param keys
     * @param step
     * @return
     */
    public boolean batchIncr(List<String> keys, int step) {
        try (Jedis jedis = pool.getResource()) {
            List<String> args = new ArrayList<>();
            args.add(String.valueOf(step));
            jedis.eval(getLuaScript(RedisConst.LUA_BATCH_INCR), keys, args);
        }
        return true;
    }

    /**
     * 批量增加（数量限制）
     *
     * @param keys
     * @param limits
     * @return
     */
    public boolean batchIncrLimit(List<String> keys, List<Integer> limits) {
        return batchIncrLimit(keys, limits, 1, 0);
    }

    /**
     * 批量增加（数量限制）
     *
     * @param keys
     * @param limits
     * @param step
     * @param timeout 单位秒
     * @return
     */
    public boolean batchIncrLimit(List<String> keys, List<Integer> limits, int step, long timeout) {
        try (Jedis jedis = pool.getResource()) {
            List<String> args = new ArrayList<>();
            args.addAll(limits.stream().map(item -> String.valueOf(item)).collect(Collectors.toList()));
            args.add(String.valueOf(step));

            timeout = timeout < 0 ? 0 : timeout;
            args.add(String.valueOf(timeout));
            Object resultObj = jedis.eval(getLuaScript(RedisConst.LUA_BATCH_INCR_LIMIT), keys, args);

            if (resultObj == null) {
                return false;
            }
            Long result = (Long) resultObj;
            return EqualUtil.isEq(result, RedisConst.LUA_BOOLEAN_TRUE);
        }
    }

    public boolean batchDecr(List<String> keys) {
        return batchDecr(keys, 1);
    }

    public boolean batchDecr(List<String> keys, int step) {
        try (Jedis jedis = pool.getResource()) {
            List<String> args = new ArrayList<>();
            args.add(String.valueOf(step));
            jedis.eval(getLuaScript(RedisConst.LUA_BATCH_DECR), keys, args);
        }
        return true;
    }

    /**
     * 批量减少
     *
     * @param keys
     * @return
     */
    public boolean batchDecrLimit(List<String> keys, List<Integer> limits) {
        return batchDecrLimit(keys, limits, 1);
    }

    public boolean batchDecrLimit(List<String> keys, List<Integer> limits, int step) {
        try (Jedis jedis = pool.getResource()) {
            List<String> args = new ArrayList<>();
            args.addAll(limits.stream().map(item -> String.valueOf(item)).collect(Collectors.toList()));
            args.add(String.valueOf(step));
            Object resultObj = jedis.eval(getLuaScript(RedisConst.LUA_BATCH_DECR_LIMIT), keys, args);

            if (resultObj == null) {
                return false;
            }
            Long result = (Long) resultObj;
            return EqualUtil.isEq(result, RedisConst.LUA_BOOLEAN_TRUE);
        }
    }


    /**
     * read lua script content.
     *
     * @param filename
     * @return
     */
    private String getLuaScript(String filename) {
        return scriptMap.computeIfAbsent(filename,
                key -> FileUtil.readFormClasspath("sea/core/lua/" + filename));
    }

    //--------------------private

    private void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
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
