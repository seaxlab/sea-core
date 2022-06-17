package com.github.seaxlab.core.test.spring.event;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/23
 * @since 1.0
 */
@Data
public class OrderEvent {
    private String orderNo;

    public OrderEvent(String orderNo) {
        this.orderNo = orderNo;
    }
}
