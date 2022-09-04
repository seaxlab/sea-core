package com.github.seaxlab.core.model.layer.event;

import lombok.Data;

import java.util.Date;

/**
 * global abstract event
 *
 * @author spy
 * @version 1.0 2021/6/27
 * @since 1.0
 */
@Data
public abstract class AbstractEvent implements Event {

    /**
     * 事件标识
     */
    protected String id;

    /**
     * 事件来源
     */
    protected String eventSource;

    /**
     * 事件创建时间
     */
    protected Date createTime;

    /**
     * 时间创建人ID
     */
    protected String createUserId;

    /**
     * 时间创建人名称
     */
    protected String createUserName;

    /**
     * 事件版本，默认1
     */
    protected Integer version;


    public AbstractEvent() {
//        this.id = "";
        this.eventSource = "";
        this.createTime = new Date();
        this.version = 1;
    }
}
