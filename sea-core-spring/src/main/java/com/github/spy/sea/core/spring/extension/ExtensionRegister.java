/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.github.spy.sea.core.spring.extension;

import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.exception.BaseAppException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ExtensionRegister
 *
 * @author fulan.zjf 2017-11-05
 */
@Component
public class ExtensionRegister {

    @Resource
    private ExtensionRepository extensionRepository;


    public void doRegistration(IExtensionPoint extensionObject) {
        Class<?> extensionClz = extensionObject.getClass();
        Extension extensionAnn = extensionClz.getDeclaredAnnotation(Extension.class);
        BizScenario bizScenario = BizScenario.valueOf(extensionAnn.bizId(), extensionAnn.useCase(), extensionAnn.scenario());
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(calculateExtensionPoint(extensionClz), bizScenario.getUniqueIdentity());
        IExtensionPoint preVal = extensionRepository.getExtensionRepo().put(extensionCoordinate, extensionObject);
        if (preVal != null) {
            throw new BaseAppException(CoreErrorConst.SYS_EXCEPTION, "Duplicate registration is not allowed for :" + extensionCoordinate);
        }

    }

    /**
     * @param targetClz
     * @return
     */
    private String calculateExtensionPoint(Class<?> targetClz) {
        Class[] interfaces = targetClz.getInterfaces();
        if (interfaces == null || interfaces.length == 0)
            throw new BaseAppException(CoreErrorConst.SYS_EXCEPTION, "Please assign a extension point interface for " + targetClz);
        for (Class intf : interfaces) {
            String extensionPoint = intf.getSimpleName();
            if (extensionPoint.contains(ExtensionConstant.EXTENSION_EXTPT_NAMING))
                return intf.getName();
        }
        throw new BaseAppException(CoreErrorConst.SYS_EXCEPTION, "Your name of ExtensionPoint for " + targetClz + " is not valid, must be end of " + ExtensionConstant.EXTENSION_EXTPT_NAMING);
    }

}