package com.github.spy.sea.core.dal.mybatis.tk.sharding;

import com.github.spy.sea.core.util.NumberUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;


/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/13
 * @since 1.0
 */
@Slf4j
public abstract class AbstractShardingEntity implements IDynamicTableName {

    @Override
    @Transient // tk中需要忽略的属性/方法
    public String getDynamicTableName() {
        StringBuilder strBuilder = new StringBuilder(this.getClass().getAnnotation(Table.class).name());
        Field[] fields = this.getClass().getDeclaredFields();
        int count = fields.length;
        for (int i = 0; i < count; i++) {
            if (fields[i].isAnnotationPresent(ShardingKey.class)) {

                // 分表总数
                int tableCount = fields[i].getAnnotation(ShardingKey.class).value();
                // 数字长度
                int length = NumberUtil.length(tableCount);

                if (tableCount > 1) {
                    try {
                        fields[i].setAccessible(true);
                        Object obj = fields[i].get(this);

                        //TODO 分库算法
                        int order = Math.abs(obj.hashCode()) % tableCount;

                        // such as order_001
                        return strBuilder.append("_").append(StringUtil.addZeroLeft(order, length)).toString();
                    } catch (Exception e) {
                        log.error("fail to get dynamic table name", e);
                    }
                }
            }

        }

        return strBuilder.toString();
    }
}
