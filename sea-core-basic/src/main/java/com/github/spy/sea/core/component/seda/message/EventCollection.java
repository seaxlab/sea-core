package com.github.spy.sea.core.component.seda.message;

import com.github.spy.sea.core.component.seda.Message;
import com.github.spy.sea.core.component.seda.event.Event;

import java.util.Collection;

/**
 * This extension of {@link Message} represents multiple source {@link Event}s.
 */
public interface EventCollection extends Message {

    /**
     * @return the collected {@link Event}s.
     */
    Collection<Event> getEvents();

}
