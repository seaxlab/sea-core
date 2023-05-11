package com.github.seaxlab.core.dal.mybatis.common.model;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

/**
 * simple bound sql source
 *
 * @author spy
 * @version 1.0 2021/4/15
 * @since 1.0
 */
public class SimpleBoundSqlSource implements SqlSource {

  private final BoundSql boundSql;

  public SimpleBoundSqlSource(BoundSql boundSql) {
    this.boundSql = boundSql;
  }

  @Override
  public BoundSql getBoundSql(Object parameterObject) {
    return boundSql;
  }
}
