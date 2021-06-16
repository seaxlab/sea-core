package com.github.spy.sea.core.spring.cache.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.spy.sea.core.cache.CacheConst;
import com.github.spy.sea.core.cache.CacheExceptionHandler;
import com.github.spy.sea.core.cache.CacheService;
import com.github.spy.sea.core.enums.CacheOpEnum;
import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.JSONUtil;
import com.github.spy.sea.core.util.SetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * cache service.
 *
 * @author spy
 * @version 1.0 2021/5/29
 * @since 1.0
 */
@Slf4j
public class RedisTemplateCacheService implements CacheService {

    private RedisTemplate redisTemplate;

    private CacheExceptionHandler exceptionHandler;

    public RedisTemplateCacheService() {
    }

    public RedisTemplateCacheService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplateCacheService(RedisTemplate redisTemplate, CacheExceptionHandler exceptionHandler) {
        this.redisTemplate = redisTemplate;
        this.exceptionHandler = exceptionHandler;
    }


    @Override
    public <T> Optional<T> query(String key, Supplier<T> supplier, Class<T> clazz) {
        return query(key, supplier, clazz, CACHE_CONFIG.getFirst(), CACHE_CONFIG.getSecond());
    }

    @Override
    public <T> Optional<T> query(String key, Supplier<T> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit) {
        timeout = timeout <= 0 ? CACHE_CONFIG.getFirst() : timeout;
        timeUnit = timeUnit == null ? CACHE_CONFIG.getSecond() : timeUnit;

        String content;
        try {
            content = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("fail to get cache, key={}, so query from supplier. ex={}", key, e);
            if (this.exceptionHandler != null) {
                this.exceptionHandler.handle(CacheOpEnum.SET.getCode(), key, e);
            }
            return Optional.ofNullable(supplier.get());
        }
        T data;

        if (content == null) {
            data = supplier.get();

            String value = data == null ? CacheConst.EMPTY_OBJ : JSONUtil.toStr(data);
            try {
                redisTemplate.opsForValue()
                             .set(key, value, timeout, timeUnit);
            } catch (Exception e) {
                log.error("fail to set one record, key={}, exception={}", key, e);
                if (this.exceptionHandler != null) {
                    this.exceptionHandler.handle(CacheOpEnum.SET.getCode(), key, e);
                }
            }
        } else {
            if (EqualUtil.isEq(content, CacheConst.EMPTY_OBJ)) {
                data = null;
            } else {
                data = JSONObject.parseObject(content, clazz);
            }
        }

        return Optional.ofNullable(data);
    }

    @Override
    public <T> List<T> queryList(String key, Supplier<List<T>> supplier, Class<T> clazz) {
        return queryList(key, supplier, clazz, CACHE_CONFIG.getFirst(), CACHE_CONFIG.getSecond());
    }

    @Override
    public <T> List<T> queryList(String key, Supplier<List<T>> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit) {
        timeout = timeout <= 0 ? CACHE_CONFIG.getFirst() : timeout;
        timeUnit = timeUnit == null ? CACHE_CONFIG.getSecond() : timeUnit;

        String content;
        try {
            content = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("fail to get cache, key={}, so query from supplier. ex={}", key, e);
            return supplier.get();
        }

        List<T> data;
        if (content == null) {
            data = supplier.get();
            try {
                redisTemplate.opsForValue()
                             .set(key, JSONUtil.toStr(data), timeout, timeUnit);
            } catch (Exception e) {
                log.error("fail to set list key={},exception={}", key, e);
                if (this.exceptionHandler != null) {
                    this.exceptionHandler.handle(CacheOpEnum.SET.getCode(), key, e);
                }
            }
        } else {
            data = JSONArray.parseArray(content, clazz);
        }
        return data;
    }


    @Override
    public boolean set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
        return true;
    }

    @Override
    public boolean setExpire(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj, CACHE_CONFIG.getFirst(), CACHE_CONFIG.getSecond());
        return true;
    }

    @Override
    public boolean set(String key, Object obj, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, obj, timeout, timeUnit);
        return true;
    }


    @Override
    public boolean setJson(String key, Object obj) {
        redisTemplate.opsForValue().set(key, JSONUtil.toStr(obj));
        return true;
    }

    @Override
    public boolean setJsonExpire(String key, Object obj) {
        redisTemplate.opsForValue().set(key, JSONUtil.toStr(obj), CACHE_CONFIG.getFirst(), CACHE_CONFIG.getSecond());
        return true;
    }

    @Override
    public boolean setJson(String key, Object obj, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, JSONUtil.toStr(obj), timeout, timeUnit);
        return true;
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long delete(Collection keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Set<String> scan(String pattern, int count) {
        if (count > 1000) {
            log.warn("count large 1000, return empty.");
            return SetUtil.empty();
        }

        Set<String> ids = new HashSet<>();

        RedisConnection redisConnection = null;
        try {
            redisConnection = redisTemplate.getConnectionFactory().getConnection();
            ScanOptions options = ScanOptions.scanOptions().match(pattern).build();

            Cursor<byte[]> c = redisConnection.scan(options);
            while (c.hasNext()) {
                ids.add(new String(c.next(), "Utf-8"));
            }
        } catch (Exception e) {
            log.error("fail to scan ", e);
        } finally {
            redisConnection.close();
        }
        return ids;

//        return (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
//            Set<String> ids = new HashSet<>();
//            ScanOptions options = ScanOptions.scanOptions()
//                                             .match(pattern)
//                                             .count(count)
//                                             .build();
//            try (Cursor<byte[]> cursor = connection.scan(options)) {
//                while (cursor.hasNext()) {
//                    ids.add(new String(cursor.next(), "Utf-8"));
//                }
//            } catch (Exception e) {
//                log.error("fail to scan", e);
//            }
//            return ids;
//        });
    }

}
