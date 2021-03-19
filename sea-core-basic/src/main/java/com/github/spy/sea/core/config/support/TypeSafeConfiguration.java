package com.github.spy.sea.core.config.support;

import com.github.spy.sea.core.config.Configuration;
import com.github.spy.sea.core.loader.LoadLevel;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * HOCON 实现(Human-Optimized Config Object Notation)
 * 可以自动加载sea.properties、sea.conf等文件
 *
 * @author spy
 * @version 1.0 2019-07-25
 * @since 1.0
 */
@Slf4j
@LoadLevel(name = "typesafe")
public class TypeSafeConfiguration implements Configuration {

    /**
     * IMPORTANT config is immutable!!!!
     * so if you want to change
     * <code>
     * config = config.withValue("abc", ConfigValueFactory.fromAnyRef("123"));
     * </code>
     */
    private Config config = ConfigFactory.load("sea");


    @Override
    public int getInt(String key, int defaultValue) {
        return checkAndGet(key, () -> config.getInt(key), defaultValue);
    }

    @Override
    public int getInt(String key) {
        return checkAndGet(key, () -> config.getInt(key));
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return checkAndGet(key, () -> config.getLong(key), defaultValue);
    }

    @Override
    public long getLong(String key) {
        return checkAndGet(key, () -> config.getLong(key));
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return checkAndGet(key, () -> config.getBoolean(key), defaultValue);
    }

    @Override
    public boolean getBoolean(String key) {
        return checkAndGet(key, () -> config.getBoolean(key));
    }

    @Override
    public String getString(String key, String defaultValue) {
        return checkAndGet(key, () -> config.getString(key), defaultValue);
    }

    @Override
    public String getString(String key) {

        return checkAndGet(key, () -> config.getString(key));
    }

    @Override
    public synchronized boolean putString(String key, String content) {
        config = config.withValue(key, ConfigValueFactory.fromAnyRef(content));
        return true;
    }

    @Override
    public synchronized boolean putStringIfAbsent(String key, String content) {
        if (config.hasPath(key)) {
            return false;
        }
        config = config.withValue(key, ConfigValueFactory.fromAnyRef(content));
        return true;
    }

    @Override
    public synchronized boolean remove(String key) {
        config = config.withoutPath(key);
        return true;
    }


    /**
     * 判断是否有值
     *
     * @param key
     * @param supplier
     * @param <T>
     * @return
     */
    private <T> T checkAndGet(String key, Supplier<T> supplier) {
        return checkAndGet(key, supplier, null);
    }

    /**
     * 判断是否有值
     *
     * @param key
     * @param supplier
     * @param defaultValue
     * @param <T>
     * @return
     */
    private <T> T checkAndGet(String key, Supplier<T> supplier, T defaultValue) {
        if (config.hasPath(key)) {
            return supplier.get();
        }
        return defaultValue;
    }
}
