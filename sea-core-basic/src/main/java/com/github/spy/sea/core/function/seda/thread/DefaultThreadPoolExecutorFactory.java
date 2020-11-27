package com.github.spy.sea.core.function.seda.thread;


import com.github.spy.sea.core.function.seda.Dispatcher;
import com.github.spy.sea.core.function.seda.Stage;
import com.github.spy.sea.core.function.seda.stage.RuntimeStage;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Default {@link ThreadPoolExecutorFactory} that creates a {@link DispatcherThreadPoolExecutor} configured according to
 * the specified {@link Stage} and {@link Dispatcher}.
 */
public class DefaultThreadPoolExecutorFactory implements ThreadPoolExecutorFactory {

    private int coreThreadCount;
    private int maxThreadCount;
    private int maxQueueCount;

    private DefaultThreadPoolExecutorFactory() {
    }

    public DefaultThreadPoolExecutorFactory(int coreThreadCount, int maxThreadCount, int maxQueueCount) {
        this.coreThreadCount = coreThreadCount;
        this.maxThreadCount = maxThreadCount;
        this.maxQueueCount = maxQueueCount;
    }


    public ThreadPoolExecutor create(Dispatcher dispatcher, RuntimeStage runtimeStage) {
        Stage stage = runtimeStage.getStage();
        ThreadPoolExecutor executor;
        if (maxQueueCount > 0) {
            executor = new DispatcherThreadPoolExecutor(dispatcher.getContext() + "_" + stage.getId() + "_ST#",
                    coreThreadCount, maxThreadCount, maxQueueCount, new LoadSheddingPolicy(runtimeStage));
        } else {
            executor = new DispatcherThreadPoolExecutor(dispatcher.getContext() + "_" + stage.getId() + "_ST#",
                    coreThreadCount, maxThreadCount);
        }
        return executor;
    }
}
