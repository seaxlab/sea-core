package com.github.spy.sea.core.socket.model;

import lombok.Data;

/**
 * Socket通信中需要明确双方的【分割符、还是半关闭方式】完成一次通信交互
 *
 * @author spy
 * @version 1.0 2021/1/5
 * @since 1.0
 */
@Data
public class SocketClientConfig {

    private String host;
    private Integer port;

    /**
     * connect timeout (ms)
     */
    private int connectTimeout = 30 * 1000;

    /**
     * socket timeout (ms)
     */
    private int socketTimeout = 30 * 1000;
}
