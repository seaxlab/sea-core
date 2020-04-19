package com.github.spy.sea.core.mybatis.util;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Example util
 *
 * @author spy
 * @version 1.0 2019/3/18
 * @since 1.0
 */
@Slf4j
public final class ExampleUtil {

    private ExampleUtil() {
    }

    /**
     * 赋值
     *
     * @param example
     * @param propertyName
     * @param value
     */
    public static void setValue(Example example, String propertyName, Object value) {
        if (value != null) {
            if (value instanceof String) {
                if (StringUtil.isNotEmpty(value)) {
                    example.and().andEqualTo(propertyName, value);
                }
            } else if (value instanceof List) {
                List dataList = (List) value;
                if (ListUtil.isNotEmpty(dataList)) {
                    example.and().andIn(propertyName, dataList);
                }
            } else {
                example.and().andEqualTo(propertyName, value);
            }
        }
    }

    /**
     * 模糊匹配
     *
     * @param example
     * @param propertyName
     * @param value
     */
    public static void setLikeValue(Example example, String propertyName, String value) {
        if (StringUtils.isNotEmpty(value)) {
            example.and().andLike(propertyName, "%" + value + "%");
        }
    }

    /**
     * 左匹配
     *
     * @param example
     * @param propertyName
     * @param value
     */
    public static void setLikeLeftValue(Example example, String propertyName, String value) {
        if (StringUtils.isNotEmpty(value)) {
            example.and().andLike(propertyName, "%" + value);
        }
    }

    /**
     * 右匹配
     *
     * @param example
     * @param propertyName
     * @param value
     */
    public static void setLikeRightValue(Example example, String propertyName, String value) {
        if (StringUtils.isNotEmpty(value)) {
            example.and().andLike(propertyName, value + "%");
        }
    }

    /**
     * 设置范围日期
     *
     * @param example
     * @param propertyName
     * @param beginDate
     * @param endDate
     */
    public static void setRangeDate(Example example, String propertyName, Date beginDate, Date endDate) {
        if (beginDate != null) {
            example.and().andGreaterThanOrEqualTo(propertyName, beginDate);
        }

        if (endDate != null) {
            example.and().andLessThanOrEqualTo(propertyName, endDate);
        }
    }

    /**
     * 同时设置status,isDeleted
     * 过滤有效数据
     *
     * @param example
     */
    public static void setStatusAndIsDeletedFlag(Example example) {
        example.and()
               .andEqualTo("status", CoreConst.YES)
               .andEqualTo("isDeleted", CoreConst.NO);

    }

    /**
     * 只设置status
     *
     * @param example
     */
    public static void setStatusFlag(Example example) {
        example.and()
               .andEqualTo("status", CoreConst.YES);

    }

    /**
     * 设置isDeleted
     *
     * @param example
     */
    public static void setIsDeletedFlag(Example example) {
        example.and()
               .andEqualTo("isDeleted", CoreConst.NO);

    }


}
