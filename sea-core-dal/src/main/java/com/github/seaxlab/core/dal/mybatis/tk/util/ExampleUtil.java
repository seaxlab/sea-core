package com.github.seaxlab.core.dal.mybatis.tk.util;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.MapUtil;
import com.github.seaxlab.core.util.ReflectUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * Example util 注意： 推荐使用Criteria参数，不在推荐使用example参数，因为example方式会多一层括号
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
   * set value
   *
   * @param criteria     条件语句
   * @param propertyName 属性名称
   * @param value        值，null值会跳过
   */
  public static void set(final Example.Criteria criteria, String propertyName, Object value) {
    if (value == null) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        criteria.andEqualTo(propertyName, value);
      }
    } else if (value instanceof Collection) {
      Collection data = (Collection) value;
      if (data.isEmpty()) {
        log.warn("collection [value] is empty.");
      } else {
        criteria.andIn(propertyName, data);
      }
    } else {
      criteria.andEqualTo(propertyName, value);
    }
  }

  /**
   * 设置日期
   *
   * @param criteria
   * @param propertyName
   * @param date
   */
  public static void setDate(final Example.Criteria criteria, String propertyName, Date date) {
    if (date == null) {
      return;
    }
    criteria.andEqualTo(propertyName, new java.sql.Date(date.getTime()));
  }

  /**
   * 设置日期时间
   *
   * @param criteria
   * @param propertyName
   * @param dateTime
   */
  public static void setDateTime(final Example.Criteria criteria, String propertyName, Date dateTime) {
    if (dateTime == null) {
      return;
    }
    criteria.andEqualTo(propertyName, dateTime);
  }

  /**
   * set time
   *
   * @param criteria     criteria
   * @param propertyName property name
   * @param date         date
   */
  public static void setTime(final Example.Criteria criteria, String propertyName, Date date) {
    if (date == null) {
      return;
    }
    criteria.andEqualTo(propertyName, new java.sql.Time(date.getTime()));
  }

  /**
   * set not equal
   *
   * @param criteria     criteria
   * @param propertyName property name
   * @param value        value
   */
  public static void setNot(final Example.Criteria criteria, String propertyName, Object value) {
    if (value == null) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        criteria.andNotEqualTo(propertyName, value);
      }
    } else if (value instanceof Collection) {
      Collection data = (Collection) value;
      if (data.isEmpty()) {
        log.warn("collection [value] is empty.");
      } else {
        criteria.andNotIn(propertyName, data);
      }
    } else {
      criteria.andNotEqualTo(propertyName, value);
    }
  }


  public static void setLike(final Example.Criteria criteria, String propertyName, Object value) {
    if (value == null) {
      return;
    }
    criteria.andLike(propertyName, "%" + value + "%");
  }


  /**
   * and 多个模糊匹配 and(xx or xx or xx)
   *
   * @param criteria criteria
   * @param map
   */
  public static void setAndOrLike(final Example.Criteria criteria, Map<String, String> map) {
    if (MapUtil.isEmpty(map)) {
      return;
    }
    map.forEach((key, value) -> {
      criteria.orLike(key, "%" + value + "%");
    });
  }

  /**
   * 左匹配
   *
   * @param criteria     criteria
   * @param propertyName property name
   * @param value        value
   */
  public static void setLikeLeft(final Example.Criteria criteria, String propertyName, String value) {
    if (StringUtils.isNotEmpty(value)) {
      criteria.andLike(propertyName, "%" + value);
    }
  }

  /**
   * 右匹配
   *
   * @param criteria     criteria
   * @param propertyName property name
   * @param value        value
   */
  public static void setLikeRight(final Example.Criteria criteria, String propertyName, String value) {
    if (StringUtils.isNotEmpty(value)) {
      criteria.andLike(propertyName, value + "%");
    }
  }

  /**
   * 设置开始结束日期范围 java.sql.Date
   *
   * @param criteria
   * @param propertyName
   * @param beginDate
   * @param endDate
   */
  public static void setRangeDate(final Example.Criteria criteria, String propertyName, Date beginDate, Date endDate) {
    if (beginDate != null) {
      criteria.andGreaterThanOrEqualTo(propertyName, new java.sql.Date(beginDate.getTime()));
    }
    if (endDate != null) {
      criteria.andLessThanOrEqualTo(propertyName, new java.sql.Date(endDate.getTime()));
    }
  }

  /**
   * 设置日期时间范围
   *
   * @param criteria
   * @param propertyName
   * @param beginDate
   * @param endDate
   */
  public static void setRangeDateTime(final Example.Criteria criteria, String propertyName, Date beginDate,
                                      Date endDate) {
    if (beginDate != null) {
      criteria.andGreaterThanOrEqualTo(propertyName, beginDate);
    }
    if (endDate != null) {
      criteria.andLessThanOrEqualTo(propertyName, endDate);
    }
  }

  /**
   * 设置开始日期时间
   *
   * @param criteria
   * @param propertyName
   * @param beginDate
   */
  public static void setRangeDateTimeBegin(final Example.Criteria criteria, String propertyName, Date beginDate) {
    if (beginDate != null) {
      criteria.andGreaterThanOrEqualTo(propertyName, beginDate);
    }
  }

  /**
   * 设置结束日期时间
   *
   * @param criteria
   * @param propertyName
   * @param endDate
   */
  public static void setRangeDateTimeEnd(final Example.Criteria criteria, String propertyName, Date endDate) {
    if (endDate != null) {
      criteria.andLessThanOrEqualTo(propertyName, endDate);
    }
  }

  /**
   * 只设置开始日期 java.sql.Date
   *
   * @param criteria     criteria
   * @param propertyName property name
   * @param beginDate    begin date
   */
  public static void setDateBegin(final Example.Criteria criteria, String propertyName, Date beginDate) {
    if (beginDate != null) {
      criteria.andGreaterThanOrEqualTo(propertyName, new java.sql.Date(beginDate.getTime()));
    }
  }

  /**
   * 只设置结束日期 java.sql.Date
   *
   * @param criteria     criteria
   * @param propertyName property name
   * @param endDate      end date
   */
  public static void setDateEnd(final Example.Criteria criteria, String propertyName, Date endDate) {
    if (endDate != null) {
      criteria.andLessThanOrEqualTo(propertyName, new java.sql.Date(endDate.getTime()));
    }
  }


  /**
   * set all args to criteria
   *
   * @param criteria criteria
   * @param args     args map
   */
  public static void setAll(Example.Criteria criteria, Object... args) {
    if (args.length == 0) {
      log.warn("args is empty.");
      return;
    }

    if (args.length % 2 != 0) {
      ExceptionHandler.publishMsg("参数个数必须是2的整数倍");
    }

    for (int i = 0; i < args.length; i = i + 2) {
      Object propertyObj = args[i];
      Object valueObj = args[i + 1];
      set(criteria, (String) propertyObj, valueObj);
    }
  }

  // ---------------------example util

  /**
   * 设置primary字段值
   * <p>
   * int,long,string,boolean Integer,Long,String,Boolean,Double
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
          set(example, field.getName(), ReflectUtil.read(obj, field.getName()));
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
  public static void set(Example example, String propertyName, Object value) {
    if (value == null) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        example.and().andEqualTo(propertyName, value);
      }
    } else if (value instanceof Collection) {
      Collection data = (Collection) value;
      if (data.isEmpty()) {
        log.warn("collection [value] is empty.");
      } else {
        example.and().andIn(propertyName, data);
      }
    } else {
      example.and().andEqualTo(propertyName, value);
    }
  }

  /**
   * set all value.
   *
   * @param example -
   * @param args    -
   */
  public static void setAll(Example example, Object... args) {
    Example.Criteria criteria = example.and();
    setAll(criteria, args);
  }


  /**
   * 设置 反向值
   *
   * @param example      example
   * @param propertyName property name of entity
   * @param value        value.
   */
  public static void setNotEqual(Example example, String propertyName, Object value) {
    if (example == null) {
      log.warn("example is null");
      return;
    }
    if (StringUtil.isEmpty(propertyName)) {
      log.warn("property name is empty.");
    }
    if (value == null) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        example.and().andNotEqualTo(propertyName, value);
      }
    } else if (value instanceof Collection) {
      Collection data = (Collection) value;
      if (data.isEmpty()) {
        log.warn("collection [value] is empty.");
      } else {
        example.and().andNotIn(propertyName, data);
      }
    } else {
      example.and().andNotEqualTo(propertyName, value);
    }


  }

  /**
   * and 一个模糊匹配
   *
   * @param example
   * @param propertyName
   * @param value
   */
  public static void setLike(Example example, String propertyName, String value) {
    if (StringUtils.isNotEmpty(value)) {
      example.and().andLike(propertyName, "%" + value + "%");
    }
  }

  /**
   * and 多个模糊匹配 and(xx or xx or xx)
   *
   * @param example
   * @param map
   */
  public static void setAndOrLike(Example example, Map<String, String> map) {
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
  public static void setLikeLeft(Example example, String propertyName, String value) {
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
  public static void setLikeRight(Example example, String propertyName, String value) {
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

}
