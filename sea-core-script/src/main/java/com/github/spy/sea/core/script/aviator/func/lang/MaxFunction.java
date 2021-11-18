package com.github.spy.sea.core.script.aviator.func.lang;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/18
 * @since 1.0
 */
@Slf4j
public class MaxFunction extends AbstractVariadicFunction {

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        List<BigDecimal> list = new ArrayList<>();
        for (AviatorObject each : args) {
            if (each.getValue(env) == null) {
                continue;
            }
            Number value = FunctionUtils.getNumberValue(each, env);
            list.add(new BigDecimal(value.toString()));
        }
        list.sort(Comparator.comparing(BigDecimal::doubleValue).reversed());
        return new AviatorDecimal(new BigDecimal(list.get(0).toString()));
    }

    @Override
    public String getName() {
        return "min";
    }

}
