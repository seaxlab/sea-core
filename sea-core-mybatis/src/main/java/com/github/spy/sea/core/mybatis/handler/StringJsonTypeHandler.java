package com.github.spy.sea.core.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * module name
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
