package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * Regex predicate judge.
 */
@Slf4j
@LoadLevel(name = "regex")
public class RegexPredicateJudge implements PredicateJudge {

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        return Pattern.matches(conditionData.getParamValue(), realData);
    }
}
