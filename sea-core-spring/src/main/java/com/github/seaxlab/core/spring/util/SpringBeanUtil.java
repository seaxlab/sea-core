package com.github.seaxlab.core.spring.util;

import com.github.seaxlab.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.BiConsumer;
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
        return convertList(data, targetClass, null, null);
    }

    /**
     * convert to list, but also can change something.
     *
     * @param data
     * @param targetClass
     * @param consumer
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> convertList(List<S> data, Class<T> targetClass, BiConsumer<S, T> consumer) {
        return convertList(data, targetClass, consumer, null);
    }

    /**
     * convert list
     *
     * @param data
     * @param targetClass
     * @param consumer         do other some thing.
     * @param ignoreProperties
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> convertList(List<S> data,
                                             Class<T> targetClass,
                                             BiConsumer<S, T> consumer,
                                             String... ignoreProperties) {
        if (ListUtil.isEmpty(data)) {
            return ListUtil.empty();
        }

        List<T> list = data.stream()
                           .map(item -> {
                               try {
                                   T target = ConstructorUtils.invokeConstructor(targetClass);

                                   BeanUtils.copyProperties(item, target, ignoreProperties);
                                   if (consumer != null) {
                                       consumer.accept(item, target);
                                   }
                                   return target;
                               } catch (Exception e) {
                                   log.error("fail to new instance", e);
                               }
                               return null;
                           }).collect(Collectors.toList());
        return list;
    }


}
