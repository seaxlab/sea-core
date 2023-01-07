package com.github.seaxlab.core.dal.mybatis.tk.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/5
 * @since 1.0
 */
@Slf4j
public class SqlUtil {

  /**
   * get final execute sql
   *
   * @param configuration
   * @param boundSql
   * @return
   */
  public static final String getFinalSql(Configuration configuration, BoundSql boundSql) {
    try {
      if (configuration == null || boundSql == null) {
        return "";
      }

      String sql = boundSql.getSql();
      if (sql == null) {
        return "";
      }

      // remove multi space
      sql = sql.replaceAll("[\\s]+", " ");

      TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
      Object parameterObject = boundSql.getParameterObject();

      List<Object> paramList = new ArrayList<>();

      // 通过BoundSql获取参数映射列表
      List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
      // 仅参数映射列表非空时处理
      if (parameterMappings != null) {
        // 遍历参数映射列表
        for (int i = 0; i < parameterMappings.size(); i++) {
          // 获取当前参数映射
          ParameterMapping parameterMapping = parameterMappings.get(i);
          // 仅参数类型不是OUT时处理，示例为IN
          if (parameterMapping.getMode() != ParameterMode.OUT) {
            Object value;
            // 获取参数名称
            String propertyName = parameterMapping.getProperty();
            // 额外参数是否包含当前属性，当前是_parameter和_databaseId
            // issue #448 ask first for additional params
            if (boundSql.hasAdditionalParameter(propertyName)) {
              value = boundSql.getAdditionalParameter(propertyName);
            } else if (parameterObject == null) {
              value = null;
            }
            // 类型处理器是否包含参数类型，当前参数类型为ParamMap
            else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
              value = parameterObject;
            } else {
              // 创建MetaObject对象
              MetaObject metaObject = configuration.newMetaObject(parameterObject);
              // 获取属性对应的值
              value = metaObject.getValue(propertyName);
            }
            // 获取参数映射的类型处理器。这里是UnknownTypeHandler
//                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
            // 获取jdbcType
//                    JdbcType jdbcType = parameterMapping.getJdbcType();
//                    if (value == null && jdbcType == null) {
//                        jdbcType = configuration.getJdbcTypeForNull();
//                    }
//                    try {
//                        //通过类型处理器为ps参数赋值
//                        typeHandler.setParameter(ps, i + 1, value, jdbcType);
//                    } catch (TypeException e) {
//                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
//                    } catch (SQLException e) {
//                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
//                    }
            // value may be null
            paramList.add(value);
          }
        }
      }

      return getSqlEscaped(replaceMark(sql, paramList));
    } catch (Exception e) {
      log.error("fail to get final execute sql.", e);
    }
    return "";
  }

  /**
   * 替换标识符
   *
   * @param sql
   * @param paramList
   * @return
   */
  private static String replaceMark(String sql, List<Object> paramList) {

    if (sql == null) {
      return "";
    }
    if (paramList == null || paramList.isEmpty()) {
      return sql;
    }

    while (sql.indexOf("?") != -1 && paramList.size() > 0) {
      Object param = paramList.get(0);
      sql = sql.replaceFirst("\\?", param == null ? "null" : param.toString());
      paramList.remove(0);
    }
    return sql;
    //替换不了的
//        return sql.replaceAll("(\r?\n(\\s*\r?\n)+)", "missing");
  }


  private static final int DIGEST_LOG_SQL_LIMIT = 4096;

  public static String getSqlEscaped(String sql) {
    String limitSql = sql;
    if (limitSql != null && limitSql.length() > DIGEST_LOG_SQL_LIMIT) {
      limitSql = limitSql.substring(0, DIGEST_LOG_SQL_LIMIT) + " ...";
    }
    return limitSql;
  }


}
