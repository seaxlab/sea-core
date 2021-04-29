package com.github.spy.sea.core.dal.mybatis.tk.util;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.annotation.Version;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@Slf4j
public class DynamicSqlUtil {
    private static final String OGNL_FQN = TkOgnlUtil.class.getName();

    public static final String DEFAULT_MAX_SUB_QUERY_FILED = "SEA_MAX_VALUE";
    public static final String DEFAULT_MIN_SUB_QUERY_FILED = "SEA_MIN_VALUE";

    /**
     * on duplicate update 获取插入的db key
     *
     * @return
     */
    public static String getDuplicateInsertColumns() {
        return "${@" + OGNL_FQN + "@onDuplicateKeyInsertColumns(records,insertColumns)}";
    }

    /**
     * on duplicate update 实体属性
     *
     * @return
     */
    public static String getDuplicateInsertFieldColumns() {
        return "${@" + OGNL_FQN + "@onDuplicateKeyInsertFieldColumns(insertColumns)}";
    }


    /**
     * on duplicate update 需要更新的db key
     *
     * @return
     */
    public static String getDuplicateUpdateColumns() {
        return "${@" + OGNL_FQN + "@onDuplicateKeyUpdateColumns(records,updateColumns)}";
    }


    public static String getMaxSubQuerySQL() {
        return "${@" + OGNL_FQN + "@getMaxSubQuerySQL(record,maxColumn)}";
    }

    /**
     * 获取以表别名为a的字段列
     * <pre>
     *     select a.id,a.code,a.name....
     * </pre>
     *
     * @param entityClass
     * @return
     */
    public static String selectTableAColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
//        sql.append(getAllColumns("a", entityClass));
        sql.append(SqlHelper.getAllColumns(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    public static String whereTableAJoinColumns() {
        return "${@" + OGNL_FQN + "@whereTableAColumns(record,maxColumn)}";
    }

    public static String whereTableAFilterColumns(Class<?> entityClass, boolean empty, boolean useVersion) {
        StringBuilder sql = new StringBuilder();
        boolean hasLogicDelete = false;

        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        EntityColumn logicDeleteColumn = SqlHelper.getLogicDeleteColumn(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnSet) {
            if (!useVersion || !column.getEntityField().isAnnotationPresent(Version.class)) {
                // 逻辑删除，后面拼接逻辑删除字段的未删除条件
                if (logicDeleteColumn != null && logicDeleteColumn == column) {
                    hasLogicDelete = true;
                    continue;
                }
                sql.append(SqlHelper.getIfNotNull(column, " AND " + column.getColumnEqualsHolder(), empty));
            }
        }
        if (useVersion) {
            sql.append(SqlHelper.whereVersion(entityClass));
        }
        if (hasLogicDelete) {
            sql.append(SqlHelper.whereLogicDelete(entityClass, false));
        }

        return sql.toString();
    }


    public static String getAllColumns(String tableAlias, Class<?> entityClass) {
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnSet) {
            sql.append(tableAlias).append(".").append(entityColumn.getColumn().replaceAll("`", "")).append(",");
        }
        return sql.substring(0, sql.length() - 1);
    }
}
