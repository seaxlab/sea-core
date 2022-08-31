package com.github.seaxlab.core.common;

import com.github.seaxlab.core.component.tracer.enums.TracerProviderEnum;
import com.github.seaxlab.core.config.ConfigurationFactory;
import com.github.seaxlab.core.enums.ConfigKeyEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * sea global config
 *
 * @author spy
 * @version 1.0 2022/9/1
 * @since 1.0
 */
@Slf4j
public class SeaGlobalConfig {

    // set
    public static void setTraceProvider(TracerProviderEnum provider) {
        ConfigurationFactory.getInstance().putString(ConfigKeyEnum.TRACE_PROVIDER.getCode(), provider.getCode());
    }

    // get
    public static TracerProviderEnum getTraceProvider() {
        return TracerProviderEnum.NONE;
    }
}
