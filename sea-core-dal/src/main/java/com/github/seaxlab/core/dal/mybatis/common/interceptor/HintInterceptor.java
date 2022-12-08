package com.github.seaxlab.core.dal.mybatis.common.interceptor;

import com.github.seaxlab.core.dal.context.HintContext;
import com.github.seaxlab.core.dal.mybatis.common.model.SimpleBoundSqlSource;
import com.github.seaxlab.core.util.StringUtil;
import java.sql.SQLException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/15
 * @since 1.0
 */
@Slf4j
@Intercepts({
  @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
  @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
    ResultHandler.class}),
  @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
    ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class HintInterceptor implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    String hint = HintContext.get();

    if (StringUtil.isNotEmpty(hint)) {
      if (log.isDebugEnabled()) {
        log.debug("set hint config={}", hint);
      }
      String sql = getSqlByInvocation(invocation);
      String newSql = hint + " " + sql;
      resetSql2Invocation(invocation, newSql);
    } else {
      if (log.isDebugEnabled()) {
        log.debug("no hint config");
      }
    }

    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {

  }


  /**
   * 获取当前sql
   *
   * @param invocation
   * @return
   */
  private String getSqlByInvocation(Invocation invocation) {
    final Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    Object parameterObject = args[1];
    BoundSql boundSql = ms.getBoundSql(parameterObject);
    return boundSql.getSql();
  }

  /**
   * 将sql重新设置到invocation中
   *
   * @param invocation
   * @param sql
   * @throws SQLException
   */
  private void resetSql2Invocation(Invocation invocation, String sql) throws SQLException {
    final Object[] args = invocation.getArgs();
    MappedStatement statement = (MappedStatement) args[0];
    Object parameterObject = args[1];
    BoundSql boundSql = statement.getBoundSql(parameterObject);
    MappedStatement newStatement = newMappedStatement(statement, new SimpleBoundSqlSource(boundSql));
    MetaObject msObject = MetaObject.forObject(newStatement, new DefaultObjectFactory(),
      new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
    msObject.setValue("sqlSource.boundSql.sql", sql);
    args[0] = newStatement;
  }


  private MappedStatement newMappedStatement(MappedStatement ms, SqlSource sqlSource) {
    MappedStatement.Builder builder =
      new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource, ms.getSqlCommandType());
    builder.resource(ms.getResource());
    builder.fetchSize(ms.getFetchSize());
    builder.statementType(ms.getStatementType());
    builder.keyGenerator(ms.getKeyGenerator());
    if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
      StringBuilder keyProperties = new StringBuilder();
      for (String keyProperty : ms.getKeyProperties()) {
        keyProperties.append(keyProperty).append(",");
      }
      keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
      builder.keyProperty(keyProperties.toString());
    }
    builder.timeout(ms.getTimeout());
    builder.parameterMap(ms.getParameterMap());
    builder.resultMaps(ms.getResultMaps());
    builder.resultSetType(ms.getResultSetType());
    builder.cache(ms.getCache());
    builder.flushCacheRequired(ms.isFlushCacheRequired());
    builder.useCache(ms.isUseCache());

    return builder.build();
  }

}
