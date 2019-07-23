package com.github.spy.sea.core.script.groovy;

/**
 * 公共接口
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
public interface GroovyRule {

    /**
     * 执行方法
     *
     * @param context
     * @return
     */
    GroovyResult run(GroovyContext context);
}
