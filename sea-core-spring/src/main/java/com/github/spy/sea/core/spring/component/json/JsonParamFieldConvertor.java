package com.github.spy.sea.core.spring.component.json;

import com.alibaba.fastjson.JSON;

/**
 * 对json参数的转换，如加解密或字段校验等
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
public interface JsonParamFieldConvertor<R, P> {

    /**
     * 转换方法
     *
     * @param json  json参数
     * @param paths 映射字段路径
     * @param value 预处理参数
     * @return
     */
    R convert(JSON json, String[] paths, P value) throws Exception;
}
