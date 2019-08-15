package com.github.spy.sea.core.serialize.protobuf;


import com.github.spy.sea.core.exception.ShouldNeverHappenException;
import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.serialize.SerializeProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-29
 * @since 1.0
 */
@LoadLevel(name = "protobuf")
public class ProtobufSerializeProcessor implements SerializeProcessor {

    private static final ProtobufHelper PROTOBUF_HELPER = new ProtobufHelper();

    /**
     * Encode method name
     */
    private static final String METHOD_TOBYTEARRAY = "toByteArray";
    /**
     * Decode method name
     */
    private static final String METHOD_PARSEFROM = "parseFrom";

    @Override
    public <T> byte[] serialize(T request) {
        Class clazz = request.getClass();
        Method method = PROTOBUF_HELPER.toByteArrayMethodMap.get(clazz);
        if (method == null) {
            try {
                method = clazz.getMethod(METHOD_TOBYTEARRAY);
                method.setAccessible(true);
                PROTOBUF_HELPER.toByteArrayMethodMap.put(clazz, method);
            } catch (Exception e) {
                throw new ShouldNeverHappenException("Cannot found method " + clazz.getName()
                        + ".toByteArray(), please check the generated code.", e);
            }
        }
        byte[] bytes = new byte[0];
        try {
            bytes = (byte[]) method.invoke(request);
        } catch (Exception e) {
            throw new ShouldNeverHappenException("serialize occurs exception", e);
        }

        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
//        Class clazz = PROTOBUF_HELPER.getPbClass(responseClazz);

        Method method = PROTOBUF_HELPER.parseFromMethodMap.get(clazz);
        if (method == null) {
            try {
                method = clazz.getMethod(METHOD_PARSEFROM, byte[].class);
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new ShouldNeverHappenException("Cannot found static method " + clazz.getName()
                            + ".parseFrom(byte[]), please check the generated code");
                }
                method.setAccessible(true);
                PROTOBUF_HELPER.parseFromMethodMap.put(clazz, method);
            } catch (NoSuchMethodException e) {
                throw new ShouldNeverHappenException("Cannot found method " + clazz.getName()
                        + ".parseFrom(byte[]), please check the generated code", e);
            }
        }
        Object result;
        try {
            result = method.invoke(null, bytes);
        } catch (Exception e) {
            throw new ShouldNeverHappenException("Error when invoke " + clazz.getName() + ".parseFrom(byte[]).", e);
        }

        return (T) result;
    }
}