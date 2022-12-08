package com.github.seaxlab.core.dal.mybatis.tk.sharding.impl;

import com.github.seaxlab.core.dal.mybatis.tk.sharding.ShardingExtra;
import com.github.seaxlab.core.dal.mybatis.tk.sharding.ShardingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * 后缀长度不固定
 * <p>
 * order_0 order_1 order_10
 * </p>
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
@Slf4j
public class SimpleModShardingStrategy implements ShardingStrategy<Long> {

  @Override
  public String execute(Long value, ShardingExtra extra) {
    long sharding = value % extra.getTableCount();

    return "" + sharding;
  }
}
