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
}
