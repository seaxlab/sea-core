package com.github.spy.sea.core.spring.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/17
 * @since 1.0
 */
@Slf4j
public class HttpParamLogAspectAdapter extends AbstractParamLogAspect {
    @Override
    protected void before(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //url
        String url = request.getRequestURI();

        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String parameter = "";

        if (args != null && args.length > 0) {
            List<Object> logArgs = new ArrayList<>();
            Arrays.stream(args)
                  .forEach(arg -> {
                      if (arg instanceof HttpServletRequest
                              || arg instanceof HttpServletResponse
                              || arg instanceof ModelMap) {
                          return;
                      }
                      logArgs.add(arg);
                  });
            parameter = JSON.toJSONString(logArgs);
        }
        log.info("请求,url:{},入参:{}", url, parameter);
    }
}
