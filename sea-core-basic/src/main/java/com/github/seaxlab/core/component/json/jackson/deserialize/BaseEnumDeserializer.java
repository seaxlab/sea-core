package com.github.seaxlab.core.component.json.jackson.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.seaxlab.core.enums.IBaseEnum;
import com.github.seaxlab.core.util.ReflectUtil;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/24
 * @since 1.0
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<IBaseEnum> {

  @Override
  public IBaseEnum deserialize(JsonParser jsonParser, DeserializationContext context)
    throws IOException, JsonProcessingException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String currentName = jsonParser.currentName();
    Object currentValue = jsonParser.getCurrentValue();

    Class fieldType = ReflectUtil.getField(currentValue.getClass(), currentName).getClass();
    //Class findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());

    //IBaseEnum<Integer> baseEnum = BaseEnumUtil.toEnum(node.intValue(), fieldType).get();
    //IBaseEnum baseEnum = EnumUtil.getEnumByValue(fieldType, node.intValue());

    //return baseEnum;
    return null;
  }
}
