package com.github.seaxlab.core.loader;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/1
 * @since 1.0
 */
@Slf4j
public class OrderComparator implements Comparator<Object> {

    private static final OrderComparator INSTANCE = new OrderComparator();


    public static void sort(List<?> list) {
        if (!list.isEmpty() && list.size() > 1) {
            list.sort(INSTANCE);
        }
    }

    @Override
    public int compare(Object o1, Object o2) {
        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return Integer.compare(i1, i2);
    }

    public static int getOrder(Object obj) {
        if (obj == null) {
            return 0;
        }

        Order orderAnno = obj.getClass().getAnnotation(Order.class);
        return orderAnno == null ? 0 : orderAnno.value();
    }

}
