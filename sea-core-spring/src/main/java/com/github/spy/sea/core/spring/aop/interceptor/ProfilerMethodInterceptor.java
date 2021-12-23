package com.github.spy.sea.core.spring.aop.interceptor;

import com.github.spy.sea.core.component.perf.Profiler;
import com.github.spy.sea.core.component.perf.ProfilerConfig;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/23
 * @since 1.0
 */
@Slf4j
public class ProfilerMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        if (!ProfilerConfig.getInstance().isOpenProfilerTree()) {
            return invocation.proceed();
        }
        String methodName = this.getClassAndMethodName(invocation);
        if (null == methodName) {
            return invocation.proceed();
        }
        try {
            if (Profiler.getEntry() == null) {
                Profiler.start(methodName);
            } else {
                Profiler.enter(methodName);
            }
            return invocation.proceed();
        } finally {
            Profiler.release();
            //当root entry为状态为release的时候，打印信息，并做reset操作
            Profiler.Entry rootEntry = Profiler.getEntry();
            if (rootEntry != null) {
                if (rootEntry.isReleased()) {
                    long duration = rootEntry.getDuration();
                    if (duration > ProfilerConfig.getInstance().getInvokeTimeout()) {
                        log.error("\n" + Profiler.dump() + "\n");
                    }
                    Profiler.reset();
                }
            }
        }
    }

    private String getClassAndMethodName(MethodInvocation invocation) {
        try {
            //MethodSignature sign = (MethodSignature) invocation.getSignature();
            //String clazzName = joinPoint.getTarget().toString();
            StringBuilder sb = new StringBuilder();
            //sb.append(Profiler.split(clazzName, "@")[0]);
            //sb.append(":").append(sign.getMethod().getName());
            sb.append(invocation.getMethod().getDeclaringClass().getName() + "." + invocation.getMethod().getName());
            sb.append("(param:").append(invocation.getMethod().getParameterTypes().length).append(")");
            return sb.toString();
        } catch (Throwable e) {
            log.warn("fail to get class and method name");
            return null;
        }
    }
}
