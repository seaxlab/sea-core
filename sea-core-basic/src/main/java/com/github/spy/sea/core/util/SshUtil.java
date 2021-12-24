package com.github.spy.sea.core.util;

import com.github.spy.sea.core.model.BaseResult;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
@Slf4j
public final class SshUtil {

    public static BaseResult<SshResp> connect(SshConfig config) {
        BaseResult<SshResp> result = BaseResult.fail();

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(config.getSshUserName(), config.getSshHost(), config.getSshPort());
            session.setPassword(config.getSshPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            log.info("Establishing Connection...");
            session.connect();

            // here assigned port is equal local port
            int assignedPort = session.setPortForwardingL(config.getLocalPort(), config.getRemoteHost(), config.getRemotePort());
            log.info("localhost:{} -> {}:{} connection established.", assignedPort, config.getRemoteHost(), config.getRemotePort());

            SshResp resp = new SshResp();
            resp.setSession(session);
            resp.setAssignedPort(assignedPort);
            result.value(resp);
        } catch (Exception e) {
            log.error("fail to connect remote ssh", e);
            result.setErrorMessage("fail to build local port forwarding ssh");
        }

        return result;
    }


    @Data
    public static class SshConfig {
        private String sshHost;
        private int sshPort;
        private String sshUserName;
        private String sshPassword;

        private int localPort;

        private String remoteHost;
        private int remotePort;
    }

    @Data
    public static class SshResp {
        private Session session;
        private int assignedPort;
    }

}
