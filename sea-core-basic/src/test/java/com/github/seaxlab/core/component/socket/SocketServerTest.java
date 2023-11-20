package com.github.seaxlab.core.component.socket;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.socket.model.SocketRequest;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/6
 * @since 1.0
 */
@Slf4j
public class SocketServerTest extends BaseCoreTest {

  private int port = 9001;
  private ExecutorService executor;
  private boolean loopFlag = true;

  @Test
  public void serverStartTest() throws Exception {
    ServerSocket serverSocket = new ServerSocket(port);
    executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      // create an open ended thread-pool
      ExecutorService threadPool = Executors.newCachedThreadPool();
      try {

        while (loopFlag) {
          // wait for a client to connect
          Socket clientSocket = serverSocket.accept();
          // create a new Service Request object for that socket, and fork it in a background thread
          threadPool.submit(new SocketRequest(clientSocket));
        }
      } catch (IOException ex) {
        log.error("io exception ", ex);
      } finally {
        threadPool.shutdown();
      }
    });

    sleepMinute(10);
  }
}
