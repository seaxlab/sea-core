package com.github.seaxlab.core.dal.mybatis.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * mybatis sql exception interceptor
 *
 * @author spy
 * @version 1.0 2022/12/16
 * @since 1.0
 */
@Slf4j
@Intercepts({ //
  @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
  @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
    ResultHandler.class}),
  @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
    ResultHandler.class, CacheKey.class, BoundSql.class})})
public class MybatisSqlExceptionInterceptor implements Interceptor {

  private static final String UNKNOWN_STMT_ID = "UNKNOWN_STMT_ID";

  private BiConsumer<Throwable, String> exceptionConsumer;

  public MybatisSqlExceptionInterceptor() {

  }

  public MybatisSqlExceptionInterceptor(BiConsumer<Throwable, String> exceptionConsumer) {
    this.exceptionConsumer = exceptionConsumer;
  }

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    //
    Object returnValue;
    String stmtId = "";
    try {
      MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
      stmtId = StringUtils.defaultIfBlank(mappedStatement.getId(), UNKNOWN_STMT_ID);
      //
      returnValue = invocation.proceed();
    } catch (Throwable t) {
      handleException(t, stmtId);
      throw t;
    }

    return returnValue;
  }


  @Override
  public Object plugin(Object target) {

    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {

  }


  //-----------------------private
  private void handleException(Throwable t, String stmtId) {
    if (this.exceptionConsumer != null) {
      try {
        this.exceptionConsumer.accept(t, stmtId);
      } catch (Exception e) {
        log.warn("fail to execute mybatis sql exception consumer.");
      }
    }
  }

}
