package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-30
 * @since 1.0
 */
@Slf4j
public class NetUtilTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {

    println("localAddress", NetUtil.getLocalAddress());

    println("ip", NetUtil.getLocalIp());
    println("localhost", NetUtil.getLocalHost());
    println("public ip", NetUtil.getPublicIP());

  }

  @Test
  public void publicIpLoopTest() throws Exception {
    for (int i = 0; i < 100; i++) {
      log.info("public ip={}", NetUtil.getPublicIP());
    }
  }

  @Test
  public void testToByte() throws Exception {
    byte[] bytes = NetUtil.toByte("192.168.0.1");
    for (byte b : bytes) {
      System.out.print(b & 0xFF);
      System.out.print(",");
    }
    Assert.assertEquals(192, bytes[0] & 0xFF);
    Assert.assertEquals(168, bytes[1] & 0xFF);
    Assert.assertEquals(0, bytes[2] & 0xFF);
    Assert.assertEquals(1, bytes[3] & 0xFF);
    Assert.assertEquals(true, NetUtil.isInternalIPV4(bytes));
    Assert.assertEquals("192.168.0.1", NetUtil.toString(bytes));
  }

  @Test
  public void testByte() throws Exception {
    //Java中byte的大小是8bits，byte的范围是[-128,127]
    //int的大小是32bits
    log.info("{}", (byte) 192); //这里溢出了
  }

  @Test
  public void testGetAvailablePort() throws Exception {
    for (int i = 0; i < 10; i++) {
      log.info("available port={}", NetUtil.getAvailablePort());
    }
  }

  @Test
  public void testIsAvailablePort() throws Exception {
    for (int i = 0; i < 100; i++) {
      log.info("port={}, available={}", i, NetUtil.isPortInUsed(i));
    }
  }

}
