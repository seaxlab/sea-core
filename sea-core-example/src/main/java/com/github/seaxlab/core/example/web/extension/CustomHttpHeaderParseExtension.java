package com.github.seaxlab.core.example.web.extension;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.util.MapUtil;
import com.github.seaxlab.core.web.extension.HttpHeaderParseExtension;
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
