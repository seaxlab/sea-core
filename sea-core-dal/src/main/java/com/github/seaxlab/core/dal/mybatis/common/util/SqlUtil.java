package com.github.seaxlab.core.dal.mybatis.common.util;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * sql util
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
      log.warn("fail to get final execute sql.", e);
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
  public static String replaceMark(String sql, List<Object> paramList) {

    if (sql == null) {
      return "";
    }
    if (paramList == null || paramList.isEmpty()) {
      return sql;
    }

    while (sql.indexOf("?") != -1 && paramList.size() > 0) {
      Object param = paramList.get(0);
      sql = sql.replaceFirst("\\?", parseParam(param));
      paramList.remove(0);
    }
    return sql;
    //替换不了的
//        return sql.replaceAll("(\r?\n(\\s*\r?\n)+)", "missing");
  }

  private static final String parseParam(Object param) {
    String value;
    if (param == null) {
      value = "null";
    } else {
      if (param instanceof String) {
        value = "'" + param.toString() + "'";
      } else if (param instanceof Date) {
        //TODO Date类型，有可能是date、time、datetime，这样子统一处理设置便于使用
        Date date = (Date) param;
        value = "'" + DateUtil.toString(date, DateFormatEnum.yyyy_MM_dd_HH_mm_ss) + "'";
      } else {
        value = param.toString();
      }
    }
    return value;
  }


  private static final int DIGEST_LOG_SQL_LIMIT = 4096;

  public static String getSqlEscaped(String sql) {
    String limitSql = sql;
    if (limitSql != null && limitSql.length() > DIGEST_LOG_SQL_LIMIT) {
      limitSql = limitSql.substring(0, DIGEST_LOG_SQL_LIMIT) + " ...";
    }
    return limitSql;
  }

//    private void parse(BoundSql boundSql, Map parameterMap) {
//        // remove multi space
//        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
//
//        List<Object> paramList = new ArrayList<>();
//
//        // 通过BoundSql获取参数映射列表
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        if (parameterMappings != null) {
//            Object[] parameterArray = new Object[parameterMappings.size()];
//            ParameterMapping parameterMapping = null;
//            Object value = null;
//            Object parameterObject = null;
//            MetaObject metaObject = null;
//            PropertyTokenizer prop = null;
//            String propertyName = null;
//            String[] names = null;
//            for (int i = 0; i < parameterMappings.size(); i++) {
//                parameterMapping = parameterMappings.get(i);
//                if (parameterMapping.getMode() != ParameterMode.OUT) {
//                    propertyName = parameterMapping.getProperty();
//                    names = propertyName.split("\\.");
//                    if (propertyName.indexOf(".") != -1 && names.length == 2) {
//                        parameterObject = parameterMap.get(names[0]);
//                        propertyName = names[1];
//                    } else if (propertyName.indexOf(".") != -1 && names.length == 3) {
//                        parameterObject = parameterMap.get(names[0]); // map
//                        if (parameterObject instanceof Map) {
//                            parameterObject = ((Map) parameterObject).get(names[1]);
//                        }
//                        propertyName = names[2];
//                    } else {
//                        parameterObject = parameterMap.get(propertyName);
//                    }
//                    metaObject = parameterMap == null ? null : MetaObject.forObject(parameterObject);
//                    prop = new PropertyTokenizer(propertyName);
//                    if (parameterObject == null) {
//                        value = null;
//                    } else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
//                        value = parameterObject;
//                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
//                        value = boundSql.getAdditionalParameter(propertyName);
//                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
//                        value = boundSql.getAdditionalParameter(prop.getName());
//                        if (value != null) {
//                            value = MetaObject.forObject(value).getValue(propertyName.substring(prop.getName().length()));
//                        }
//                    } else {
//                        value = metaObject == null ? null : metaObject.getValue(propertyName);
//                    }
//                    parameterArray[i] = value;
//                }
//            }
//        }
//    }

}
