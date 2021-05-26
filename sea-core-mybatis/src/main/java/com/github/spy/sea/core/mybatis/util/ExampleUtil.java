package com.github.spy.sea.core.mybatis.util;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.util.*;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * 设置primary字段值
     * <p>
     * int,long,string,boolean
     * Integer,Long,String,Boolean,Double
     * </p>
     *
     * @param example
     * @param obj
     */
    public static void setPrimaryFieldValue(final Example example, final Object obj) {
        Preconditions.checkNotNull(example, "example can't be null.");
        Preconditions.checkNotNull(obj, "Object can't be null.");

        List<Field> fieldList = ReflectUtil.getAllFieldsList(obj.getClass());

        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            if (EqualUtil.isEq("serialVersionUID", field.getName())) {
                continue;
            }

            switch (field.getType().getName()) {
                case "int":
                case "long":
                case "boolean":
                case "java.lang.Integer":
                case "java.lang.Long":
                case "java.lang.Double":
                case "java.lang.Boolean":
                case "java.lang.String":
                    setValue(example, field.getName(), ReflectUtil.read(obj, field.getName()));
                    break;
            }
            continue;
        }
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
     * and 一个模糊匹配
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
     * and 多个模糊匹配
     * and(xx or xx or xx)
     *
     * @param example
     * @param map
     */
    public static void setAndOrLikeValue(Example example, Map<String, String> map) {
        if (MapUtil.isEmpty(map)) {
            return;
        }
        Example.Criteria criteria = example.and();
        map.forEach((key, value) -> {
            criteria.orLike(key, "%" + value + "%");
        });
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
     * set status=1
     *
     * @param example
     */
    public static void setStatusFlag(Example example) {
        example.and()
               .andEqualTo("status", CoreConst.YES);

    }

    /**
     * set isDeleted=0
     *
     * @param example
     */
    public static void setIsDeletedFlag(Example example) {
        example.and()
               .andEqualTo("isDeleted", CoreConst.NO);
    }

    /**
     * set isEnabled=1
     *
     * @param example
     */
    public static void setIsEnabled(Example example) {
        example.and()
               .andEqualTo("isEnabled", CoreConst.YES);
    }

    /**
     * 同时设置isEnabled,isDeleted.
     *
     * @param example
     */
    public static void setIsEnabledAndIsDeletedFlag(Example example) {
        example.and()
               .andEqualTo("isEnabled", CoreConst.YES)
               .andEqualTo("isDeleted", CoreConst.NO);
    }

}
