package com.github.spy.sea.core.model.event;

import lombok.Data;

import java.util.Date;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/27
 * @since 1.0
 */
@Data
public abstract class AbstractEvent implements Event {

    protected String id;

    protected Date createTime;

    protected Integer version;


    public AbstractEvent() {
//        this.id = "";
        this.createTime = new Date();
        this.version = 1;
    }
}
