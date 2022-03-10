package com.github.spy.sea.core.component.transform;

import lombok.Data;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/25
 * @since 1.0
 */
@Data
public class TransformConfig {

    private Class objectClass;

    private BeanRule beanRule;

    private List<FieldRule> fieldRules;

    /**
     * 根节点
     */
    private String resultData;

    /**
     * 一级节点
     */
    private String pathName1;

    /**
     * 二级节点
     */
    private String pathName2;

    /**
     * 三级节点
     */
    private String pathName3;

    /**
     * 四级节点
     */
    private String pathName4;


    public static TransformConfig create(Class objectClass) {
        TransformConfig cfg = new TransformConfig();
        cfg.setObjectClass(objectClass);
        return cfg;
    }

    public static TransformConfig create(Class objectClass, List<FieldRule> fieldRules) {
        TransformConfig cfg = new TransformConfig();
        cfg.setObjectClass(objectClass);
        cfg.setFieldRules(fieldRules);

        return cfg;
    }

    public static TransformConfig create(Class objectClass, List<FieldRule> fieldRules, BeanRule beanRule) {
        TransformConfig cfg = new TransformConfig();
        cfg.setObjectClass(objectClass);
        cfg.setFieldRules(fieldRules);
        cfg.setBeanRule(beanRule);
        return cfg;
    }

    public static TransformConfig create(Class objectClass, List<FieldRule> fieldRules, BeanRule beanRule, String pathName1) {
        TransformConfig cfg = new TransformConfig();
        cfg.setObjectClass(objectClass);
        cfg.setFieldRules(fieldRules);
        cfg.setBeanRule(beanRule);
        cfg.setPathName1(pathName1);

        return cfg;
    }

    public static TransformConfig create(Class objectClass, List<FieldRule> fieldRules, BeanRule beanRule,
                                         String pathName1, String pathName2) {
        TransformConfig cfg = new TransformConfig();
        cfg.setObjectClass(objectClass);
        cfg.setFieldRules(fieldRules);
        cfg.setBeanRule(beanRule);
        cfg.setPathName1(pathName1);
        cfg.setPathName2(pathName2);

        return cfg;
    }

    public static TransformConfig create(Class objectClass, List<FieldRule> fieldRules, String pathName1) {
        TransformConfig cfg = new TransformConfig();
        cfg.setObjectClass(objectClass);
        cfg.setFieldRules(fieldRules);
        cfg.setPathName1(pathName1);

        return cfg;
    }

    public static TransformConfig create(Class objectClass, List<FieldRule> fieldRules, String pathName1, String pathName2) {

        TransformConfig cfg = new TransformConfig();
        cfg.setObjectClass(objectClass);
        cfg.setFieldRules(fieldRules);
        cfg.setPathName1(pathName1);
        cfg.setPathName2(pathName2);

        return cfg;
    }
}
