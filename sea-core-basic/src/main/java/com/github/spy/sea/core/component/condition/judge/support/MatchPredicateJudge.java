package com.github.spy.sea.core.component.condition.judge.support;

import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.judge.PredicateJudge;
import com.github.spy.sea.core.component.condition.util.PathMatchUtil;
import com.github.spy.sea.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * Match predicate judge.
 */
@Slf4j
@LoadLevel(name = "match")
public class MatchPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
//        if (Objects.equals(ParamTypeEnum.URI.getName(), conditionData.getParamType())) {
//            return PathMatchUtil.match(conditionData.getParamValue().trim(), realData);
//        }
//        return realData.contains(conditionData.getParamValue().trim());
        return PathMatchUtil.match(conditionData.getParamValue().trim(), realData);
    }
}
