package com.github.seaxlab.core.model.layer.event;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * global event model
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
     * 事件来源
     *
     * @return integer
     */
    default String eventSource() {
        return "";
    }

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
     * 时间创建人id
     *
     * @return
     */
    default String getCreateUserId() {
        return "";
    }

    /**
     * 时间创建人名称
     *
     * @return
     */
    default String getCreateUserName() {
        return "";
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
