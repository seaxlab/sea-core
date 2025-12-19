package com.github.seaxlab.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/28
 * @since 1.0
 */
@Slf4j
public final class IpCheckUtil {

  private IpCheckUtil() {
  }

  private static final String PART1 = "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\.";
  private static final String PART2 = "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\.";
  private static final String PART3 = "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\.";
  private static final String PART4 = "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})";

  // IP的正则
  private static final Pattern pattern = Pattern.compile(PART1 + PART2 + PART3 + PART4);
  public static final String DEFAULT_ALLOW_ALL_FLAG = "*";// 允许所有ip标志位
  public static final String DEFAULT_DENY_ALL_FLAG = "0"; // 禁止所有ip标志位

  /**
   * 根据IP地址，及IP白名单设置规则判断IP是否包含在白名单.
   *
   * @param ip           待检测的IP
   * @param limitedIpStr 被限制/允许的IP
   * @return
   */
  public static boolean isPermitted(String ip, String limitedIpStr) {
    if (null == ip || ip.isEmpty()) {
      return false;
    }
    //ip格式不对
    if (!pattern.matcher(ip).matches()) {
      return false;
    }
    if (DEFAULT_ALLOW_ALL_FLAG.equals(limitedIpStr)) {
      return true;
    }
    if (DEFAULT_DENY_ALL_FLAG.equals(limitedIpStr)) {
      return false;
    }
    Set<String> ipList = getAvailableIpList(limitedIpStr);
    return isPermitted(ip, ipList);
  }

  /**
   * 根据IP白名单设置获取可用的IP列表
   *
   * @param allowIp
   * @return set
   */

  private static Set<String> getAvailableIpList(String allowIp) {
    // 拆分出白名单正则
    String[] splitRex = allowIp.split(";");
    Set<String> ipList = new HashSet<>(splitRex.length);
    for (String allow : splitRex) {
      // 处理通配符 *
      if (allow.contains("*")) {
        String[] ips = allow.split("\\.");
        String[] from = new String[]{"0", "0", "0", "0"};
        String[] end = new String[]{"255", "255", "255", "255"};
        List<String> tem = new ArrayList<>();
        for (int i = 0; i < ips.length; i++) {
          if (ips[i].contains("*")) {
            tem = complete(ips[i]);
            from[i] = null;
            end[i] = null;
          } else {
            from[i] = ips[i];
            end[i] = ips[i];
          }
        }
        StringBuilder fromIP = new StringBuilder();
        StringBuilder endIP = new StringBuilder();
        for (int i = 0; i < 4; i++) {
          if (from[i] != null) {
            fromIP.append(from[i]).append(".");
            endIP.append(end[i]).append(".");
          } else {
            fromIP.append("[*].");
            endIP.append("[*].");
          }
        }
        fromIP.deleteCharAt(fromIP.length() - 1);
        endIP.deleteCharAt(endIP.length() - 1);

        for (String s : tem) {
          String ip =
            fromIP.toString().replace("[*]", s.split(";")[0]) + "-" + endIP.toString().replace("[*]", s.split(";")[1]);
          if (validate(ip)) {
            ipList.add(ip);
          }
        }
      } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
        ipList.add(allow);
      } else {// 处理单个 ip 或者 范围
        if (validate(allow)) {
          ipList.add(allow);
        }
      }

    }

    return ipList;
  }

  /**
   * 对单个IP节点进行范围限定
   *
   * @param arg
   * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
   */
  private static List<String> complete(String arg) {
    List<String> com = new ArrayList<>();
    int len = arg.length();
    if (len == 1) {
      com.add("0;255");
    } else if (len == 2) {
      String s1 = complete(arg, 1);
      if (s1 != null) {
        com.add(s1);
      }
      String s2 = complete(arg, 2);
      if (s2 != null) {
        com.add(s2);
      }
    } else {
      String s1 = complete(arg, 1);
      if (s1 != null) {
        com.add(s1);
      }
    }
    return com;
  }

  private static String complete(String arg, int length) {
    String from = "";
    String end = "";
    if (length == 1) {
      from = arg.replace("*", "0");
      end = arg.replace("*", "9");
    } else {
      from = arg.replace("*", "00");
      end = arg.replace("*", "99");
    }
    if (Integer.parseInt(from) > 255) {
      return null;
    }
    if (Integer.parseInt(end) > 255) {
      end = "255";
    }
    return from + ";" + end;
  }

  /**
   * 在添加至白名单时进行格式校验
   *
   * @param ip
   * @return
   */
  private static boolean validate(String ip) {
    String[] temp = ip.split("-");
    for (String s : temp) {
      if (!pattern.matcher(s).matches()) {
        return false;
      }
    }
    return true;
  }

  /**
   * isPermited:(根据IP,及可用Ip列表来判断ip是否包含在白名单之中).
   *
   * @param ip
   * @param ipList
   * @return
   * @date 2017-4-17 下午03:01:03
   */
  private static boolean isPermitted(String ip, Set<String> ipList) {
    if (ipList.isEmpty() || ipList.contains(ip)) {
      return true;
    }
    for (String allow : ipList) {
      if (allow.contains("-")) {// 处理 类似 192.168.0.0-192.168.2.1
        String[] tempAllow = allow.split("-");
        String[] from = tempAllow[0].split("\\.");
        String[] end = tempAllow[1].split("\\.");
        String[] tag = ip.split("\\.");
        boolean check = true;
        for (int i = 0; i < 4; i++) {// 对IP从左到右进行逐段匹配
          int s = Integer.parseInt(from[i]);
          int t = Integer.parseInt(tag[i]);
          int e = Integer.parseInt(end[i]);
          if (!(s <= t && t <= e)) {
            check = false;
            break;
          }
        }
        if (check) {
          return true;
        }
      } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
        int splitIndex = allow.indexOf("/");
        // 取出子网段
        String ipSegment = allow.substring(0, splitIndex); // 192.168.3.0
        // 子网数
        String netmask = allow.substring(splitIndex + 1);// 24
        // ip 转二进制
        long ipLong = ipToLong(ip);
        //子网二进制
        long maskLong = (2L << 32 - 1) - (2L << Integer.valueOf(32 - Integer.valueOf(netmask)) - 1);
        // ip与和子网相与 得到 网络地址
        String calcSegment = longToIP(ipLong & maskLong);
        // 如果计算得出网络地址和库中网络地址相同 则合法
        if (ipSegment.equals(calcSegment)) {
          return true;
        }
      }
    }
    return false;
  }


  private static long ipToLong(String strIP) {
    long[] ip = new long[4];
    // 先找到IP地址字符串中.的位置
    int position1 = strIP.indexOf(".");
    int position2 = strIP.indexOf(".", position1 + 1);
    int position3 = strIP.indexOf(".", position2 + 1);
    // 将每个.之间的字符串转换成整型
    ip[0] = Long.parseLong(strIP.substring(0, position1));
    ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
    ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
    ip[3] = Long.parseLong(strIP.substring(position3 + 1));
    return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
  }

  // 将10进制整数形式转换成127.0.0.1形式的IP地址
  private static String longToIP(long longIP) {
    StringBuilder sb = new StringBuilder();
    // 直接右移24位
    sb.append(longIP >>> 24);
    sb.append(".");
    // 将高8位置0，然后右移16位
    sb.append((longIP & 0x00FFFFFF) >>> 16);
    sb.append(".");
    sb.append((longIP & 0x0000FFFF) >>> 8);
    sb.append(".");
    sb.append(longIP & 0x000000FF);
    return sb.toString();
  }

}
