package com.github.seaxlab.core.component.tracer.sofa.util;

import com.alipay.common.tracer.core.holder.SofaTraceContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * sofa util
 *
 * @author spy
 * @version 1.0 2022/8/31
 * @since 1.0
 */
@Slf4j
public final class SofaUtil {

    private SofaUtil() {
    }

    public static String getTraceId() {
        try {
            return SofaTraceContextHolder.getSofaTraceContext()//
                                         .getCurrentSpan()//
                                         .getSofaTracerSpanContext()//
                                         .getTraceId();
        } catch (Exception e) {
            log.warn("fail to get sofa tracer id.");
        }
        return "N/A1";
    }
}
