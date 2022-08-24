package com.github.seaxlab.core.util;

import com.github.seaxlab.core.serialize.SerializeProcessor;
import com.github.seaxlab.core.serialize.support.DefaultSerializeProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * serialize util.
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
@Slf4j
public final class SerializeUtil {

    public static final String TYPE_JSON = "JSON";

    public static final String TYPE_BYTE = "BYTE";

    private SerializeUtil() {
    }

    private static final SerializeProcessor processor = new DefaultSerializeProcessor();

    public static byte[] serialize(Object object) {
        return processor.serialize(object);
    }

    public static Object deserialize(byte[] bytes) {
        return processor.deserialize(bytes, Object.class);
    }


}
