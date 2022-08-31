package com.github.seaxlab.core.component.tracer.skywalking.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * sky walking util
 *
 * @author spy
 * @version 1.0 2022/08/31
 * @since 1.0
 */
@Slf4j
public class SkyWalkingUtil {

    private SkyWalkingUtil() {
    }

    public static String getTraceId() {
        try {
            return TraceContext.traceId();
        } catch (Exception e) {

        }
        return "N/A2";
    }

}
