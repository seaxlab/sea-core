package com.github.spy.sea.core.dal.mybatis.tk.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/29
 * @since 1.0
 */
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringListJsonTypeHandler extends ListJsonTypeHandler<String> {
}
