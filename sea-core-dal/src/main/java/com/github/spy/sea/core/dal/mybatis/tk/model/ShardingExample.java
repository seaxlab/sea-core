package com.github.spy.sea.core.dal.mybatis.tk.model;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
@Slf4j
public class ShardingExample extends Example {

    private AbstractDynamicTableEntity entity;

    public ShardingExample(AbstractDynamicTableEntity entity) {
        super(entity.getClass());
        this.entity = entity;
    }

    @Override
    public String getDynamicTableName() {
        return entity.getDynamicTableName();
    }
}
