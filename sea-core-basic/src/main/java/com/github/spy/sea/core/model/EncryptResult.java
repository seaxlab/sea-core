package com.github.spy.sea.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 加密后的响应
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncryptResult implements Serializable {

    /**
     * 响应成功与否
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 消息
     */
    private String message;

    /**
     * appId
     */
    private String appId;

    /**
     * 签名
     */
    private String sign;

    /**
     * 秘钥版本
     */
    private String version;
    /**
     * 内容内容
     */
    private String bizContent;
}
