package com.github.spy.sea.core.component.ssh.resp;

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
public class SshResp {
    private Session session;
    private int assignedPort;
}
