# SeaCoreConfig

````
 DefaultPointCutAdvisor advisor = new DefaultPointCutAdvisor(new LogCostPointCut());

        advisor.setAdviceBeanName("seaLogCostPointcutAdvisor");
        advisor.setAdvice(new LogCostMethodInterceptor());
        advisor.setOrder(OrderedEnum.LOG_COST.getCode());
````

````
//        DefaultPointCutAdvisor advisor = new DefaultPointCutAdvisor(new LogRequestPointcut());
//        advisor.setAdviceBeanName("seaLogRequestPointcutAdvisor");
//        advisor.setAdvice(new LogRequestMethodInterceptor());
//        advisor.setOrder(OrderedEnum.LOG_REQUEST.getCode());
````