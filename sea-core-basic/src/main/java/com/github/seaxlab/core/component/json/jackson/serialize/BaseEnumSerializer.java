package com.github.seaxlab.core.component.json.jackson.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.seaxlab.core.enums.IBaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/24
 * @since 1.0
 */
@Slf4j
public class BaseEnumSerializer extends JsonSerializer<IBaseEnum> {

    @Override
    public void serialize(IBaseEnum baseEnum, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        //generator.writeStartObject();
        //...
        //generator.writeEndObject();

        if (baseEnum.getCode() instanceof Integer) {
            generator.writeNumber((Integer) baseEnum.getCode());
        } else {
            generator.writeNumber((String) baseEnum.getCode());
        }

    }
}
