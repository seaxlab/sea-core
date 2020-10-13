package com.github.spy.sea.core.spring.util;

import com.github.spy.sea.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * extension for spring bean util.
 *
 * @author spy
 * @version 1.0 2020/10/13
 * @since 1.0
 */
@Slf4j
public class SpringBeanUtil {

    /**
     * source object to target object.
     *
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null) {
            log.warn("source is null.");
            return null;
        }

        T target = null;
        try {
            target = ConstructorUtils.invokeConstructor(targetClass);
        } catch (NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | InstantiationException e) {
            log.error("fail to new instance", e);
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * source list to target list
     *
     * @param data        source data
     * @param targetClass target class
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> convertList(List<S> data, Class<T> targetClass) {
        if (ListUtil.isEmpty(data)) {
            return ListUtil.empty();
        }

        List<T> list = data.stream()
                           .map(item -> {
                               try {
                                   T target = ConstructorUtils.invokeConstructor(targetClass);

                                   BeanUtils.copyProperties(item, target);
                                   return target;
                               } catch (Exception e) {
                                   log.error("fail to new instance", e);
                               }
                               return null;
                           }).collect(Collectors.toList());
        return list;
    }


}
