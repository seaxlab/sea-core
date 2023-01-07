package com.github.seaxlab.core.socket.impl;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.socket.SocketClient;
import com.github.seaxlab.core.socket.model.SocketClientConfig;
import com.github.seaxlab.core.socket.model.SocketClientSendData;
import com.github.seaxlab.core.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/5
 * @since 1.0
 */
@Slf4j
public class DefaultSocketClient implements SocketClient {

  private byte[] EMPTY_BYTE = new byte[0];

  private Socket socket;
  private BufferedReader input;
  private BufferedWriter output;

  @Override
  public void init(SocketClientConfig clientConfig) {
    // create a socket with a timeout
    SocketAddress socketAddress = new InetSocketAddress(clientConfig.getHost(), clientConfig.getPort());
    // create a socket
    this.socket = new Socket();

    try {
      // this method will block no more than timeout ms.
      this.socket.connect(socketAddress, clientConfig.getConnectTimeout());
      this.socket.setSoTimeout(clientConfig.getSocketTimeout());
      //TODO to socket client config.
      this.socket.setTcpNoDelay(true);
      this.socket.setKeepAlive(true);

      this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      this.output = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    } catch (IOException e) {
      log.error("fail to connect socket.", e);
      ExceptionHandler.publishMsg("init socket error.");
    }
  }

  @Override
  public byte[] send(SocketClientSendData data) {

    try {
      this.output.write(new String(data.getPlayLoad()));
      this.output.newLine();
      this.output.flush();

      String line = input.readLine();
      return line.getBytes();
    } catch (IOException e) {
      log.error("socket send play load error", e);
    }
    return EMPTY_BYTE;
  }

  @Override
  public void close() {
    if (socket != null) {
      IOUtil.close(output);
      IOUtil.close(input);
      IOUtil.close(socket);
      this.socket = null;
      this.output = null;
      this.input = null;
    }
  }

  @Override
  public boolean isValid() {
    return socket != null
      && socket.isBound()
      && !socket.isClosed()
      && socket.isConnected()
      && !socket.isInputShutdown()
      && !socket.isOutputShutdown();
  }

  @Override
  public void activate() {

  }

  @Override
  public void inActivate() {

  }
}
