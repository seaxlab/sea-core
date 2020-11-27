package com.github.spy.sea.core.function.seda.stage;


import com.github.spy.sea.core.function.seda.Stage;
import com.github.spy.sea.core.function.seda.StageController;
import com.github.spy.sea.core.function.seda.event.EventHandler;

/**
 * 默认的stage。
 */
public class DefaultStage implements Stage {
    private static final int DEFAULT_CORE_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_MAX_THREAD_COUNT = DEFAULT_CORE_THREAD_COUNT * 2;

    private String context;
    private String id;
    private int coreThreads = 5;
    private int maxThreads = 25;
    private int maxQueueSize = 0;

    private EventHandler<?> eventHandler;
    private StageController controller;

    public DefaultStage(String id) {
        this(id, null);
    }

    public DefaultStage(String id, String context) {
        this(id, context, DEFAULT_CORE_THREAD_COUNT, DEFAULT_MAX_THREAD_COUNT, 0);
    }

    public DefaultStage(String id, String context, int coreThreads, int maxThreads, int maxQueueSize) {
        this.id = id;
        this.context = context;
        this.coreThreads = coreThreads < 0 ? 1 : coreThreads;
        this.maxThreads = maxThreads < 0 ? 1 : maxThreads;
        this.maxQueueSize = maxQueueSize < 0 ? 0 : maxQueueSize;
        this.controller = new ThreadPoolStageController(coreThreads, maxThreads, maxQueueSize);
    }

    public String getContext() {
        return this.context;
    }

    public String getId() {
        return this.id;
    }


    public EventHandler<?> getEventHandler() {
        return this.eventHandler;
    }

    public void setEventHandler(EventHandler<?> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public int getCoreThreads() {
        return this.coreThreads;
    }

    public void setCoreThreads(int coreThreads) {
        this.coreThreads = coreThreads;
    }

    public int getMaxThreads() {
        return this.maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public StageController getController() {
        return this.controller;
    }

    public void setController(StageController controller) {
        this.controller = controller;
    }

}
