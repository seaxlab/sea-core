package com.github.spy.sea.core.component.seda;


import com.github.spy.sea.core.component.seda.event.Event;
import com.github.spy.sea.core.component.seda.stage.RuntimeStage;

/**
 * The {@link StageController} is the responsible for the execution of the given {@link Event}, in this case, only the
 * {@link Message} because there is an instance of this class per defined {@link Stage}.
 */
public interface StageController extends DispatcherAware, Lifecycle {

    /**
     * Execution handler for the given stage.
     *
     * @param event
     */
    void execute(Event event);

    /**
     * Runtime configuration of the running {@link Stage} ({@link RuntimeStage}).
     *
     * @param runtimeStage
     */
    void setRuntimeStage(RuntimeStage runtimeStage);

}
