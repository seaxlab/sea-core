package com.github.seaxlab.core.thread;

/**
 * specified name thread local.
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

    private String name;

    private NamedThreadLocal() {
    }

    public NamedThreadLocal(String name) {
        super();
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
