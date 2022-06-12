package com.github.seaxlab.core.dal.mybatis.plus.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/29
 * @since 1.0
 */
@Slf4j
public final class WrapperUtil {

    /**
     * set equal or in
     *
     * @param wrapper
     * @param propertyName
     * @param value
     */
    public static void set(final QueryWrapper wrapper, String propertyName, Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof String) {
            if (StringUtil.isNotEmpty(value)) {
                wrapper.eq(propertyName, value);
            }
        } else if (value instanceof Collection) {
            Collection data = (Collection) value;
            if (data.isEmpty()) {
                log.warn("collection [value] is empty.");
            } else {
                wrapper.in(propertyName, data);
            }
        } else {
            wrapper.eq(propertyName, value);
        }
    }

    /**
     * set not equal or in
     *
     * @param wrapper
     * @param propertyName
     * @param value
     */
    public static void setNot(final QueryWrapper wrapper, String propertyName, Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof String) {
            if (StringUtil.isNotEmpty(value)) {
                wrapper.ne(propertyName, value);
            }
        } else if (value instanceof Collection) {
            Collection data = (Collection) value;
            if (data.isEmpty()) {
                log.warn("collection [value] is empty.");
            } else {
                wrapper.notIn(propertyName, data);
            }
        } else {
            wrapper.ne(propertyName, value);
        }
    }

    /**
     * 只比较日期
     *
     * @param wrapper
     * @param propertyName
     * @param beginDate
     * @param endDate
     */
    public static void setRangeDate(final QueryWrapper wrapper, String propertyName, Date beginDate, Date endDate) {
        if (beginDate != null) {
            wrapper.ge(propertyName, new java.sql.Date(beginDate.getTime()));
        }
        if (endDate != null) {
            wrapper.le(propertyName, new java.sql.Date(endDate.getTime()));
        }
    }

    /**
     * 比较日期时间
     *
     * @param wrapper
     * @param propertyName
     * @param beginDate
     * @param endDate
     */
    public static void setRangeDateTime(final QueryWrapper wrapper, String propertyName, Date beginDate, Date endDate) {
        if (beginDate != null) {
            wrapper.ge(propertyName, beginDate);
        }
        if (endDate != null) {
            wrapper.le(propertyName, endDate);
        }
    }


    /**
     * 同时设置status,isDeleted
     * 过滤有效数据
     *
     * @param wrapper wrapper
     */
    public static void setStatusAndIsDeletedFlag(final QueryWrapper wrapper) {
        wrapper.eq("status", CoreConst.YES);
        wrapper.eq("isDeleted", CoreConst.NO);

    }

    /**
     * set status=1
     *
     * @param wrapper wrapper
     */
    public static void setStatusFlag(final QueryWrapper wrapper) {
        wrapper.eq("status", CoreConst.YES);
    }

    /**
     * set isDeleted=0
     *
     * @param wrapper wrapper
     */
    public static void setIsDeletedFlag(final QueryWrapper wrapper) {
        wrapper.eq("isDeleted", CoreConst.NO);
    }

    /**
     * set isEnabled=1
     *
     * @param wrapper wrapper
     */
    public static void setIsEnabled(final QueryWrapper wrapper) {
        wrapper.eq("isEnabled", CoreConst.YES);
    }

    /**
     * 同时设置isEnabled,isDeleted.
     *
     * @param wrapper wrapper
     */
    public static void setIsEnabledAndIsDeletedFlag(final QueryWrapper wrapper) {
        wrapper.eq("isEnabled", CoreConst.YES);
        wrapper.eq("isDeleted", CoreConst.NO);
    }

}
