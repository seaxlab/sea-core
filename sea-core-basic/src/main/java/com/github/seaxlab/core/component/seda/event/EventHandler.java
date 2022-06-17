package com.github.seaxlab.core.component.seda.event;


import com.github.seaxlab.core.component.seda.Message;
import com.github.seaxlab.core.component.seda.stage.RoutingOutcome;

/**
 * {@link EventHandler} represents the unit of work behind the {@link Stage}.
 *
 * @see {@link Message}
 * @see {@link RoutingOutcome}
 */
public interface EventHandler<T extends Message> {

    /**
     * Execution of {@link Stage} {@link EventHandler}.
     *
     * @param message payload received.
     * @return {@link RoutingOutcome} specifiying the next {@link Stage} and the {@link Message} passsed to it or them.
     * Null return value is allowed, mainly to mark the end of a execution path.
     */
    RoutingOutcome execute(T message);

}
