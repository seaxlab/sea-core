package com.github.seaxlab.core.web.extension;

import java.util.Map;

// should move to sea-core-web? it is biz framework

/**
 * catch specified http header into thread context.
 *
 * @author spy
 * @version 1.0 2020/5/19
 * @since 1.0
 */
public interface HttpHeaderParseExtension {

    /**
     * get specified key
     *
     * @return Map, key:out-key, value:inner-key
     */
    Map<String, String> get();
}
