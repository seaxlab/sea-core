package com.github.seaxlab.core.spring.extension;

import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ExtensionRegister
 *
 * @author fulan.zjf 2017-11-05
 */
@Component("seaCoreExtensionRegister")
public class ExtensionRegister {

    @Autowired
    private ExtensionRepository extensionRepository;


    public void doRegistration(IExtensionPoint extensionObject) {
        Class<?> extensionClz = extensionObject.getClass();
        Extension extensionAnn = extensionClz.getDeclaredAnnotation(Extension.class);
        BizScenario bizScenario = BizScenario.of(extensionAnn.bizId(), extensionAnn.useCase(), extensionAnn.scenario());
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(calculateExtensionPoint(extensionClz), bizScenario.getUniqueIdentity());
        IExtensionPoint preVal = extensionRepository.getExtensionRepo().put(extensionCoordinate, extensionObject);
        if (preVal != null) {
            throw new BaseAppException(ErrorMessageEnum.EXTENSION_DUPLICATE_REGISTER_F, extensionCoordinate);
        }

    }

    /**
     * @param targetClz
     * @return
     */
    private String calculateExtensionPoint(Class<?> targetClz) {
        Class[] interfaces = targetClz.getInterfaces();
        if (interfaces == null || interfaces.length == 0)
            throw new BaseAppException(ErrorMessageEnum.EXTENSION_NEED_POINT_INTERFACE_F, targetClz);
        for (Class intf : interfaces) {
            String extensionPoint = intf.getSimpleName();
            if (extensionPoint.contains(ExtensionConstant.EXTENSION_EXTPT_NAMING))
                return intf.getName();
        }
        throw new BaseAppException(ErrorMessageEnum.EXTENSION_END_NAME_NOT_VALID_F, targetClz, ExtensionConstant.EXTENSION_EXTPT_NAMING);
    }

}