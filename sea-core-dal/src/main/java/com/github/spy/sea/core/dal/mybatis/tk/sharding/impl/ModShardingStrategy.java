package com.github.spy.sea.core.dal.mybatis.tk.sharding.impl;

import com.github.spy.sea.core.dal.mybatis.tk.sharding.ShardingExtra;
import com.github.spy.sea.core.dal.mybatis.tk.sharding.ShardingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
@Slf4j
public class ModShardingStrategy implements ShardingStrategy<Long> {

    @Override
    public String execute(Long value, ShardingExtra extra) {


        return null;
    }
}
