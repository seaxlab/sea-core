package com.github.seaxlab.core.dal.mybatis.tk.util;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.ListUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@Slf4j
public class TkOgnlUtil {

  /**
   * 插入值
   *
   * @param records
   * @param parameter
   * @return
   */
  public static String onDuplicateKeyInsertColumns(List records, Object parameter) {
    if (ListUtil.isEmpty(records)) {
      ExceptionHandler.publishMsg("records is null");
      return "";
    }

    if (parameter == null) {
      ExceptionHandler.publishMsg("insert columns cannot be null.");
      return "";
    }

    Object record = records.get(0);
    Set<EntityColumn> allColumns = EntityHelper.getColumns(record.getClass());
    // to db column
    Map<String, String> columnMap = allColumns.stream()
                                              .collect(Collectors.toMap(EntityColumn::getProperty, EntityColumn::getColumn));

    if (parameter instanceof String[]) {
      String[] columns = (String[]) parameter;

      List<String> list = Arrays.stream(columns).map(item -> columnMap.get(item)).collect(Collectors.toList());
      return "(" + String.join(",", list) + ")";
    }
    return "";
  }

  public static String onDuplicateKeyInsertFieldColumns(Object parameter) {
//        "(#{column1},#{column2},#{column3})"
    if (parameter == null) {
      ExceptionHandler.publishMsg("insert field columns cannot be null.");
      return "";
    }

    if (parameter instanceof String[]) {
      String[] columns = (String[]) parameter;
      List<String> list = Arrays.stream(columns)
                                .map(column -> "#{item." + column + "}")
                                .collect(Collectors.toList());
      return "(" + String.join(",", list) + ")";
    }

    return "";
  }

  public static String onDuplicateKeyUpdateColumns(List records, Object parameter) {
    if (ListUtil.isEmpty(records)) {
      ExceptionHandler.publishMsg("records is null");
      return "";
    }

    if (parameter == null) {
      ExceptionHandler.publishMsg("on duplicate key columns cannot be null.");
      return "";
    }

    Object record = records.get(0);
    Set<EntityColumn> allColumns = EntityHelper.getColumns(record.getClass());
    // to db column
    Map<String, String> columnMap = allColumns.stream()
                                              .collect(Collectors.toMap(EntityColumn::getProperty, EntityColumn::getColumn));

    if (parameter instanceof String[]) {
      String[] columns = (String[]) parameter;

      List<String> list = new ArrayList<>(columns.length);
      for (int i = 0; i < columns.length; i++) {
        String column = columns[i];
        column = columnMap.get(column);
        column = column.replaceAll("`", "");
        list.add(column + "=VALUES(" + column + ")");
      }
      return String.join(",", list);
    }

    return "";
  }

  /**
   * 获取子查询sql
   *
   * @param record
   * @param maxColumn
   * @return
   */
  public static String getMaxSubQuerySQL(Object record, String maxColumn) {
    EntityTable entityTable = EntityHelper.getEntityTable(record.getClass());
    Set<EntityColumn> allColumns = EntityHelper.getColumns(record.getClass());
    // to db column
    Map<String, String> columnMap = getColumnMap(allColumns);
    String maxColumnName = columnMap.get(maxColumn);
    Preconditions.checkNotNull(maxColumnName, "列属性不能为空");
//        SELECT
    //        MAX(USER_ID) AS 'MAX_USER_ID'
    //    FROM
    //        sys_user
    //    where id=1
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT MAX(").append(maxColumnName.replaceAll("`", "")).append(") AS ").append("'")
       .append(DynamicSqlUtil.DEFAULT_MAX_SUB_QUERY_FILED).append("' ");
    sql.append("FROM ").append(entityTable.getName()).append(" ");

    return sql.toString();
  }

  public static String getOrderByProperty(Object record, String orderByProperty) {
    Set<EntityColumn> allColumns;
    if (record instanceof Example) {
      Example example = (Example) record;
      allColumns = EntityHelper.getColumns(example.getEntityClass());
    } else {
      allColumns = EntityHelper.getColumns(record.getClass());
    }

    // to db column
    Map<String, String> columnMap = getColumnMap(allColumns);
    String columnName = columnMap.get(orderByProperty);

    return columnName.replaceAll("`", "");
  }

  public static String whereTableAColumns(Object record, String maxColumn) {
    //TODO 这里record不能为空，否则就不知道类型了
//        EntityTable entityTable = EntityHelper.getEntityTable(record.getClass());
    Set<EntityColumn> allColumns = EntityHelper.getColumns(record.getClass());
    // to db column
    Map<String, String> columnMap = getColumnMap(allColumns);
    String maxColumnName = columnMap.get(maxColumn);

    StringBuilder sql = new StringBuilder();
    sql.append(" WHERE ")
       .append("a.").append(maxColumnName.replaceAll("`", ""))
       .append("=").append("b.").append(DynamicSqlUtil.DEFAULT_MAX_SUB_QUERY_FILED);

    return sql.toString();
  }


  /**
   * 注意这里返回的是`id`,`code`
   *
   * @param allColumns
   * @return
   */
  private static Map<String, String> getColumnMap(Set<EntityColumn> allColumns) {
    return allColumns.stream()
                     .collect(Collectors.toMap(EntityColumn::getProperty, EntityColumn::getColumn));
  }


}
