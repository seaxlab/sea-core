package com.github.seaxlab.core.dal.mybatis.plus.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.seaxlab.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * wrapper util
 *
 * @author spy
 * @version 1.0 2021/11/29
 * @since 1.0
 */
@Slf4j
public final class WrapperUtil {

  private WrapperUtil() {
  }

  /**
   * set equal or in
   *
   * @param wrapper
   * @param propertyName
   * @param value
   */
  public static <T> void set(final QueryWrapper<T> wrapper, String propertyName, Object value) {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        wrapper.eq(propertyName, value);
      }
    } else if (value instanceof Collection) {
      Collection<?> data = (Collection<?>) value; // NOSONAR
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
   * set equal or in
   *
   * @param wrapper
   * @param column
   * @param value
   */
  public static <T> void set(final LambdaQueryWrapper<T> wrapper, SFunction<T, ?> column, Object value) {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        wrapper.eq(column, value);
      }
    } else if (value instanceof Collection) {
      Collection<?> data = (Collection<?>) value; // NOSONAR
      if (data.isEmpty()) {
        log.warn("collection [value] is empty.");
      } else {
        wrapper.in(column, data);
      }
    } else {
      wrapper.eq(column, value);
    }
  }

  /**
   * set not equal or in
   *
   * @param wrapper
   * @param propertyName
   * @param value
   */
  public static <T> void setNot(final QueryWrapper<T> wrapper, String propertyName, Object value) {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        wrapper.ne(propertyName, value);
      }
    } else if (value instanceof Collection) {
      Collection<?> data = (Collection<?>) value; // NOSONAR
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
   * set not equal or in
   *
   * @param wrapper
   * @param func
   * @param value
   */
  public static <T> void setNot(final LambdaQueryWrapper<T> wrapper, SFunction<T, ?> func, Object value) {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        wrapper.ne(func, value);
      }
    } else if (value instanceof Collection) {
      Collection<?> data = (Collection<?>) value; // NOSONAR
      if (data.isEmpty()) {
        log.warn("collection [value] is empty.");
      } else {
        wrapper.notIn(func, data);
      }
    } else {
      wrapper.ne(func, value);
    }
  }

  /**
   * 模糊匹配%xxx%
   *
   * @param wrapper
   * @param func
   * @param value
   * @param <T>
   */
  public static <T> void setLike(final LambdaQueryWrapper<T> wrapper, SFunction<T, ?> func, Object value) {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        wrapper.like(func, value);
      }
    } else {
      wrapper.like(func, value);
    }
  }

  /**
   * 左匹配%xxx
   *
   * @param wrapper
   * @param func
   * @param value
   * @param <T>
   */
  public static <T> void setLikeLeft(final LambdaQueryWrapper<T> wrapper, SFunction<T, ?> func, Object value) {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        wrapper.likeLeft(func, value);
      }
    } else {
      wrapper.likeLeft(func, value);
    }
  }

  /**
   * 右匹配xxx%
   *
   * @param wrapper
   * @param func
   * @param value
   * @param <T>
   */
  public static <T> void setLikeRight(final LambdaQueryWrapper<T> wrapper, SFunction<T, ?> func, Object value) {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof String) {
      if (StringUtil.isNotEmpty(value)) {
        wrapper.likeRight(func, value);
      }
    } else {
      wrapper.likeRight(func, value);
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
  public static <T> void setRangeDate(final QueryWrapper<T> wrapper, String propertyName, Date beginDate,
                                      Date endDate) {
    if (beginDate != null) {
      wrapper.ge(propertyName, new java.sql.Date(beginDate.getTime()));
    }
    if (endDate != null) {
      wrapper.le(propertyName, new java.sql.Date(endDate.getTime()));
    }
  }

  /**
   * 只比较日期
   *
   * @param wrapper
   * @param func
   * @param beginDate
   * @param endDate
   */
  public static <T> void setRangeDate(final LambdaQueryWrapper<T> wrapper, SFunction<T, ?> func, Date beginDate,
                                      Date endDate) {
    if (beginDate != null) {
      wrapper.ge(func, new java.sql.Date(beginDate.getTime()));
    }
    if (endDate != null) {
      wrapper.le(func, new java.sql.Date(endDate.getTime()));
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
  public static <T> void setRangeDateTime(final QueryWrapper<T> wrapper, String propertyName, Date beginDate,
                                          Date endDate) {
    if (beginDate != null) {
      wrapper.ge(propertyName, beginDate);
    }
    if (endDate != null) {
      wrapper.le(propertyName, endDate);
    }
  }


  /**
   * 比较日期时间
   *
   * @param wrapper
   * @param func
   * @param beginDate
   * @param endDate
   */
  public static <T> void setRangeDateTime(final LambdaQueryWrapper<T> wrapper, SFunction<T, ?> func, Date beginDate,
                                          Date endDate) {
    if (beginDate != null) {
      wrapper.ge(func, beginDate);
    }
    if (endDate != null) {
      wrapper.le(func, endDate);
    }
  }

}
