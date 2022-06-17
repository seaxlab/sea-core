package com.github.seaxlab.core.component.sensitive.strategy;


import com.github.seaxlab.core.component.sensitive.enums.SensitiveDefaultLengthEnum;
import com.github.seaxlab.core.component.sensitive.util.SensitiveInfoUtils;

/**
 * 银行卡号脱敏
 *
 * @author yhq
 * @date 2021年9月6日 16点13分
 **/
public class SensitiveBankCardStrategy implements IStrategy {

    @Override
    public String desensitization(String bankCard, int begin, int end) {
        if (begin != SensitiveDefaultLengthEnum.BANKCARD.getBegin() && begin != 0 &&
                end != SensitiveDefaultLengthEnum.BANKCARD.getEnd() && end != 0) {
            return SensitiveInfoUtils.bankCard(bankCard, begin, end);
        }
        return SensitiveInfoUtils.bankCard(bankCard, SensitiveDefaultLengthEnum.BANKCARD.getBegin(), SensitiveDefaultLengthEnum.BANKCARD.getEnd());
    }

}
