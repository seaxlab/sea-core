package com.github.spy.sea.core.support.notify.dto;

import com.github.spy.sea.core.model.BaseRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/4
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailServerConfigDTO extends BaseRequestDTO {

    private String protocol;

    private String host;

    private Integer port;

    private Boolean isSsl;

    private Integer sslPort;

    private String isAuth;

    private String username;

    private String password;

    private String defaultSender;

    private String defaultSenderName;

    private Boolean debug;

}