package com.github.spy.sea.core.component.ssh.dto;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
@Data
public class SshConfig {

    private String sshHost;
    private int sshPort;
    private String sshUserName;
    private String sshPassword;

    // private key, such as `.ssh/id_rsa`
    private String privateKey;

    private int localPort;

    private String remoteHost;
    private int remotePort;
}
