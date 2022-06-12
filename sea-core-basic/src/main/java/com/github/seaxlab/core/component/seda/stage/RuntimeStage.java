package com.github.seaxlab.core.component.seda.stage;

import com.ctc.wstx.shaded.msv.org_isorelax.dispatcher.impl.DispatcherImpl;
import com.github.seaxlab.core.component.seda.Stage;
import com.github.seaxlab.core.component.seda.StageController;
import com.github.seaxlab.core.component.seda.event.EventHandler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@link RuntimeStage} represents the {@link DispatcherImpl} internal configuration metadata to route, track stats,
 * execute.
 *
 * @see {@link Stage}
 * @see {@link EventHandler}
 * @see {@link ThreadPoolExecutor}
 */
public class RuntimeStage {

    private final Stage stage;
    private final String context;
    private final String id;
    @SuppressWarnings("rawtypes")
    private final EventHandler eventHandler;


    private final StageController controller;

    public RuntimeStage(Stage stage) {
        this.stage = stage;
        this.context = stage.getContext();
        this.id = stage.getId();
        this.eventHandler = stage.getEventHandler();
        this.controller = stage.getController();
    }

    public Stage getStage() {
        return this.stage;
    }

    public String getId() {
        return this.id;
    }

    public String getContext() {
        return this.context;
    }

    @SuppressWarnings("rawtypes")
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    public StageController getController() {
        return this.controller;
    }

}
