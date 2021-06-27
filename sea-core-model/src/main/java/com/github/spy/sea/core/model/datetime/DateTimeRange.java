package com.github.spy.sea.core.model.datetime;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.spy.sea.core.model.DTO;
import lombok.Data;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/26
 * @since 1.0
 */
@Data
public class DateTimeRange extends DTO {
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date begin;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date end;
}
