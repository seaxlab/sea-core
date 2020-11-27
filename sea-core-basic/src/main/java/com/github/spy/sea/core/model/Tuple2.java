package com.github.spy.sea.core.model;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
public class Tuple2<V1, V2> extends Tuple {

    private Tuple2() {
    }

    public Tuple2(V1 v1, V2 v2) {
        super(new Object[]{v1, v2});
    }

    /**
     * 第一个值
     *
     * @return
     */
    public V1 getFirst() {
        return get(0);
    }

    /**
     * 第二个值
     *
     * @return
     */
    public V2 getSecond() {
        return get(1);
    }
}
