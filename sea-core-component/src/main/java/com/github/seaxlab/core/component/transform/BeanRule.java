package com.github.seaxlab.core.component.transform;

/**
 * 对象转换操作
 */
public abstract class BeanRule {

    /**
     * 对象转换之后的处理
     *
     * @param bean
     */
    public abstract void parse(Object bean);

    public static void parse(Object bean, BeanRule rule) {
        if (rule == null) {
            return;
        }
        rule.parse(bean);
    }

}
