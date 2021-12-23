package com.github.spy.sea.core.component.perf.anno;

import java.lang.annotation.*;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/23
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Profiler {
}
