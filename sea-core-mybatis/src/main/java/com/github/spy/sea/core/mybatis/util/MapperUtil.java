package com.github.spy.sea.core.mybatis.util;

import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperProxy;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Proxy;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/6/3
 * @since 1.0
 */
@Slf4j
public final class MapperUtil {


    /**
     * check code.
     *
     * @param mapper
     * @param code
     * @param <T>
     */
    @Deprecated // mapper已经被MapperProxy代理过，层次嵌套太深不建议使用
    public static <T> void checkCode(Mapper<T> mapper, String code) {

        Object obj;
        if (mapper instanceof Proxy) {
            obj = ((Class) mapper.getClass().getGenericInterfaces()[0]).getClass().getGenericInterfaces()[0];
        } else {
            obj = mapper;
        }

        Class<?> clazz = ReflectUtil.getSingleGenericClass(obj);

        checkCode(clazz, mapper, code);
    }

    /**
     * check code
     *
     * @param clazz
     * @param mapper
     * @param code
     */
    public static void checkCode(Class<?> clazz, Mapper mapper, String code) {
        Example example = new Example(clazz);
        ExampleUtil.setValue(example, "code", code);
        ExampleUtil.setIsDeletedFlag(example);

        int count = mapper.selectCountByExample(example);
        if (count > 0) {
            ExceptionHandler.publishMsg("已存在相同的编码");
        }
    }

    /**
     * base query by id.
     *
     * @param mapper
     * @param id
     * @param <T>
     * @return
     */
    @Deprecated // mapper已经被MapperProxy代理过，层次嵌套太深不建议使用
    public static <T> T baseQueryById(Mapper<T> mapper, Long id) {
        Class<?> clazz = ReflectUtil.getSingleGenericClass(mapper);
        return baseQueryById(clazz, mapper, id);
    }

    /**
     * basic query by id
     * <p>
     * must have id,is_deleted field
     * </p>
     *
     * @param clazz
     * @param mapper
     * @param id
     * @param <T>
     * @return
     */
    public static <T> T baseQueryById(Class<?> clazz, Mapper<T> mapper, Long id) {
        Example example = new Example(clazz);
        ExampleUtil.setValue(example, "id", id);
        ExampleUtil.setIsDeletedFlag(example);

        T record = mapper.selectOneByExample(example);
        if (record == null) {
            ExceptionHandler.publishMsg("记录不存在");
        }
        return record;
    }

    /**
     * base query by code.
     *
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    @Deprecated // mapper已经被MapperProxy代理过，层次嵌套太深不建议使用
    public static <T> T baseQueryByCode(Mapper<T> mapper, String code) {

        if (mapper instanceof MapperProxy) {
            MapperProxy proxy = (MapperProxy) mapper;
        }
        Class<?> clazz = ReflectUtil.getSingleGenericClass(mapper);

        return baseQueryByCode(clazz, mapper, code);
    }

    /**
     * basic query by code
     * <p>
     * must have id,is_deleted field
     * </p>
     *
     * @param clazz
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> T baseQueryByCode(Class<?> clazz, Mapper<T> mapper, String code) {
        return baseQuery(clazz, mapper, "code", code);
    }

    /**
     * base query for common.
     *
     * @param clazz
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T baseQuery(Class<?> clazz, Mapper<T> mapper, String key, String value) {
        Example example = new Example(clazz);
        ExampleUtil.setValue(example, key, value);
        ExampleUtil.setIsDeletedFlag(example);

        T record = mapper.selectOneByExample(example);
        if (record == null) {
            ExceptionHandler.publishMsg("记录不存在");
        }
        return record;
    }


    /**
     * basic update selective field by code.
     *
     * @param record
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> int baseUpdateSelectiveByCode(T record, Mapper<T> mapper, String code) {
        return baseUpdateSelective(record, mapper, "code", code);
    }

    /**
     * base update selective for common.
     *
     * @param record
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> int baseUpdateSelective(T record, Mapper<T> mapper, String key, String value) {
        Example example = new Example(record.getClass());
        ExampleUtil.setValue(example, key, value);
        ExampleUtil.setIsDeletedFlag(example);

        return mapper.updateByExampleSelective(record, example);
    }

    /**
     * basic update all field by code.
     *
     * @param record
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> int baseUpdateByCode(T record, Mapper<T> mapper, String code) {
        return baseUpdate(record, mapper, "code", code);
    }

    /**
     * basic update all field for common.
     *
     * @param record
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> int baseUpdate(T record, Mapper<T> mapper, String key, String value) {
        Example example = new Example(record.getClass());
        ExampleUtil.setValue(example, key, value);
        ExampleUtil.setIsDeletedFlag(example);

        return mapper.updateByExample(record, example);
    }


    /**
     * <p>this just sugar.</p>
     * the same to baseUpdateSelectiveByCode
     *
     * @param record
     * @param mapper
     * @param code
     * @param <T>
     * @return
     */
    public static <T> int baseDeleteByCode(T record, Mapper<T> mapper, String code) {
        return baseUpdateSelectiveByCode(record, mapper, code);
    }

    /**
     * <p>this just sugar.</p>
     * the same to baseUpdateSelective.
     *
     * @param record
     * @param mapper
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> int baseDelete(T record, Mapper<T> mapper, String key, String value) {
        return baseUpdateSelective(record, mapper, key, value);
    }

}
