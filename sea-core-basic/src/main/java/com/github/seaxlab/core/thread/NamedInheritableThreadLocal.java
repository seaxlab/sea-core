package com.github.seaxlab.core.thread;

/**
 * specified name inheritable thread local
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
public class NamedInheritableThreadLocal<T> extends InheritableThreadLocal<T> {

    private final String name;

    /**
     * specified name.
     *
     * @param name name
     */
    public NamedInheritableThreadLocal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
