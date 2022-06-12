package com.github.seaxlab.core.component.seda;

import com.github.seaxlab.core.component.seda.event.Event;
import com.github.seaxlab.core.component.seda.exception.InvalidStageException;

import java.util.List;

/**
 * {@link Dispatcher} is the basic interface for the entry point to route messages through the {@link Stage}s.
 */
public interface Dispatcher extends Lifecycle {

    /**
     * Routes the {@link Message} to the specified {@link Stage} executing the associated {@link EventHandler}.
     *
     * @param stageId {@link Stage#getId()}
     * @param message payload.
     * @throws InvalidStageException
     */
    boolean execute(String stageId, Message message) throws InvalidStageException;

    /**
     * Routes the {@link Event} to the specified {@link Stage} executing the associated {@link EventHandler}.
     *
     * @throws InvalidStageException
     */
    boolean execute(Event event) throws InvalidStageException;

    /**
     * {@link Stage} list of supported flow {@link EventHandler}.
     *
     * @param stages {@link Stage} list.
     */
    void setStages(List<Stage> stages);

    /**
     * Gets the configured list of {@link Stage}s.
     *
     * @return immutable list of mutable {@link Stage}s.
     */
    List<Stage> getStages();

    /**
     * @return The dispatcher context identifier.
     */
    String getContext();

    /**
     * This parameter sets a context, to allow different flows running at the same time and it is propagated in logs &
     * JXM metrics.
     *
     * @param context
     */
    void setContext(String context);

}
