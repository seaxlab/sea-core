package com.github.spy.sea.core.dal.mybatis.tk.sharding;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
@Data
public class ShardingExtra {

    /**
     * 表总数
     */
    private int tableCount;

    /**
     * 前缀位数
     */
    private int prefixCount;

    /**
     * 后缀位数
     */
    private int suffixCount;

}
