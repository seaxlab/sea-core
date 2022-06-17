package com.github.seaxlab.core.component.transform;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/24
 * @since 1.0
 */
@Data
public class FieldRule {

    /**
     * 源属性
     */
    private String source;

    /**
     * 目标属性
     */
    private String target;

    /**
     * 值解析器
     */
    private ValueParser valueParser;


    /**
     * 创建规则，节点名称替换
     *
     * @param source 源节点
     * @param target 目标节点
     * @return
     */
    public static FieldRule create(String source, String target) {
        FieldRule rule = new FieldRule();
        rule.setSource(source);
        rule.setTarget(target);

        return rule;
    }

    /**
     * 创建字段替换规则，节点名称和值均可替换
     *
     * @param source 源节点
     * @param target 目标节点
     * @param parser 值解析器
     * @return
     */
    public static FieldRule create(String source, String target, ValueParser parser) {
        FieldRule rule = new FieldRule();
        rule.setSource(source);
        rule.setTarget(target);
        rule.setValueParser(parser);

        return rule;
    }

}
