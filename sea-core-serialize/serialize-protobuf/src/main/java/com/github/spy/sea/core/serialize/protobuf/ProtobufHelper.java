package com.github.spy.sea.core.serialize.protobuf;

import com.github.spy.sea.core.exception.ShouldNeverHappenException;
import com.google.protobuf.MessageLite;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author leizhiyuan
 */
public class ProtobufHelper {

    /**
     * Cache of parseFrom method
     */
    ConcurrentMap<Class, Method> parseFromMethodMap = new ConcurrentHashMap<Class, Method>();

    /**
     * Cache of toByteArray method
     */
    ConcurrentMap<Class, Method> toByteArrayMethodMap = new ConcurrentHashMap<Class, Method>();

    /**
     * {className:class}
     */
    private ConcurrentMap<String, Class> requestClassCache = new ConcurrentHashMap<String, Class>();

    /**
     * @param clazzName
     * @return
     */
    public Class getPbClass(String clazzName) {
        Class reqClass = requestClassCache.get(clazzName);
        if (reqClass == null) {
            // 读取接口里的方法参数和返回值
            Class clazz = null;
            try {
                clazz = Class.forName(clazzName);
            } catch (ClassNotFoundException e) {
                throw new ShouldNeverHappenException("get class occurs exception", e);

            }
            loadProtoClassToCache(clazzName, clazz);
        }
        return requestClassCache.get(clazzName);
    }

    /**
     * @param key
     * @param clazz
     */
    private void loadProtoClassToCache(String key, Class clazz) {
        if (clazz == void.class || !isProtoBufMessageClass(clazz)) {
            throw new ShouldNeverHappenException("class based protobuf: " + clazz.getName()
                    + ", only support return protobuf message!");
        }
        requestClassCache.put(key, clazz);
    }

    /**
     * Is this class is assignable from MessageLite
     *
     * @param clazz unknown class
     * @return is assignable from MessageLite
     */
    boolean isProtoBufMessageClass(Class clazz) {
        return clazz != null && MessageLite.class.isAssignableFrom(clazz);
    }
}
