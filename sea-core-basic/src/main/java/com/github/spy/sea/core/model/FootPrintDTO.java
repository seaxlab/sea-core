package com.github.spy.sea.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Foot print info
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FootPrintDTO implements Serializable {

    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用版本
     */
    private String appVersion;
    /**
     * 应用标识
     */
    private String appId;
    /**
     * 应用备注信息
     */
    private String appRemark;

    /**
     * 请求标识
     */
    private String requestId;

    private String userId;
    private String username;
    private String token;

    // ---- client info  ----


    private String userAgent;
    private String ip;

    /**
     * channel. eg: alipay
     */
    private String channel;

    /**
     * terminal no. eg: POS00001
     */
    private String terminalNo;

    //----- server info ----


    private String hostname;
    private String port;


    private String creator;
    private String editor;
    private String operator;
}
