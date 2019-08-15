package com.github.spy.sea.core.serialize.fst;

import com.github.spy.sea.core.loader.LoadLevel;
import com.github.spy.sea.core.serialize.SerializeProcessor;
import org.nustaq.serialization.FSTConfiguration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-15
 * @since 1.0
 */
@LoadLevel(name = "fst")
public class FstSerializeProcessor implements SerializeProcessor {

    static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();


    @Override
    public <T> byte[] serialize(T obj) {
        return configuration.asByteArray(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return (T) configuration.asObject(bytes);
    }
}
