package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ping util
 *
 * @author spy
 * @version 1.0 2020/8/27
 * @since 1.0
 */
@Slf4j
public final class PingUtil {

  /**
   * ping ip
   *
   * @param ipAddress 这里只能是ip不能是domain
   * @return true: host可用；false: host不可用
   * @throws Exception
   */
  public static boolean ping(String ipAddress) throws Exception {
    // 超时应该在3钞以上
    int timeout = 3000;
    boolean status = InetAddress.getByName(ipAddress).isReachable(timeout);
    return status;
  }

  /**
   * ping ip or domain.
   *
   * @param ipAddress ip or domain
   * @param pingTimes ping times
   * @param timeout   timeout by second.
   * @return
   */
  public static boolean ping(String ipAddress, int pingTimes, int timeout) {
    if (pingTimes <= 0) {
      pingTimes = 3;
    }
    if (timeout <= 0) {
      timeout = 10;
    }

    BufferedReader in = null;
    Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
    String pingCommand = "ping " + ipAddress + " -c " + pingTimes;
    if (SystemInfoUtil.isLinux()) {
      pingCommand += " -w " + timeout;
    }
    log.info("cmd={}", pingCommand);

    try {
      // 执行命令并获取输出
      Process p = r.exec(pingCommand);
      if (p == null) {
        return false;
      }
      in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
      int connectedCount = 0;
      String line = null;
      while ((line = in.readLine()) != null) {
        connectedCount += getCheckResult(line);
      }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
      return connectedCount == pingTimes;

    } catch (Exception ex) {
      log.error("ping exception", ex);
      return false;
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        log.error("ping io exception", e);
      }
    }
  }

  //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
  private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
    // (\d+ms)(\s+)(TTL=\d+)
    Pattern pattern = Pattern.compile("(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      return 1;
    }
    return 0;
  }
}
