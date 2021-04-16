package com.github.spy.sea.core.dal.mybatis.tk.util;

import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.EntityColumn;
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
}
