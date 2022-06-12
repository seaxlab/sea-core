package com.github.seaxlab.core.web.extension.impl;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.web.extension.HttpRequestParseExtension;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/11
 * @since 1.0
 */
@Slf4j
public class DefaultMockHttpRequestExtension implements HttpRequestParseExtension {
    @Override
    public Map<String, String> get() {
        Map<String, String> map = new HashMap<>(1);
        map.put(CoreConst.DEFAULT_MOCK_KEY, CoreConst.DEFAULT_MOCK_KEY);
        return map;
    }
}
