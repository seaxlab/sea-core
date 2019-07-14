package com.github.spy.sea.core.plugin;

import java.lang.annotation.*;

/**
 * @author Clinton Begin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {
    Signature[] value();
}

