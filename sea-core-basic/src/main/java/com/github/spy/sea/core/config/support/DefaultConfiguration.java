package com.github.spy.sea.core.config.support;

import com.github.spy.sea.core.config.Configuration;
import com.github.spy.sea.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认实现
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Slf4j
@LoadLevel(name = "default")
public class DefaultConfiguration implements Configuration {

    private static Map<String, Object> cache = new ConcurrentHashMap<>();


    @Override
    public int getInt(String key, int defaultValue) {
        return MapUtils.getIntValue(cache, key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return MapUtils.getIntValue(cache, key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return MapUtils.getLongValue(cache, key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return MapUtils.getIntValue(cache, key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return MapUtils.getBooleanValue(cache, key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key) {
        return MapUtils.getBooleanValue(cache, key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return MapUtils.getString(cache, key, defaultValue);
    }

    @Override
    public String getString(String key) {
        return MapUtils.getString(cache, key);
    }

    @Override
    public boolean putString(String key, String content) {
        cache.put(key, content);
        return true;
    }

    @Override
    public boolean putStringIfAbsent(String key, String content) {
        if (!cache.containsKey(key)) {
            cache.put(key, content);
        }

        return false;
    }

    @Override
    public boolean remove(String key) {

        cache.remove(key);
        return false;
    }


}
