package com.github.spy.sea.core.mybatis.util;

import com.github.spy.sea.core.util.ListUtil;
import com.github.spy.sea.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 模块名
 *
 * @author spy
 * @version 1.0 2019/3/18
 * @since 1.0
 */
@Slf4j
public final class ExampleUtil {

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
     * like
     *
     * @param example
     * @param propertyName
     * @param value
     */
    public static void setLikeValue(Example example, String propertyName, String value) {
        if (StringUtils.isNotEmpty(value)) {
            example.and().andLike(propertyName, value);
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

}
