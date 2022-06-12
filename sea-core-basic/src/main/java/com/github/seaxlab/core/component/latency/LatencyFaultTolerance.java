package com.github.seaxlab.core.component.latency;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/16
 * @since 1.0
 */
public interface LatencyFaultTolerance<T> {

    /**
     * update fault item
     *
     * @param name                 target item
     * @param currentLatency       current latency
     * @param notAvailableDuration not available duration
     */
    void updateFaultItem(final T name, final long currentLatency, final long notAvailableDuration);

    /**
     * check this one is available
     *
     * @param name target item
     * @return boolean
     */
    boolean isAvailable(final T name);

    /**
     * remove target item
     *
     * @param name target item
     */
    void remove(final T name);

    /**
     * pick one item
     *
     * @return may be null.
     */
    T pickOneAtLeast();
}
