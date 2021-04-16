package com.github.spy.sea.core.dal.mybatis.tk.util;

import lombok.extern.slf4j.Slf4j;

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
}
