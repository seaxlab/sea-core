package com.github.spy.sea.core.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/11/1
 * @since 1.0
 */
public interface Fn<T, R> extends Function<T, R>, Serializable {
}
