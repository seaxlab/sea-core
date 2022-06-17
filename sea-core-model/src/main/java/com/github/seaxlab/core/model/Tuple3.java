package com.github.seaxlab.core.model;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
public class Tuple3<V1, V2, V3> extends Tuple {

    private Tuple3() {
    }

    public Tuple3(V1 v1, V2 v2, V3 v3) {
        super(new Object[]{v1, v2, v3});
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

    /**
     * 第三个值
     *
     * @return
     */
    public V3 getThird() {
        return get(2);
    }


    /**
     * static method
     *
     * @param v1
     * @param v2
     * @param <V1>
     * @param <V2>
     * @return
     */
    public static <V1, V2, V3> Tuple3<V1, V2, V3> of(V1 v1, V2 v2, V3 v3) {
        return new Tuple3<>(v1, v2, v3);
    }
}
