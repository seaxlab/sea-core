package com.github.spy.sea.core.dal.mybatis.tk.sharding;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
public interface ShardingStrategy<T> {

    /**
     * 返回数字字符串
     *
     * @param value 分表字段值
     * @param extra 扩展参数
     * @return 数字字符串
     */
    String execute(T value, ShardingExtra extra);
}
