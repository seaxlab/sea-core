package com.github.spy.sea.core.serialize.support;

import com.github.spy.sea.core.serialize.SerializeProcessor;
import com.github.spy.sea.core.serialize.exception.DeserializeException;
import com.github.spy.sea.core.serialize.exception.SerializeException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * JDK默认实现
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
@Slf4j
public class DefaultSerializeProcessor implements SerializeProcessor {

    @Override
    public <T> byte[] serialize(T obj) {
        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(obj);
            bytes = baos.toByteArray();

        } catch (Exception e) {
            log.error("序列化出错：", e);
            throw new SerializeException();
        }

        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {

            return clazz.cast(ois.readObject());

        } catch (Exception e) {
            log.error("反序列化出错：", e);
            throw new DeserializeException();
        }
//        return null;
    }
}
