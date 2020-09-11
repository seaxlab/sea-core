package com.github.spy.sea.core.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基础接口
 *
 * @author spy
 */
@Data
public class BasePageQueryDTO implements Serializable {

    @Builder.Default
    protected Integer page = 1;

    @Builder.Default
    protected Integer size = 10;

    //@Data 注解不一定是最好的，有默认值的，字段比较少的，可以手动生产get/set方法
}
