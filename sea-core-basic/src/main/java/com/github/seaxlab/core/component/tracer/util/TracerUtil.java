package com.github.seaxlab.core.component.tracer.util;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.component.tracer.enums.TracerProviderEnum;
import com.github.seaxlab.core.component.tracer.skywalking.util.SkyWalkingUtil;
import com.github.seaxlab.core.component.tracer.sofa.util.SofaUtil;
import com.github.seaxlab.core.config.ConfigurationFactory;
import com.github.seaxlab.core.util.EqualUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * tracer util
 *
 * @author spy
 * @version 1.0 2022/08/31
 * @since 1.0
 */
@Slf4j
public class TracerUtil {

    private TracerUtil() {
    }

    /**
     * get trace id
     *
     * @return
     */
    public static String getTraceId() {
        String mode = ConfigurationFactory.getInstance().getString(CoreConst.TRACER_PROVIDER, "none");

        if (EqualUtil.isEq(mode, TracerProviderEnum.SKYWALKING.getCode(), false)) {
            return SkyWalkingUtil.getTraceId();
        }
        if (EqualUtil.isEq(mode, TracerProviderEnum.SOFA.getCode(), false)) {
            return SofaUtil.getTraceId();
        }

        return "NONE";
    }

    //----------------------------------------


}
