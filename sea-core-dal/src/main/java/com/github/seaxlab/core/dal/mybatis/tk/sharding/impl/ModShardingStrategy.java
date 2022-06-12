package com.github.seaxlab.core.dal.mybatis.tk.sharding.impl;

import com.github.seaxlab.core.dal.mybatis.tk.sharding.ShardingExtra;
import com.github.seaxlab.core.dal.mybatis.tk.sharding.ShardingStrategy;
import com.github.seaxlab.core.util.NumberUtil;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 固定长度的后缀,不足部分用0填充
 * id=1100 -> order_010
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
@Slf4j
public class ModShardingStrategy implements ShardingStrategy<Long> {

    @Override
    public String execute(Long value, ShardingExtra extra) {
        int length = NumberUtil.length(extra.getTableCount());
        long sharding = value % extra.getTableCount();

        return StringUtil.addZeroLeft(sharding, length);
    }
}
