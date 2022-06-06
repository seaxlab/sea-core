package com.github.spy.sea.core.spring.component.json;

import com.github.spy.sea.core.spring.component.json.annotation.JsonParam;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;

/**
 * Json param config
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Getter
public class JsonParamConfig {

    private String path = "";
    private boolean enable = true;
    private boolean required = false;
    //TODO confirm
    private boolean xss = true;
    private String defaultValue = null;

    private String[] handleFieldName = null;

    private Class<? extends JsonParamFieldConvertor<?, ?>>[] convertor = null;

    public JsonParamConfig() {
    }

    public JsonParamConfig(MethodParameter parameter) {
        JsonParam classAnnotation = parameter.getDeclaringClass().getAnnotation(JsonParam.class);
        if (classAnnotation != null) {
            path = joinPath(classAnnotation.path(), classAnnotation.root(), path);
            enable = classAnnotation.enable();
            xss = classAnnotation.xss();
            defaultValue = classAnnotation.defaultValue();
            convertor = classAnnotation.convertor();
            handleFieldName = classAnnotation.handleFieldName();
        }

        JsonParam methodAnnotation = parameter.getMethodAnnotation(JsonParam.class);
        if (methodAnnotation != null) {
            path = joinPath(methodAnnotation.path(), methodAnnotation.root(), path);
            enable = methodAnnotation.enable();
            xss = methodAnnotation.xss();
            defaultValue = methodAnnotation.defaultValue();
            convertor = methodAnnotation.convertor();
            handleFieldName = methodAnnotation.handleFieldName();
        }
        boolean flag = true;
        JsonParam parameterAnnotation = parameter.getParameterAnnotation(JsonParam.class);
        if (parameterAnnotation != null) {
            enable = parameterAnnotation.enable();
            xss = parameterAnnotation.xss();
            defaultValue = parameterAnnotation.defaultValue();
            convertor = parameterAnnotation.convertor();
            handleFieldName = parameterAnnotation.handleFieldName();

            if (parameterAnnotation.root()) {
                path = parameterAnnotation.path();
                flag = false;
            } else if (StringUtils.isNotBlank(parameterAnnotation.path())) {
                path = joinPath(methodAnnotation.path(), methodAnnotation.root(), path);
                flag = false;
            }
        }
        if (flag) {
            path = joinPath(methodAnnotation.path(), methodAnnotation.root(), path);
        }

    }


    //---- private

    /**
     * join path
     *
     * @param path
     * @param rootFlag
     * @param basePath
     * @return
     */
    private static String joinPath(String path, boolean rootFlag, String basePath) {
        path = path.trim();
        if (StringUtils.isNotBlank(path)) {
            path = path.trim();
            if (rootFlag) {
                basePath = path;
            } else if (StringUtils.isNotBlank(path)) {
                basePath += "." + path;
            }
        }
        return basePath;
    }

}
