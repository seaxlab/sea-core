package com.github.spy.sea.core.concurrent;

/**
 * <p>
 * Definition of an interface for the thread-safe initialization of objects.
 * </p>
 * <p>
 * The idea behind this interface is to provide access to an object in a
 * thread-safe manner. A {@code ConcurrentInitializer} can be passed to multiple
 * threads which can all access the object produced by the initializer. Through
 * the {@link #get()} method the object can be queried.
 * </p>
 * <p>
 * Concrete implementations of this interface will use different strategies for
 * the creation of the managed object, e.g. lazy initialization or
 * initialization in a background thread. This is completely transparent to
 * client code, so it is possible to change the initialization strategy without
 * affecting clients.
 * </p>
 *
 * @param <T> the type of the object managed by this initializer class
 * @since 3.0
 */
public interface ConcurrentInitializer<T> {
    /**
     * Returns the fully initialized object produced by this {@code
     * ConcurrentInitializer}. A concrete implementation here returns the
     * results of the initialization process. This method may block until
     * results are available. Typically, once created the result object is
     * always the same.
     *
     * @return the object created by this {@code ConcurrentException}
     * @throws ConcurrentException if an error occurred during initialization of
     *                             the object
     */
    T get() throws ConcurrentException;
}
