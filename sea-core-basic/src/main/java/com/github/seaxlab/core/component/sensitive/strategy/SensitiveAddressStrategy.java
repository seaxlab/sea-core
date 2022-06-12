package com.github.seaxlab.core.component.sensitive.strategy;


import com.github.seaxlab.core.component.sensitive.enums.SensitiveDefaultLengthEnum;
import com.github.seaxlab.core.component.sensitive.util.SensitiveInfoUtils;

/**
 * 地址脱敏
 *
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitiveAddressStrategy implements IStrategy {

    @Override
    public String desensitization(String address, int begin, int end) {
        if (begin != SensitiveDefaultLengthEnum.ADDRESS.getBegin() && begin != 0) {
            return SensitiveInfoUtils.address(address, begin);
        }
        return SensitiveInfoUtils.address(address, SensitiveDefaultLengthEnum.ADDRESS.getBegin());
    }

}
