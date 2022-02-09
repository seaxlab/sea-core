package com.github.spy.sea.core.example.extension;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.MapUtil;
import com.github.spy.sea.core.web.extension.HttpHeaderParseExtension;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/19
 * @since 1.0
 */
@Slf4j
public class CustomHttpHeaderParseExtension implements HttpHeaderParseExtension {
    @Override
    public Map<String, String> get() {
        Map<String, String> map = MapUtil.newNoResizeHashMap(1);

        map.put("X-Service-Chain", CoreConst.CTX_TAG_GRAY);
        return map;
    }
}
