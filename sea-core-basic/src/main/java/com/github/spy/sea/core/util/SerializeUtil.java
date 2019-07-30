package com.github.spy.sea.core.util;

import com.github.spy.sea.core.serialize.SerializeProcessor;
import com.github.spy.sea.core.serialize.support.DefaultSerializeProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
@Slf4j
public class SerializeUtil {

    public static final String TYPE_JSON = "JSON";

    public static final String TYPE_BYTE = "BYTE";


    public static byte[] serialize(Object object) {
        SerializeProcessor processor = new DefaultSerializeProcessor();

        return processor.serialize(object);
    }

    public static Object unserialize(byte[] bytes) {

        SerializeProcessor processor = new DefaultSerializeProcessor();

        return processor.deserialize(bytes, Object.class);
    }


}
