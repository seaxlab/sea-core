package com.github.spy.sea.core.serialize;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
public interface SerializeProcessor {

    /**
     * 序列化字节
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] seriablize(T obj);


    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deseriablize(byte[] bytes, Class<T> clazz);
}
