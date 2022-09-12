package com.github.seaxlab.core.component.json.jackson.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.seaxlab.core.enums.DateFormatEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/12
 * @since 1.0
 */
@Slf4j
public class DateToStringSerializer extends StdSerializer<Date> {

    static DateTimeFormatter format = DateTimeFormatter.ofPattern(DateFormatEnum.yyyy_MM_dd_HH_mm_ss.getValue());

    public DateToStringSerializer() {
        super(Date.class);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        //TODO 这里如果能够兼容@JsonFormat(pattern="yyyy-MM-dd") 这种优先级的就更好了
        if (value != null) {
            gen.writeString(LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault()).format(format));
        } else {
            gen.writeNull();
        }
    }

}

