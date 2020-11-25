package com.github.spy.sea.core.extension;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/25
 * @since 1.0
 */
public interface HttpCookieParseExtension {
    /**
     * get specified key
     *
     * @return Map, key:out-key, value:inner-key
     */
    Map<String, String> get();
}
