package com.github.seaxlab.core.dal.mybatis.common.interceptor;

import com.github.seaxlab.core.dal.mybatis.common.util.SqlUtil;
import com.google.common.base.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * mybatis sql log interceptor
 *
 * @author spy
 * @version 1.0 2020/4/27
 * @since 1.0
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
  @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
    ResultHandler.class}),
  @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
    ResultHandler.class, CacheKey.class, BoundSql.class})})
public class MybatisSqlLogInterceptor implements Interceptor {

  private static final int MAX_RECORD_SIZE = 5000;
  //10s
  private static final int MIN_COST_TIME = 1000 * 5;
  private static final String UNKNOWN_STMT_ID = "UNKNOWN_STMT_ID";

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    if (log.isDebugEnabled()) {
      log.debug("mybatis sql log interceptor begin.");
    }
    //
    Stopwatch stopwatch = Stopwatch.createStarted();
    Object returnValue;
    String stmtId = "";
    BoundSql boundSql = null;
    Configuration configuration = null;
    try {
      MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
      stmtId = StringUtils.defaultIfBlank(mappedStatement.getId(), UNKNOWN_STMT_ID);

      Object parameter = null;
      if (invocation.getArgs().length > 1) {
        parameter = invocation.getArgs()[1];
      }
      boundSql = mappedStatement.getBoundSql(parameter);
      configuration = mappedStatement.getConfiguration();

      returnValue = invocation.proceed();
      dealReturnValue(returnValue, stmtId);
    } finally {
      stopwatch.stop();

      String sql = SqlUtil.getFinalSql(configuration, boundSql);
      long cost = stopwatch.elapsed(TimeUnit.MILLISECONDS);
      log.warn("sql=[{}],cost={}ms", sql, cost);
//      if (cost > MIN_COST_TIME) {
//        log.warn("db execute, cost={}ms", cost);
//      }
    }

    if (log.isDebugEnabled()) {
      log.debug("mybatis sql log interceptor end.");
    }

    return returnValue;
  }


  private void dealReturnValue(Object returnValue, String stmtId) {
    if (returnValue == null) {
      return;
    }

    if (returnValue instanceof ArrayList) {
      List data = (ArrayList) returnValue;
      if (data.size() >= MAX_RECORD_SIZE) {
        log.warn("DB501 large record, {}", stmtId);
      }
    }

  }


  @Override
  public Object plugin(Object target) {

    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {

  }
}
