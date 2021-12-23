package com.github.spy.sea.core.component.perf;

/**
 * profiler的开关这是，目前仅仅是一个静态单例，需要外部来触发修改
 */
public class ProfilerConfig {

    private static ProfilerConfig instance = new ProfilerConfig();

    public static ProfilerConfig getInstance() {
        return instance;
    }


    /**
     * 是否打开打印日志的开关
     */
    private boolean openProfilerTree = false;

    /**
     * 超时时间，单位ms，默认500ms，超过这个时间才会打印
     */
    private long invokeTimeout = 500;

    /**
     * 是否打印纳秒
     *
     * @return
     */
    private boolean openProfilerNanoTime = false;

    public boolean isOpenProfilerTree() {
        return openProfilerTree;
    }

    public void setOpenProfilerTree(boolean openProfilerTree) {
        this.openProfilerTree = openProfilerTree;
    }

    public long getInvokeTimeout() {
        return invokeTimeout;
    }

    public void setInvokeTimeout(long invokeTimeout) {
        this.invokeTimeout = invokeTimeout;
    }

    public boolean isOpenProfilerNanoTime() {
        return openProfilerNanoTime;
    }

    public void setOpenProfilerNanoTime(boolean openProfilerNanoTime) {
        this.openProfilerNanoTime = openProfilerNanoTime;
    }


}
