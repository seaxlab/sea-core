package com.github.spy.sea.core.script.aviator.func.lang;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/18
 * @since 1.0
 */
@Slf4j
public class SumFunction extends AbstractVariadicFunction {
    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        // 这里是主函数
        // log.info("{}", Thread.currentThread().getName());

        BigDecimal sum = new BigDecimal(0);
        for (AviatorObject each : args) {
            if (each.getValue(env) == null) {
                continue;
            }
            Number value = FunctionUtils.getNumberValue(each, env);
            sum = sum.add(new BigDecimal(value.toString()));
        }
        return new AviatorDecimal(new BigDecimal(sum.toString()));
    }

    @Override
    public String getName() {
        return "sum";
    }
}