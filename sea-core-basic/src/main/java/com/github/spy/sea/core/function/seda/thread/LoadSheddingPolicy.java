package com.github.spy.sea.core.function.seda.thread;


import com.github.spy.sea.core.function.seda.stage.RuntimeStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class LoadSheddingPolicy implements RejectedExecutionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoadSheddingPolicy.class);
    private RuntimeStage runtimeStage;

    public LoadSheddingPolicy(RuntimeStage runtimeStage) {
        this.runtimeStage = runtimeStage;
    }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        LOGGER.info("Discarded execution for stage [" + this.runtimeStage.getId() + "]...");
    }

    public void setStageContext(RuntimeStage runtimeStage) {
        this.runtimeStage = runtimeStage;
    }

}
