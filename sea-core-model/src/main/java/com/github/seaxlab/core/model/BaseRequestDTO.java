package com.github.seaxlab.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求对象基类
 *
 * @author spy
 * @version 1.0 2019-06-02
 * @since 1.0
 */
@Data
public class BaseRequestDTO implements Serializable {

    /**
     * 请求日志Id，便于请求链路追踪
     */
    private String requestId;
}
