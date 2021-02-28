package com.github.spy.sea.core.component.seda.thread;


import com.github.spy.sea.core.component.seda.Dispatcher;
import com.github.spy.sea.core.component.seda.stage.RuntimeStage;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Creates a custom {@link ThreadPoolExecutor} for a given {@link Stage} and contextual {@link Dispatcher}.
 */
public interface ThreadPoolExecutorFactory {

    ThreadPoolExecutor create(Dispatcher dispatcher, RuntimeStage runtimeStage);

}
