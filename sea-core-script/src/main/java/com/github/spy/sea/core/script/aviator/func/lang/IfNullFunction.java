package com.github.spy.sea.core.script.aviator.func.lang;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/18
 * @since 1.0
 */
@Slf4j
public class IfNullFunction extends AbstractVariadicFunction {

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        String originValue = FunctionUtils.getStringValue(args[0], env);
        String defaultValue = FunctionUtils.getStringValue(args[1], env);

        return new AviatorString(Optional.ofNullable(originValue).orElse(defaultValue));
    }

    @Override
    public String getName() {
        return "ifnull";
    }


}
