package com.github.seaxlab.core.dal.mybatis.tk.mapper.provider;

import com.github.seaxlab.core.dal.mybatis.tk.util.DynamicSqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@Slf4j
public class InsertOrUpdateProviderExt extends MapperTemplate {

  public InsertOrUpdateProviderExt(Class<?> mapperClass, MapperHelper mapperHelper) {
    super(mapperClass, mapperHelper);
  }

  //    insert into table (field1,field2) values (#{field1},#{field2),(#{field1},#{field2)
  //    ON DUPLICATE KEY UPDATE
  //    field2 = #{field2},
  //    field3 = #{field3}

  public String insertOrUpdateSelective(MappedStatement ms) {
    Class<?> entityClass = this.getEntityClass(ms);

    StringBuilder sql = new StringBuilder();
    sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
    sql.append(DynamicSqlUtil.getDuplicateInsertColumns());
    sql.append(" VALUES ");
    sql.append("<foreach collection='records' index='index' item='item' separator=',' >");
    sql.append(DynamicSqlUtil.getDuplicateInsertFieldColumns());
    sql.append("</foreach>");
    sql.append("\n ON DUPLICATE KEY UPDATE \n");
    sql.append(DynamicSqlUtil.getDuplicateUpdateColumns());

    return sql.toString();
  }

}
