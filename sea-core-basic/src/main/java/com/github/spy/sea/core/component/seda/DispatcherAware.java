package com.github.spy.sea.core.component.seda;

/**
 * Declares Dispatcher awareness for an {@link EventHandler} mainly.
 */
public interface DispatcherAware {

    Dispatcher getDispatcher();

    void setDispatcher(Dispatcher dispatcher);
}
