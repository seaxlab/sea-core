package com.github.spy.sea.core.component.fsm.v1;

import com.github.spy.sea.core.component.fsm.v1.impl.DefaultCheckable;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/28
 * @since 1.0
 */
@Slf4j
public class FsmProcessorAdapter extends AbstractFsmProcessor {
    @Override
    public Checkable getCheckable(FsmContext context) {
        return new DefaultCheckable();
    }

    @Override
    public String getNextState(FsmContext context) {
        return null;
    }

    @Override
    public BaseResult action(FsmContext context, String nextState) {
        return BaseResult.success();
    }

    @Override
    public BaseResult save(FsmContext context, String nextState) {
        return BaseResult.success();
    }

    @Override
    public void after(FsmContext context) {

    }
}
