package com.github.seaxlab.core.dal.mybatis.common.handler;

import java.util.List;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * integer list json
 *
 * @author spy
 * @version 1.0 2021/5/29
 * @since 1.0
 */
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class IntegerListJsonTypeHandler extends ListJsonTypeHandler<Integer> {

}
