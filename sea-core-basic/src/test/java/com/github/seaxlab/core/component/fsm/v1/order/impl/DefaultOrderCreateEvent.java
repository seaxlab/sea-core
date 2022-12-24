package com.github.seaxlab.core.component.fsm.v1.order.impl;

import com.github.seaxlab.core.component.fsm.v1.order.OrderCreateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/28
 * @since 1.0
 */
@Slf4j
public class DefaultOrderCreateEvent implements OrderCreateEvent {
  @Override
  public String getEventType() {
    return null;
  }

  @Override
  public String getOrderId() {
    return null;
  }

  @Override
  public boolean newCreate() {
    return false;
  }
}
