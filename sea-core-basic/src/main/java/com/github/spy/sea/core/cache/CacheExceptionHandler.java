package com.github.spy.sea.core.cache;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/8
 * @since 1.0
 */
public interface CacheExceptionHandler {

    /**
     * cache exception handler
     *
     * @param type cache op enum
     * @param key  cache key
     * @param t    exception.
     */
    void handle(String type, String key, Throwable t);
}
