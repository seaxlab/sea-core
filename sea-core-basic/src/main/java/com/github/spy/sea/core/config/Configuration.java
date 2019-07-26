package com.github.spy.sea.core.config;

/**
 * 配置接口
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
public interface Configuration<T> {

    /**
     * Gets int.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the int
     */
    int getInt(String key, int defaultValue);

    /**
     * Gets int.
     *
     * @param key the key
     * @return the int
     */
    int getInt(String key);

    /**
     * Gets long.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the long
     */
    long getLong(String key, long defaultValue);

    /**
     * Gets long.
     *
     * @param key the key
     * @return the long
     */
    long getLong(String key);


    /**
     * Gets boolean.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the boolean
     */
    boolean getBoolean(String key, boolean defaultValue);

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    boolean getBoolean(String key);


    /**
     * Gets config.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the config
     */
    String getString(String key, String defaultValue);

    /**
     * Gets config.
     *
     * @param key the key
     * @return the config
     */
    String getString(String key);

    /**
     * Put config boolean.
     *
     * @param key     the key
     * @param content the content
     * @return the boolean
     */
    boolean putString(String key, String content);

    /**
     * Put config if absent boolean.
     *
     * @param key     the key
     * @param content the content
     * @return the boolean
     */
    boolean putStringIfAbsent(String key, String content);

    /**
     * Remove config.
     *
     * @param key the key
     * @return the boolean
     */
    boolean remove(String key);
}
