package com.github.spy.sea.core.component.tracer.sofa.simple;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-08
 * @since 1.0
 */
public enum SimpleLogEnum {

    DIGEST("simple_digest_log_name", "simple-digest.log", "simple_digest_rolling"),
    STAT("simple_stat_log_name", "simple-stat.log", "simple_stat_rolling");

    private String logNameKey;
    private String defaultLogName;
    private String rollingKey;

    SimpleLogEnum(String logNameKey, String defaultLogName, String rollingKey) {
        this.logNameKey = logNameKey;
        this.defaultLogName = defaultLogName;
        this.rollingKey = rollingKey;
    }

    public String getLogNameKey() {
        return logNameKey;
    }

    public String getDefaultLogName() {
        return defaultLogName;
    }

    public String getRollingKey() {
        return rollingKey;
    }
}