package com.github.spy.sea.core.thread.util;

import com.alipay.common.tracer.core.async.SofaTracerCallable;
import com.alipay.common.tracer.core.context.trace.SofaTraceContext;
import com.alipay.common.tracer.core.holder.SofaTraceContextHolder;
import com.github.spy.sea.core.component.tracer.sofa.simple.SimpleTracer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/15
 * @since 1.0
 */
@Slf4j
public final class CallableUtil {

    public static <V> Callable<V> create(boolean cleanTraceFlag, Supplier<V> supplier) {
        Callable<V> callable = null;
        if (cleanTraceFlag) {
            callable = () -> {
                V ret;
                SimpleTracer tracer = SimpleTracer.getTracerSingleton();
                String tracerStatus = SimpleTracer.RESULT_CODE_FAIL;
                try {
                    tracer.begin("sea-callable");
                    ret = supplier.get();
                    tracerStatus = SimpleTracer.RESULT_CODE_SUC;

                } catch (Exception e) {
                    tracer.setException(e);
                    throw e;
                } finally {
                    tracer.end(tracerStatus);
                }
                return ret;
            };
        } else {
            callable = new SofaTracerCallable<V>(() -> {
                return supplier.get();
            });
        }

        return callable;
    }


    private static void cleanTraceId() {
        try {
            SofaTraceContext sofaTraceContext = SofaTraceContextHolder.getSofaTraceContext();
            sofaTraceContext.clear();
        } catch (Exception var1) {
            log.error("fail to clear sofa trace");
        }
    }

}
