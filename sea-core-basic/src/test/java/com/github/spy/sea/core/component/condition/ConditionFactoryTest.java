package com.github.spy.sea.core.component.condition;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.component.condition.dto.ConditionContext;
import com.github.spy.sea.core.component.condition.dto.ConditionData;
import com.github.spy.sea.core.component.condition.enums.MatchModeEnum;
import com.github.spy.sea.core.component.condition.enums.OperatorEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/3
 * @since 1.0
 */
@Slf4j
public class ConditionFactoryTest extends BaseCoreTest {

    @Test
    public void testEquals() throws Exception {

        ConditionContext context = new ConditionContext();

        // this is rule
        List<ConditionData> conditionDataList = new ArrayList<>();

        ConditionData conditionData = new ConditionData();
        conditionData.setOperator("equals"); // 完全匹配模式
        conditionData.setParamType("mock"); // 通过mock取值
        conditionData.setParamName("userId"); // key
        conditionData.setParamValue("123456"); // 预定义值

        conditionDataList.add(conditionData);


        boolean result = ConditionFactory.match(context, MatchModeEnum.AND, conditionDataList);
        log.info("result={}", result);
    }

    @Test
    public void testGroovy() throws Exception {
        ConditionContext context = new ConditionContext();

        // this is rule
        List<ConditionData> conditionDataList = new ArrayList<>();

        ConditionData conditionData = new ConditionData();
        conditionData.setOperator("groovy");
        conditionData.setParamType("mock"); // 通过mock取值
        conditionData.setParamName("userId"); // key
        conditionData.setParamValue("'mock'.equals(userId)"); // 预定义值

        conditionDataList.add(conditionData);


        boolean result = ConditionFactory.match(context, MatchModeEnum.AND, conditionDataList);
        log.info("result={}", result);
    }

    @Test
    public void testAntMatch() throws Exception {
        ConditionContext context = new ConditionContext();

        // this is rule
        List<ConditionData> conditionDataList = new ArrayList<>();

        ConditionData conditionData = new ConditionData();
        conditionData.setOperator(OperatorEnum.MATCH.getCode());
        conditionData.setParamType("mock"); // 通过mock取值
        conditionData.setParamName("userId"); // key
        conditionData.setParamValue("mock"); // 预定义值

        conditionDataList.add(conditionData);


        boolean result = ConditionFactory.match(context, MatchModeEnum.AND, conditionDataList);
        log.info("result={}", result);
    }

    @Test
    public void testRegex() throws Exception {
        ConditionContext context = new ConditionContext();

        // this is rule
        List<ConditionData> conditionDataList = new ArrayList<>();

        ConditionData conditionData = new ConditionData();
        conditionData.setOperator(OperatorEnum.REGEX.getCode());
        conditionData.setParamType("mock"); // 通过mock取值
        conditionData.setParamName("userId"); // key
        conditionData.setParamValue("[a-z]+"); // 预定义值

        conditionDataList.add(conditionData);


        boolean result = ConditionFactory.match(context, MatchModeEnum.AND, conditionDataList);
        log.info("result={}", result);
    }

}
