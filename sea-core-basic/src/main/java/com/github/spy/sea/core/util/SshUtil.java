package com.github.spy.sea.core.util;

import com.github.spy.sea.core.component.ssh.dto.SshConfig;
import com.github.spy.sea.core.component.ssh.resp.SshResp;
import com.github.spy.sea.core.model.BaseResult;
import com.google.common.base.Charsets;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/24
 * @since 1.0
 */
@Slf4j
public final class SshUtil {

    private static final int DEFAULT_CONNECT_TIME_OUT = 30 * 1000; //30s
    private static final int DEFAULT_WAIT_TIMEOUT = 100;


    /**
     * connect remote host by local port forwarding and ssh.
     *
     * @param config
     * @return
     */
    public static BaseResult<SshResp> connect(SshConfig config) {
        BaseResult<SshResp> result = BaseResult.fail();

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(config.getSshUserName(), config.getSshHost(), config.getSshPort());
            session.setPassword(config.getSshPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(DEFAULT_CONNECT_TIME_OUT);
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


    /**
     * 执行命令
     *
     * @param config
     * @param command
     * @return
     */
    public static BaseResult<String> executeCmd(SshConfig config, String command) {
        BaseResult<String> result = BaseResult.fail();

        Session session = null;
        ChannelExec channel = null;

        try {
            session = buildSession(config);

            try (PipedOutputStream errPipe = new PipedOutputStream();
                 PipedInputStream errIs = new PipedInputStream(errPipe)) {

                channel = (ChannelExec) session.openChannel("exec");
                channel.setInputStream(null);
                channel.setErrStream(errPipe);
                channel.setCommand(command);

                InputStream is = channel.getInputStream();

                log.info("begin exec cmd [{}]", command);
                channel.connect();
                while (!channel.isEOF()) {
                    Thread.sleep(DEFAULT_WAIT_TIMEOUT);
                }
                String output = IOUtils.toString(is, Charsets.UTF_8);
                IOUtils.close(is);

                if (channel.getExitStatus() == 0) {
                    log.info("exec cmd successfully.");
                    result.value(output);
                } else {
                    String msg = IOUtils.toString(errIs, Charsets.UTF_8);
                    log.warn("fail to exit status={}, msg={}", channel.getExitStatus(), msg);
                    result.setErrorMessage(msg);
                }
            }
        } catch (Exception e) {
            log.error("fail to connect remote ssh", e);
            result.setErrorMessage("fail to build local port forwarding ssh");
        } finally {
            close(channel);
            close(session);
        }
        return result;
    }

    /**
     * rename file
     *
     * @param config
     * @param oldPath
     * @param newPath
     * @return
     */
    public static BaseResult<Boolean> rename(SshConfig config, String oldPath, String newPath) {
        BaseResult<Boolean> result = BaseResult.fail();
        Session session = null;
        ChannelSftp channel = null;

        try {
            session = buildSession(config);

            log.info("rename {} --> {}", oldPath, newPath);
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.rename(oldPath, newPath);

            result.value(true);
        } catch (Exception e) {
            log.error("fail to connect remote ssh", e);
            result.setErrorMessage("fail to build local port forwarding ssh");
        } finally {
            close(channel);
            close(session);
        }
        return result;
    }

    /**
     * upload file by sftp
     *
     * @param config
     * @param localFilePath
     * @param remoteDir
     * @return
     */
    public static BaseResult<Boolean> upload(SshConfig config, String localFilePath, String remoteDir) {
        BaseResult<Boolean> result = BaseResult.fail();

        Session session = null;
        ChannelSftp channel = null;

        try {
            session = buildSession(config);

            log.info("upload file {} --> {}", localFilePath, remoteDir);
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.put(localFilePath, remoteDir);

            result.value(true);
        } catch (Exception e) {
            log.error("fail to connect remote ssh", e);
            result.setErrorMessage("fail to build local port forwarding ssh");
        } finally {
            close(channel);
            close(session);
        }
        return result;
    }

    /**
     * download file from remote by sftp
     *
     * @param config
     * @param remoteFilePath
     * @param localDir
     * @return
     */
    public static BaseResult<Boolean> download(SshConfig config, String remoteFilePath, String localDir) {
        BaseResult<Boolean> result = BaseResult.fail();

        Session session = null;
        ChannelSftp channel = null;

        try {
            session = buildSession(config);

            log.info("download file {} --> {}", remoteFilePath, localDir);
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.get(remoteFilePath, localDir);

            result.value(true);
        } catch (Exception e) {
            log.error("fail to connect remote ssh", e);
            result.setErrorMessage("fail to build local port forwarding ssh");
        } finally {
            close(channel);
            close(session);
        }
        return result;
    }


    // --------private method.

    private static Session buildSession(SshConfig config) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(config.getSshUserName(), config.getSshHost(), config.getSshPort());
        session.setPassword(config.getSshPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(DEFAULT_CONNECT_TIME_OUT);
        log.info("Establishing Connection...");
        session.connect();
        log.info("connection -> {}:{} established.", config.getSshHost(), config.getSshPort());
        return session;
    }

    private static void close(Channel channel) {
        if (channel != null) {
            try {
                channel.disconnect();
            } catch (Exception e) {
                log.error("fail to close channel", e);
            }
        }
    }

    private static void close(Session session) {
        if (session != null) {
            try {
                session.disconnect();
            } catch (Exception e) {
                log.error("fail to close session", e);
            }
        }
    }
}
