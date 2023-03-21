package com.github.seaxlab.core.util;

import com.github.seaxlab.core.component.ssh.dto.SshConfig;
import com.github.seaxlab.core.component.ssh.dto.response.SshRespDTO;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.google.common.base.Charsets;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * ssh util (jsch)
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
  public static SshRespDTO setUpPortForwarding(SshConfig config) {
    SshRespDTO resp = new SshRespDTO();

    try {
      Session session = buildSession(config);

      // here assigned port is equal local port
      int assignedPort = session.setPortForwardingL(config.getLocalPort(), config.getRemoteHost(),
        config.getRemotePort());
      log.info("localhost:{} -> {}:{} connection established.", assignedPort, config.getRemoteHost(),
        config.getRemotePort());

      resp.setSession(session);
      resp.setAssignedPort(assignedPort);
    } catch (JSchException e) {
      log.error("fail to connect remote ssh", e);
      ExceptionHandler.publishMsg("fail to build local port forwarding ssh");
    }

    return resp;
  }


  /**
   * 执行命令
   *
   * @param config
   * @param command
   * @return
   */
  public static String executeCmd(SshConfig config, String command) {
    Session session = null;
    ChannelExec channel = null;

    try {
      session = buildSession(config);

      try (PipedOutputStream errPipe = new PipedOutputStream(); PipedInputStream errIs = new PipedInputStream(
        errPipe)) {

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
          return output;
        } else {
          String msg = IOUtils.toString(errIs, Charsets.UTF_8);
          log.warn("fail to exit status={}, msg={}", channel.getExitStatus(), msg);
          ExceptionHandler.publishMsg(msg);
        }
      }
    } catch (Exception e) {
      log.error("fail to connect remote ssh", e);
      ExceptionHandler.publishMsg("执行异常");
    } finally {
      close(channel);
      close(session);
    }
    //
    return "";
  }

  /**
   * rename file
   *
   * @param config
   * @param oldPath
   * @param newPath
   * @return
   */
  public static boolean rename(SshConfig config, String oldPath, String newPath) {
    Session session = null;
    ChannelSftp channel = null;

    try {
      session = buildSession(config);

      log.info("rename {} --> {}", oldPath, newPath);
      channel = (ChannelSftp) session.openChannel("sftp");
      channel.connect();
      channel.rename(oldPath, newPath);

      return true;
    } catch (Exception e) {
      log.error("fail to connect remote ssh", e);
      ExceptionHandler.publishMsg("重命名异常");
    } finally {
      close(channel);
      close(session);
    }
    return false;
  }

  /**
   * upload file by sftp
   *
   * @param config
   * @param localFilePath
   * @param remoteDir
   * @return
   */
  public static boolean upload(SshConfig config, String localFilePath, String remoteDir) {
    Session session = null;
    ChannelSftp channel = null;

    try {
      session = buildSession(config);

      log.info("upload file {} --> {}", localFilePath, remoteDir);
      channel = (ChannelSftp) session.openChannel("sftp");
      channel.connect();
      channel.put(localFilePath, remoteDir);

      return true;
    } catch (Exception e) {
      log.error("fail to connect remote ssh", e);
      ExceptionHandler.publishMsg("上传异常");
    } finally {
      close(channel);
      close(session);
    }
    return false;
  }

  /**
   * download file from remote by sftp
   *
   * @param config
   * @param remoteFilePath
   * @param localDir
   * @return
   */
  public static boolean download(SshConfig config, String remoteFilePath, String localDir) {
    Session session = null;
    ChannelSftp channel = null;

    try {
      session = buildSession(config);

      log.info("download file {} --> {}", remoteFilePath, localDir);
      channel = (ChannelSftp) session.openChannel("sftp");
      channel.connect();
      channel.get(remoteFilePath, localDir);

      return true;
    } catch (Exception e) {
      log.error("fail to connect remote ssh", e);
      ExceptionHandler.publishMsg("下载异常");
    } finally {
      close(channel);
      close(session);
    }
    return false;
  }

  // --------private method.

  private static Session buildSession(SshConfig config) throws JSchException {
    JSch jsch = new JSch();
    if (StringUtil.isNotBlank(config.getPrivateKey())) {
      jsch.addIdentity(config.getPrivateKey());
    }
    Session session = jsch.getSession(config.getSshUserName(), config.getSshHost(), config.getSshPort());

    if (StringUtil.isNotBlank(config.getSshPassword())) {
      session.setPassword(config.getSshPassword());
    }
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
