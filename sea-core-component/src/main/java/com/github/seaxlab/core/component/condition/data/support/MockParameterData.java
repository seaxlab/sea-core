package com.github.seaxlab.core.component.condition.data.support;

import com.github.seaxlab.core.component.condition.data.ParameterData;
import com.github.seaxlab.core.component.condition.dto.ConditionContext;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/3
 * @since 1.0
 */
@Slf4j
@LoadLevel(name = "mock")
public class MockParameterData implements ParameterData {

    @Override
    public String builder(ConditionContext context, String paramName) {
        log.warn("this should not be used in pro env.");
        return "mock";
    }
}
