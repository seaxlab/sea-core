package com.github.seaxlab.core.component.fsm.v1.order.impl;

import com.github.seaxlab.core.component.fsm.v1.order.OrderCreateEvent;
import com.github.seaxlab.core.component.fsm.v1.order.OrderFsmEngine;
import com.github.seaxlab.core.model.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/28
 * @since 1.0
 */
@Slf4j
public class DefaultOrderFsmEngine implements OrderFsmEngine {

    @Override
    public Result sendEvent(OrderCreateEvent orderCreateEvent) {
//        FsmOrder fsmOrder = null;
//        if (orderStateEvent.newCreate()) {
//            fsmOrder = this.fsmOrderService.getFsmOrder(orderStateEvent.getOrderId());
//            if (fsmOrder == null) {
//                throw new FsmException(ErrorCodeEnum.ORDER_NOT_FOUND);
//            }
//        }
//        return sendEvent(orderStateEvent, fsmOrder);
        return null;
    }

//    public <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) throws Exception {
//        // 构造当前事件上下文
//        StateContext context = this.getStateContext(orderStateEvent, fsmOrder);
//        // 获取当前事件处理器
//        StateProcessor<T> stateProcessor = this.getStateProcessor(context);
//        // 执行处理逻辑
//        return stateProcessor.action(context);
//    }
//
//    private <T> StateProcessor<T, ?> getStateProcessor(StateContext<?> context) {
//        OrderStateEvent stateEvent = context.getOrderStateEvent();
//        FsmOrder fsmOrder = context.getFsmOrder();
//        // 根据状态+事件对象获取所对应的业务处理器集合
//        List<AbstractStateProcessor> processorList = stateProcessorRegistry.acquireStateProcess(fsmOrder.getOrderState(),
//                stateEvent.getEventType(), fsmOrder.bizCode(), fsmOrder.sceneId());
//        if (processorList == null) {
//            // 订单状态发生改变
//            if (!Objects.isNull(stateEvent.orderState()) && !stateEvent.orderState().equals(fsmOrder.getOrderState())) {
//                throw new FsmException(ErrorCodeEnum.ORDER_STATE_NOT_MATCH);
//            }
//            throw new FsmException(ErrorCodeEnum.NOT_FOUND_PROCESSOR);
//        }
//        if (CollectionUtils.isEmpty(processorResult)) {
//            throw new FsmException(ErrorCodeEnum.NOT_FOUND_PROCESSOR);
//        }
//        if (processorResult.size() > 1) {
//            throw new FsmException(ErrorCodeEnum.FOUND_MORE_PROCESSOR);
//        }
//        return processorResult.get(0);
//    }
//
//    private StateContext<?> getStateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
//        StateContext<?> context = new StateContext(orderStateEvent, fsmOrder);
//        return context;
//    }
}
