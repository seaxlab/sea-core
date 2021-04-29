package com.github.spy.sea.core.component.fsm.v1.order;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/28
 * @since 1.0
 */
public interface OrderCreateEvent {

    /**
     * 订单状态事件
     */
    String getEventType();

    /**
     * 订单ID
     */
    String getOrderId();

    /**
     * 如果orderState不为空，则代表只有订单是当前状态才进行迁移
     */
    default String orderState() {
        return null;
    }

    /**
     * 是否要新创建订单
     */
    boolean newCreate();
}
