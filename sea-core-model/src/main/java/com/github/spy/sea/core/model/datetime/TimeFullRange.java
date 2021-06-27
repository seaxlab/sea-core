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
public class TimeFullRange extends DTO {

    @JSONField(format = "HH:mm:ss")
    private Date begin;

    @JSONField(format = "HH:mm:ss")
    private Date end;
}
