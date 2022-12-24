package com.github.seaxlab.core.dal.mybatis.tk.mapper.provider;

import com.github.seaxlab.core.dal.mybatis.tk.util.DynamicSqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * 选取最大记录数，该SQL还不稳定
 *
 * @author spy
 * @version 1.0 2021/4/26
 * @since 1.0
 */
@Slf4j
public class SelectMaxProviderExt extends MapperTemplate {

  public SelectMaxProviderExt(Class<?> mapperClass, MapperHelper mapperHelper) {
    super(mapperClass, mapperHelper);
  }


  public String selectMax(MappedStatement ms) {
    Class<?> entityClass = this.getEntityClass(ms);
    String tableName = this.tableName(entityClass);

    // 将返回值设置成ResultMap
    setResultType(ms, entityClass);

    StringBuilder sql = new StringBuilder();
    sql.append(DynamicSqlUtil.selectTableAColumns(entityClass));
    sql.append(" FROM ");
    sql.append(tableName).append(" AS a,");
    sql.append(" ( ").append(DynamicSqlUtil.getMaxSubQuerySQL())
       // 这是什么鬼，用常量可以
//        sql.append(" where age=12 ")
       //TODO 这里不能嵌套
       .append(SqlHelper.whereAllIfColumns(entityClass, false))
       .append(" )").append(" b ");
    sql.append(DynamicSqlUtil.whereTableAJoinColumns()).append(" ");
    //TODO 这里也不能嵌套
//        sql.append(DynamicSqlUtil.whereTableAFilterColumns(entityClass, false, false));
//        sql.append(" and age=12");
    return sql.toString();
  }

  /**
   * @param ms
   * @return
   */
  public String selectMaxList(MappedStatement ms) {
    Class<?> entityClass = this.getEntityClass(ms);
    String tableName = this.tableName(entityClass);

    // 将返回值设置成ResultMap
    setResultType(ms, entityClass);

    StringBuilder sql = new StringBuilder();
    sql.append(DynamicSqlUtil.selectTableAColumns(entityClass));
    sql.append(" FROM ");
    sql.append(tableName).append(" a, ");
    sql.append("( ").append(DynamicSqlUtil.getMaxSubQuerySQL()).append(")").append(" b ");
    sql.append(DynamicSqlUtil.whereTableAJoinColumns());
//        sql.append(DynamicSqlUtil.whereTableAFilterColumns(entityClass, false, false));

    return sql.toString();
  }

//    SELECT
//        a.*
//    FROM
//        sys_user a,
//        (SELECT
//            MAX(USER_ID) AS 'MAX_USER_ID'
//        FROM
//            sys_user) b
//    WHERE
//        a.USER_ID = b.MAX_USER_ID;
}
