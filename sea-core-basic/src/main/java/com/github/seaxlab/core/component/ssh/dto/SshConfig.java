package com.github.seaxlab.core.component.ssh.dto;

import lombok.Data;

/**
 * ssh config
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
@Data
public class SshConfig {

  private String host;
  private Integer port;
  private String userName;
  private String password;

  // private key, such as `.ssh/id_rsa`
  private String privateKey;

  private Integer localPort;

  private String remoteHost;
  private Integer remotePort;
}
