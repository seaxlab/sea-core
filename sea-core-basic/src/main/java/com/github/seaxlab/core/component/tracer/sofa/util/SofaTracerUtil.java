package com.github.seaxlab.core.component.tracer.sofa.util;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.alipay.common.tracer.core.context.trace.SofaTraceContext;
import com.alipay.common.tracer.core.holder.SofaTraceContextHolder;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * sofa util
 *
 * @author spy
 * @version 1.0 2022/8/31
 * @since 1.0
 */
@Slf4j
public final class SofaTracerUtil {

    private SofaTracerUtil() {
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

    /**
     * put tag
     *
     * @param key
     * @param value
     */
    public static void putTag(String key, String value) {
        try {
            SofaTraceContext sofaTraceContext = SofaTraceContextHolder.getSofaTraceContext();
            if (sofaTraceContext == null) {
                return;
            }
            SofaTracerSpan sofaTracerSpan = sofaTraceContext.getCurrentSpan();
            if (sofaTracerSpan == null) {
                return;
            }
            sofaTracerSpan.setTag(key, StringUtils.defaultString(value, EMPTY));

        } catch (Exception e) {
            log.error("fail to put tracer tag", e);
        }
    }
}
