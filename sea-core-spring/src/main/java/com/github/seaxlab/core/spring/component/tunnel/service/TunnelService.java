package com.github.seaxlab.core.spring.component.tunnel.service;


import com.github.seaxlab.core.spring.component.tunnel.bo.BeanEventReqBO;
import com.github.seaxlab.core.spring.component.tunnel.bo.ExecuteReqBO;

/**
 * 隧道服务（调用biz service bean）
 *
 * @author spy
 * @version 1.0 2024/8/31
 * @since 1.0
 */
public interface TunnelService {
    /**
     * 执行 bean中方法/字段
     *
     * @param bo service,field,method,argument1~N,argumentTypes,txFlag
     * @return result
     */
    Object executeSimple(ExecuteReqBO bo);

    /**
     * 执行spring事件
     *
     * @param bo
     * @return
     */
    Object executeEvent(BeanEventReqBO bo);
}
