package com.github.seaxlab.core.component.loadbalance;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
public interface HashFunction {

    long hash(String str);
}
