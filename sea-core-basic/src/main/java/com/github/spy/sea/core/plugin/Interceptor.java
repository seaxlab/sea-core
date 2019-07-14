package com.github.spy.sea.core.plugin;

import java.util.Properties;

/**
 * @author Clinton Begin
 */
public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    default Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    default void setProperties(Properties properties) {
        // NOP
    }

}
