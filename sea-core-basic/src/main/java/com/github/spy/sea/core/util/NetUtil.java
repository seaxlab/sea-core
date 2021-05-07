package com.github.spy.sea.core.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * net work util
 *
 * @author spy
 * @version 1.0 2019-05-31
 * @since 1.0
 */
@Slf4j
public final class NetUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetUtil.class);
    private static final String LOCALHOST = "127.0.0.1";

    private static final String ANY_HOST = "0.0.0.0";

    private static volatile InetAddress LOCAL_ADDRESS = null;

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    /**
     * To string address string.
     *
     * @param address the address
     * @return the string
     */
    public static String toStringAddress(SocketAddress address) {
        return toStringAddress((InetSocketAddress) address);
    }

    /**
     * To ip address string.
     *
     * @param address the address
     * @return the string
     */
    public static String toIpAddress(SocketAddress address) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
        return inetSocketAddress.getAddress().getHostAddress();
    }

    /**
     * To string address string.
     *
     * @param address the address
     * @return the string
     */
    public static String toStringAddress(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }

    /**
     * To inet socket address inet socket address.
     *
     * @param address the address
     * @return the inet socket address
     */
    public static InetSocketAddress toInetSocketAddress(String address) {
        int i = address.indexOf(':');
        String host;
        int port;
        if (i > -1) {
            host = address.substring(0, i);
            port = Integer.parseInt(address.substring(i + 1));
        } else {
            host = address;
            port = 0;
        }
        return new InetSocketAddress(host, port);
    }

    /**
     * To long long.
     *
     * @param address the address
     * @return the long
     */
    public static long toLong(String address) {
        InetSocketAddress ad = toInetSocketAddress(address);
        String[] ip = ad.getAddress().getHostAddress().split("\\.");
        long r = 0;
        r = r | (Long.parseLong(ip[0]) << 40);
        r = r | (Long.parseLong(ip[1]) << 32);
        r = r | (Long.parseLong(ip[2]) << 24);
        r = r | (Long.parseLong(ip[3]) << 16);
        r = r | ad.getPort();
        return r;
    }

    /**
     * Gets local ip.
     *
     * @return the local ip
     */
    public static String getLocalIp() {
        InetAddress address = getLocalAddress();
        return address == null ? LOCALHOST : address.getHostAddress();
    }

    /**
     * Gets local host.
     *
     * @return the local host
     */
    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? "localhost" : address.getHostName();
    }

    /**
     * Gets local address.
     *
     * @return the local address
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            LOGGER.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    LOGGER.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        LOGGER.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            LOGGER.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }
        LOGGER.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    /**
     * Valid address.
     *
     * @param address the address
     */
    public static void validAddress(InetSocketAddress address) {
        if (null == address.getHostName() || 0 == address.getPort()) {
            throw new IllegalArgumentException("invalid address:" + address);
        }
    }

    /**
     * is valid address
     *
     * @param address
     * @return true if the given address is valid
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        return isValidIp(address.getHostAddress(), false);
    }

    /**
     * is valid IP
     *
     * @param ip
     * @param validLocalAndAny Are 127.0.0.1 and 0.0.0.0 valid IPs?
     * @return true if the given IP is valid
     */
    public static boolean isValidIp(String ip, boolean validLocalAndAny) {
        if (validLocalAndAny) {
            return ip != null && IP_PATTERN.matcher(ip).matches();
        } else {
            return (ip != null
                    && !ANY_HOST.equals(ip)
                    && !LOCALHOST.equals(ip)
                    && IP_PATTERN.matcher(ip).matches());
        }
    }


    /**
     * ip字符串转byte[]
     * byte会溢出 ，byte的范围是[-128,127]
     *
     * @param ipStr
     * @return
     */
    public static byte[] toByte(String ipStr) {
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(ipStr);
        } catch (UnknownHostException e) {
            log.error("to byte exception", e);
            return ArrayUtil.emptyByte();
        }
        return ip.getAddress();
    }

    /**
     * byte[] to ip str
     *
     * @param ip
     * @return
     */
    public static String toString(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }
        StringBuilder builder = new StringBuilder();
        for (byte b : ip) {
            builder.append(b & 0xFF).append(".");
        }
        String ipStr = builder.toString();
        return ipStr.substring(0, ipStr.length() - 1);
    }

    /**
     * 判断是否是内网IPV4
     *
     * @param ip byte
     * @return true/false
     */
    public static boolean isInternalIPV4(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }

        //10.0.0.0~10.255.255.255
        //172.16.0.0~172.31.255.255
        //192.168.0.0~192.168.255.255
        if (ip[0] == (byte) 10) {
            return true;
        } else if (ip[0] == (byte) 172) {
            if (ip[1] >= (byte) 16 && ip[1] <= (byte) 31) {
                return true;
            }
        } else if (ip[0] == (byte) 192) {
            if (ip[1] == (byte) 168) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是IPV6
     *
     * @param inetAddr
     * @return
     */
    public static boolean isInternalIPV6(InetAddress inetAddr) {
        if (inetAddr.isAnyLocalAddress() // Wild card ipv6
                || inetAddr.isLinkLocalAddress() // Single broadcast ipv6 address: fe80:xx:xx...
                || inetAddr.isLoopbackAddress() //Loopback ipv6 address
                || inetAddr.isSiteLocalAddress()) { // Site local ipv6 address: fec0:xx:xx...
            return true;
        }
        return false;
    }

    // apache common-validator
//    private static boolean ipCheck(byte[] ip) {
//        if (ip.length != 4) {
//            throw new RuntimeException("illegal ipv4 bytes");
//        }
//
//        InetAddressValidator validator = InetAddressValidator.getInstance();
//        return validator.isValidInet4Address(ipToIPv4Str(ip));
//    }
//
//    private static boolean ipV6Check(byte[] ip) {
//        if (ip.length != 16) {
//            throw new RuntimeException("illegal ipv6 bytes");
//        }
//
//        InetAddressValidator validator = InetAddressValidator.getInstance();
//        return validator.isValidInet6Address(ipToIPv6Str(ip));
//    }


    /**
     * 获取当前主机公网IP
     */
    public static String getPublicIP() {

        // 这里使用jsonip.com第三方接口获取本地IP
        String jsonip = "https://jsonip.com/";
        // 接口返回结果
        String result = "";
        BufferedReader in = null;
        try {
            // 使用HttpURLConnection网络请求第三方接口
            URL url = new URL(jsonip);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(30 * 1000);
            urlConnection.setReadTimeout(30 * 1000);
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("fail to reach jsonip.com", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("fail to close input stream.", e2);
            }

        }

        try {
            JSONObject json = JSONObject.parseObject(result);
            return (String) json.getOrDefault("ip", "");
        } catch (Exception e) {
            log.error("fail to parse result", e);
        }
        return "";

        // 正则表达式，提取xxx.xxx.xxx.xxx，将IP地址从接口返回结果中提取出来
//        String rexp = "(\\d{1,3}\\.){3}\\d{1,3}";
//        Pattern pat = Pattern.compile(rexp);
//        Matcher mat = pat.matcher(result);
//        String res = "";
//        while (mat.find()) {
//            res = mat.group();
//            break;
//        }
//        return res;
    }
}
