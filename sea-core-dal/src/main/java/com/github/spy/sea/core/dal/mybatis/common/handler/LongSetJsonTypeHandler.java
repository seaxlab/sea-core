package com.github.spy.sea.core.dal.mybatis.common.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.Set;

/**
 * long set json
 *
 * @author spy
 * @version 1.0 2021/8/4
 * @since 1.0
 */
@MappedTypes({Set.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class LongSetJsonTypeHandler extends SetJsonTypeHandler<Long> {
}
