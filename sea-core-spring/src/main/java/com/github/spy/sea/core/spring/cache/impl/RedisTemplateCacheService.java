package com.github.spy.sea.core.spring.cache.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.spy.sea.core.cache.CacheConst;
import com.github.spy.sea.core.cache.CacheExceptionHandler;
import com.github.spy.sea.core.cache.CacheService;
import com.github.spy.sea.core.enums.CacheOpEnum;
import com.github.spy.sea.core.exception.Precondition;
import com.github.spy.sea.core.model.EntityKey;
import com.github.spy.sea.core.util.EqualUtil;
import com.github.spy.sea.core.util.JSONUtil;
import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
            handleException(CacheOpEnum.GET, key, e);
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
                handleException(CacheOpEnum.SET, key, e);
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
    public <T> Optional<T> queryIfAbsent(String key, Supplier<T> supplier, Class<T> clazz) {
        return queryIfAbsent(key, supplier, clazz, CACHE_CONFIG.getFirst(), CACHE_CONFIG.getSecond());
    }

    @Override
    public <T> Optional<T> queryIfAbsent(String key, Supplier<T> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit) {
        timeout = timeout <= 0 ? CACHE_CONFIG.getFirst() : timeout;
        timeUnit = timeUnit == null ? CACHE_CONFIG.getSecond() : timeUnit;

        String content;
        try {
            content = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("fail to get cache, key={}, so query from supplier. ex={}", key, e);
            handleException(CacheOpEnum.GET, key, e);
            return Optional.ofNullable(supplier.get());
        }
        T data;

        if (content == null) {
            data = supplier.get();

            if (data != null) {
                try {
                    redisTemplate.opsForValue()
                                 .set(key, JSONUtil.toStr(data), timeout, timeUnit);
                } catch (Exception e) {
                    log.error("fail to set one record, key={}, exception={}", key, e);
                    handleException(CacheOpEnum.SET, key, e);
                }
            }
        } else {
            data = JSONObject.parseObject(content, clazz);
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
            handleException(CacheOpEnum.GET, key, e);
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
                handleException(CacheOpEnum.SET, key, e);
            }
        } else {
            data = JSONArray.parseArray(content, clazz);
        }
        return data;
    }

    @Override
    public <T> List<T> queryListIfAbsent(String key, Supplier<List<T>> supplier, Class<T> clazz) {
        return queryListIfAbsent(key, supplier, clazz, CACHE_CONFIG.getFirst(), CACHE_CONFIG.getSecond());
    }

    @Override
    public <T> List<T> queryListIfAbsent(String key, Supplier<List<T>> supplier, Class<T> clazz, long timeout, TimeUnit timeUnit) {
        timeout = timeout <= 0 ? CACHE_CONFIG.getFirst() : timeout;
        timeUnit = timeUnit == null ? CACHE_CONFIG.getSecond() : timeUnit;

        String content;
        try {
            content = (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("fail to get cache, key={}, so query from supplier. ex={}", key, e);
            handleException(CacheOpEnum.GET, key, e);
            return supplier.get();
        }

        List<T> data;
        if (content == null) {
            data = supplier.get();
            // 重点：不为空时存储
            if (ListUtil.isNotEmpty(data)) {
                try {
                    redisTemplate.opsForValue()
                                 .set(key, JSONUtil.toStr(data), timeout, timeUnit);
                } catch (Exception e) {
                    log.error("fail to set list key={},exception={}", key, e);
                    handleException(CacheOpEnum.SET, key, e);
                }
            }
        } else {
            data = JSONArray.parseArray(content, clazz);
        }
        return data;
    }

    @Override
    public <T extends EntityKey> List<T> queryMapList(String key, List<String> mapKeys) {
        return queryMapList(key, mapKeys, null);
    }

    @Override
    public <T extends EntityKey> List<T> queryMapList(String key, List<String> mapKeys, Function<List<String>, List<T>> function) {
        return queryMapList(key, mapKeys, function, CACHE_CONFIG.getFirst(), CACHE_CONFIG.getSecond());
    }

    @Override
    public <T extends EntityKey> List<T> queryMapList(String key, List<String> mapKeys, Function<List<String>, List<T>> function, long timeout, TimeUnit timeUnit) {
        if (ListUtil.isEmpty(mapKeys)) {
            log.warn("map keys is empty.");
            return ListUtil.empty();
        }

        mapKeys = mapKeys.stream()
                         .filter(item -> StringUtil.isNotBlank(item))
                         .distinct()
                         .collect(Collectors.toList());
        if (ListUtil.isEmpty(mapKeys)) {
            log.warn("map keys is empty after filter blank element.");
            return ListUtil.empty();
        }

        List<T> data = null;
        try {
            List<String> hKeys = new ArrayList<>(mapKeys);
            data = redisTemplate.opsForHash().multiGet(key, hKeys);
        } catch (Exception e) {
            log.error("failed get from cache, key={},ex={}", key, e);
            handleException(CacheOpEnum.GET_MAP, key, e);
        }
        // insure data is not null
        if (data == null) {
            data = new ArrayList<>();
        }

        //重点：redis hashmap 独有的类型，获取不到就会返回null
        data.removeAll(Collections.singleton(null));
        if (mapKeys.size() == data.size()) {
            return data;
        }

        if (function == null) {
            return data;
        }

        List<String> exitKeys = ListUtil.toListDistinct(data, EntityKey::getEntityKey);
        List<String> restKeys = ListUtil.filter(mapKeys, keys -> !exitKeys.contains(keys));
        // 没有查询到的key
        List<T> restData = function.apply(restKeys);
        log.info("rest data size={}", restData.size());

        if (ListUtil.isNotEmpty(restData)) {
            data.addAll(restData);

            Map<String, T> restDataMap = ListUtil.toMap(restData, item -> StringUtils.isNotBlank(item.getEntityKey()), EntityKey::getEntityKey);
            try {
                redisTemplate.opsForHash().putAll(key, restDataMap);
                if (timeout <= 0) {
                    log.warn("timeout={}<=0, so no expire", timeout);
                    redisTemplate.expire(key, timeout, timeUnit);
                }
            } catch (Exception e) {
                log.error("failed put hash to cache, key={},ex={}", key, e);
                handleException(CacheOpEnum.SET_MAP, key, e);
            }
        }
        return data;
    }

    @Override
    public boolean set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
        return true;
    }

    @Override
    public boolean setSafe(String key, Object obj) {
        try {
            redisTemplate.opsForValue().set(key, obj);
        } catch (Exception e) {
            log.error("fail to set key={}, obj={}, ex={}", key, obj, e);
            handleException(CacheOpEnum.SET, key, e);
        }
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
    public boolean setSafe(String key, Object obj, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, obj, timeout, timeUnit);
        } catch (Exception e) {
            log.error("fail to set key={}, obj={}, ex={}", key, obj, e);
            handleException(CacheOpEnum.SET, key, e);
        }
        return true;
    }

    @Override
    public boolean setJson(String key, Object obj) {
        redisTemplate.opsForValue().set(key, JSONUtil.toStr(obj));
        return true;
    }

    @Override
    public boolean setJsonSafe(String key, Object obj) {
        try {
            redisTemplate.opsForValue().set(key, JSONUtil.toStr(obj));
        } catch (Exception e) {
            log.error("fail to set json key={}, obj={}, ex={}", key, obj, e);
            handleException(CacheOpEnum.SET, key, e);
        }
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
    public boolean setJsonSafe(String key, Object obj, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, JSONUtil.toStr(obj), timeout, timeUnit);
        } catch (Exception e) {
            log.error("fail to set json key={}, obj={}, ex={}", key, obj, e);
            handleException(CacheOpEnum.SET, key, e);
        }
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
    public boolean deleteSafe(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("fail to delete key={},ex={}", key, e);
            handleException(CacheOpEnum.DELETE, key, e);
        }

        return false;
    }

    @Override
    public Long delete(Collection keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Long deleteSafe(Collection keys) {
        try {
            return redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("fail to delete key={},ex={}", keys, e);
            handleException(CacheOpEnum.DELETE, keys, e);
        }

        return 0L;
    }

    @Override
    public void scan(String pattern, int count, Consumer<byte[]> consumer) {
        // check args
        Precondition.checkNotBlank(pattern, "pattern cannot be null");
        Precondition.checkNotNull(consumer, "consumer cannot be null");
        count = count <= 0 ? 1000 : count;

        RedisConnection redisConnection = null;
        try {
            redisConnection = redisTemplate.getConnectionFactory().getConnection();
            ScanOptions options = ScanOptions.scanOptions()
                                             .match(pattern)
                                             .count(count)
                                             .build();

            try (Cursor<byte[]> cursor = redisConnection.scan(options)) {
                cursor.forEachRemaining(consumer);
            }
        } catch (Exception e) {
            log.error("fail to scan ", e);
            handleException(CacheOpEnum.SCAN, pattern, e);
        } finally {
            redisConnection.close();
        }
    }


    // ----------------------private method region---------------------
    private void handleException(CacheOpEnum cacheOpEnum, String key, Throwable t) {
        if (this.exceptionHandler != null) {
            this.exceptionHandler.handle(cacheOpEnum.getCode(), key, t);
        }
    }

    private void handleException(CacheOpEnum cacheOpEnum, Collection keys, Throwable t) {
        if (this.exceptionHandler != null) {
            StringBuilder builder = new StringBuilder();
            keys.stream().forEach(key -> builder.append((String) key));
            this.exceptionHandler.handle(cacheOpEnum.getCode(), builder.toString(), t);
        }
    }


}
