package com.github.spy.sea.core.dal.mybatis.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * string json
 *
 * @author spy
 * @version 1.0 2020/11/9
 * @since 1.0
 */
@Slf4j
@MappedTypes({String.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringJsonTypeHandler extends JSONTypeHandler<String> {

}
