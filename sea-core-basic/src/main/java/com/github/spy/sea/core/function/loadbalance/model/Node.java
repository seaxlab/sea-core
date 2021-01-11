package com.github.spy.sea.core.function.loadbalance.model;

import lombok.Data;

import java.io.Serializable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/26
 * @since 1.0
 */
@Data
public class Node implements Serializable {

    private String id;
    /**
     * 内部id
     */
    private String innerId;

    private String name;

    private int weight = 1;

    private Object extra;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInnerId() {
        return innerId;
    }

    public void setInnerId(String innerId) {
        this.innerId = innerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
