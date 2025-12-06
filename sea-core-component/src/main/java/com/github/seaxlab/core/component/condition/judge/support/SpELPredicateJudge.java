
package com.github.seaxlab.core.component.condition.judge.support;

import com.github.seaxlab.core.component.condition.dto.ConditionData;
import com.github.seaxlab.core.component.condition.judge.PredicateJudge;
import com.github.seaxlab.core.loader.LoadLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * SpEL predicate judge.
 */
@Slf4j
@LoadLevel(name = "spEL")
public class SpELPredicateJudge implements PredicateJudge {

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    @Override
    public Boolean judge(final ConditionData conditionData, final String realData) {
        Expression expression = EXPRESSION_PARSER.parseExpression(conditionData.getParamValue().replace('#' + conditionData.getParamName(), realData));
        return expression.getValue(Boolean.class);
    }
}
