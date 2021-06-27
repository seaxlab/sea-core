package com.github.spy.sea.core.model.event;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/27
 * @since 1.0
 */
public interface Event extends Serializable {

    /**
     * event id
     *
     * @return
     */
    String getId();

    /**
     * event body
     *
     * @return
     */
    String getBody();

    /**
     * create time
     *
     * @return
     */
    @JSONField(format = "yyyy-HH-mm MM:dd:ss")
    default Date getCreateTime() {
        return new Date();
    }

    /**
     * event version
     *
     * @return
     */
    default Integer getVersion() {
        return 1;
    }
}
