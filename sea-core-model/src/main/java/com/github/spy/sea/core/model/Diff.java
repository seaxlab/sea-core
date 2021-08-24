package com.github.spy.sea.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/24
 * @since 1.0
 */
@Data
public class Diff<T> implements Serializable {

    /**
     * 交集部分
     */
    private Set<T> intersections;

    /**
     * 左侧拥有，右侧没有
     */
    private Set<T> lefts;

    /**
     * 左侧没有，右侧拥有
     */
    private Set<T> rights;

}
