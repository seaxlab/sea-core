package com.github.spy.sea.core.web.extension.impl;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.web.extension.HttpHeaderParseExtension;
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
public class DefaultMockHttpHeaderExtension implements HttpHeaderParseExtension {
    @Override
    public Map<String, String> get() {
        Map<String, String> map = new HashMap<>(1);
        map.put("Sea-Mock", CoreConst.DEFAULT_MOCK_KEY);
        return map;
    }
}
