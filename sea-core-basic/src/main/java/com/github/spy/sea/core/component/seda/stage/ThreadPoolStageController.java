package com.github.spy.sea.core.component.seda.stage;

import com.github.spy.sea.core.component.seda.Dispatcher;
import com.github.spy.sea.core.component.seda.Message;
import com.github.spy.sea.core.component.seda.StageController;
import com.github.spy.sea.core.component.seda.TimeoutEnabled;
import com.github.spy.sea.core.component.seda.event.Event;
import com.github.spy.sea.core.component.seda.event.RunnableEventHandlerWrapper;
import com.github.spy.sea.core.component.seda.thread.DefaultThreadPoolExecutorFactory;
import com.github.spy.sea.core.component.seda.thread.ThreadPoolExecutorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolStageController implements StageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolStageController.class);

    public static final String THREAD_POOL_EXECUTOR_MXBEAN_PREFIX = "com.github.spy.sea.core.seda.thread:type=ThreadPoolExecutor,name=tpe-";

    protected ThreadPoolExecutorFactory executorFactory;

    protected Dispatcher dispatcher;
    protected RuntimeStage runtimeStage;
    protected ExecutorService executor;

    protected int timeout = -1;
    protected int configuredTimeout = this.timeout;
    protected int timeoutMonitorThreadCount = 1;
    protected ScheduledThreadPoolExecutor timeoutMonitorExecutor = null;

    protected volatile boolean started = false;
    protected volatile boolean stopRequired = false;

    private int coreThreadCount;
    private int maxThreadCount;
    private int maxQueueCount;

    private ThreadPoolStageController() {
    }

    public ThreadPoolStageController(int coreThreadCount, int maxThreadCount, int maxQueueCount) {
        this.coreThreadCount = coreThreadCount;
        this.maxThreadCount = maxThreadCount;
        this.maxQueueCount = maxQueueCount;
        executorFactory = new DefaultThreadPoolExecutorFactory(coreThreadCount, maxThreadCount, maxQueueCount);
    }

    @Override
    public void execute(Event event) {
        Message message = event.getMessage();

        // Allow to customize the runnable.
        Runnable runnable = this.customize(event, new RunnableEventHandlerWrapper(this.dispatcher, this.runtimeStage, event));
        assert runnable != null : "Customized runnable cannot be null!";

        Future<?> future = this.executor.submit(runnable);

        this.handleTimeoutControl(message, future);
    }

    /**
     * Customizable {@link Runnable} creation to wrap around the original {@link RunnableEventHandlerWrapper}. <br/>
     * Expert use only!
     *
     * @param event
     * @return
     */
    protected Runnable customize(Event event, RunnableEventHandlerWrapper originalRunnable) {
        return originalRunnable;
    }

    private void handleTimeoutControl(final Message message, final Future<?> future) {
        if (this.timeoutMonitorExecutor == null) {
            return;
        }

        int runningTimeout = this.evaluateTimeout(message);
        if (runningTimeout > 0 && !future.isDone() && !future.isCancelled()) {
            this.timeoutMonitorExecutor.schedule(new Runnable() {
                private final Future<?> internalFuture = future;
                private final Message internalData = message;

                public void run() {
                    if (!this.internalFuture.isDone() && !this.internalFuture.isCancelled()) {
                        this.internalFuture.cancel(true);
                        LOGGER.debug("Cancelling work @" + this.internalData.hashCode());
                    } else {
                        LOGGER.debug("Work @" + this.internalData.hashCode() + " was already cancelled/done!");
                    }
                }
            }, this.configuredTimeout, TimeUnit.MILLISECONDS);
        }
    }

    private int evaluateTimeout(Message message) {
        return (message instanceof TimeoutEnabled) ? ((TimeoutEnabled) message).getTimeoutInMillis()
                : this.configuredTimeout;
    }

    @Override
    public void start() {
        assert this.dispatcher != null : "Dispatcher must be specified.";
        assert this.runtimeStage != null : "RuntimeStage must be specified.";
        assert this.executorFactory != null : "ExecutorFactory must be specified.";
        assert this.timeoutMonitorThreadCount > 0 : "timeoutMonitorThreadCount must be > 0.";

        this.executor = this.executorFactory.create(this.dispatcher, this.runtimeStage);

        if (this.timeout > 0) {
            this.timeoutMonitorExecutor = new ScheduledThreadPoolExecutor(this.timeoutMonitorThreadCount);
        } else {
            this.timeoutMonitorExecutor = null;
        }
        this.configuredTimeout = this.timeout;

        this.started = true;
    }

    @Override
    public void stop() {
        this.stopRequired = true;

        // shutdown the threadpool...
        List<Runnable> remaining = this.executor.shutdownNow();
        int remainingCount = remaining != null ? remaining.size() : 0;

        LOGGER.info("Shutdown processed for [" + this.runtimeStage.getId() + "], remaining runnables: " + remainingCount);

        if (this.timeoutMonitorExecutor != null) {
            List<Runnable> monitorRemaining = this.timeoutMonitorExecutor.shutdownNow();
            int monitorRemainingCount = monitorRemaining != null ? monitorRemaining.size() : 0;

            LOGGER.info("Shutdown processed for timeoutMonitorExecutor [" + this.runtimeStage.getId()
                    + "], remaining runnables: " + monitorRemainingCount);
        }

        this.started = false;
        this.stopRequired = false;
    }

    @Override
    public Dispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Override
    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void setRuntimeStage(RuntimeStage runtimeStage) {
        this.runtimeStage = runtimeStage;
        // this.executor = runtimeStage.getExecutor();
    }

    public void setExecutorFactory(ThreadPoolExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }

    public ThreadPoolExecutorFactory getExecutorFactory() {
        return this.executorFactory;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setTimeoutMonitorThreadCount(int timeoutMonitorThreadCount) {
        this.timeoutMonitorThreadCount = timeoutMonitorThreadCount;
    }

    @Override
    public boolean isRunning() {
        return this.started && !this.stopRequired;
    }

}
