package com.github.seaxlab.core.component.fsm.v1;

import com.github.seaxlab.core.component.fsm.v1.impl.DefaultCheckable;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/28
 * @since 1.0
 */
@Slf4j
public class FsmProcessorAdapter<T> extends AbstractFsmProcessor<T> {

  @Override
  public Checkable getCheckable(FsmContext context) {
    return new DefaultCheckable();
  }

  @Override
  public String getNextState(FsmContext context) {
    return null;
  }

  @Override
  public void action(FsmContext context, String nextState) {
  }

  @Override
  public void save(FsmContext context, String nextState) {
  }

  @Override
  public T after(FsmContext context) {
    return null;
  }
}
