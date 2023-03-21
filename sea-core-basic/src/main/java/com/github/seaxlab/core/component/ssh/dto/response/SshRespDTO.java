package com.github.seaxlab.core.component.ssh.dto.response;

import com.jcraft.jsch.Session;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
@Data
public class SshRespDTO {

  private Session session;
  private int assignedPort;
}
