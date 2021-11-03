package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * Match predicate judge.
 */
@Slf4j
@LoadLevel(name = "numberRange")
public class NumberRangePredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        int input = Integer.parseInt(realData);

        String[] arrays = conditionData.getParamValue().trim().split(",");

        if (arrays.length != 2) {
            ExceptionHandler.publishMsg("range value format error, plz check.");
        }

        int begin = Integer.parseInt(arrays[0]);
        int end = Integer.parseInt(arrays[1]);

        return input >= begin && input <= end;
    }
}
