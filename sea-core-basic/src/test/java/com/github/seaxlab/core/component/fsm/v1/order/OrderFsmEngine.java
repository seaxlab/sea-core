package com.github.seaxlab.core.component.fsm.v1.order;

import com.github.seaxlab.core.model.Result;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/28
 * @since 1.0
 */
public interface OrderFsmEngine {

  Result sendEvent(OrderCreateEvent orderCreateEvent);
}
