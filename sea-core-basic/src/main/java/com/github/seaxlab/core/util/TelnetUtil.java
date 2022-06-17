package com.github.seaxlab.core.util;

import com.github.seaxlab.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/27
 * @since 1.0
 */
@Slf4j
public final class TelnetUtil {
    /**
     * 最小端口号
     */
    private static final int MIN_PORT = 0;
    /**
     * 最大端口号
     */
    private static final int MAX_PORT = 65535;

    //telnet客户端对象VT220/VT52
    TelnetClient client = new TelnetClient("VT52");

    StringBuffer buffer = new StringBuffer();
    InputStream inputStream = null; // 输入流,接收服务端的返回信息
    OutputStream outputStream = null; // 输出流,向服务端写命令

    private static List<String> defaultPromt = new ArrayList<>();
    private static List<String> user = new ArrayList<>();
    private static List<String> pass = new ArrayList<>();
    //默认端口
    public static int defaultPort = 23;

    static {
        defaultPromt.add("#");
        defaultPromt.add(">");
        defaultPromt.add("%");
        user.add("ogin:");
        pass.add("assword:");
    }

    private String hostname;
    private int port;

    /**
     * telnet constructor
     *
     * @param hostname
     * @param port
     */
    public TelnetUtil(String hostname, int port) {
        if (port < MIN_PORT || port > MAX_PORT) {
            throw new IllegalArgumentException("port should be in range 0~65535.");
        }
        this.hostname = hostname;
        this.port = port;
    }


    /**
     * telnet constructor.
     *
     * @param hostname 服务器IP地址
     * @param port     telnet端口
     * @param username 用户名
     * @param password 密码
     * @throws Exception
     */
    public TelnetUtil(String hostname, int port, String username, String password) throws Exception {
        // 连接服务器
        conn(hostname, port);
        // 获得输入流对象
        this.inputStream = this.client.getInputStream();
        // 获得输出流对象
        this.outputStream = this.client.getOutputStream();

        login(username, password);
    }


    /**
     * check the connection is available.
     *
     * @return Result<StatusEnum>
     */
    public Result<StatusEnum> connectTest() {
        log.info("check [{}:{}]available", this.hostname, this.port);
        Result result = Result.fail();
        StatusEnum status;
        try {
            client.connect(this.hostname, this.port);
            client.disconnect();
            status = StatusEnum.SUCCESS;
        } catch (ConnectException ce) {
            log.error("Could not connect to server={},port={},exception={}", this.hostname, this.port, ce);
            status = StatusEnum.FAIL;
        } catch (UnknownHostException e) {
            log.error("Unknown host: {},exception={}", this.hostname, e);
            status = StatusEnum.ERROR;
        } catch (Exception e) {
            log.error("Error connecting to server: {},exception={}", this.hostname, e);
            status = StatusEnum.UNKNOWN;
        }
        result.value(status);
        return result;
    }

    /**
     * 关闭连接
     *
     * @throws Exception
     */
    public void close() throws Exception {
        this.client.disconnect();
    }

    /**
     * 连接到服务器
     *
     * @param hostname 服务器IP地址
     * @param port     端口
     * @throws Exception
     */
    private void conn(String hostname, int port) throws Exception {
        this.client.connect(hostname, port);
    }

    /**
     * 登录服务器
     *
     * @param username 用户名
     * @param password 密码
     * @throws Exception
     */
    private void login(String username, String password) throws Exception {
        sendCommand(username, user);
        List<String> temp = new ArrayList<String>();
        temp.add(":");
        String result = getResult(temp);

        if (!(result.trim().endsWith("word:"))) {
            throw new Exception("Invalid user:" + username);
        }
        temp.add("#");
        temp.add(">");
        temp.add("%");
        sendCommand(password, pass);
        result = getResult(temp);

        if ((result.trim().endsWith("word:"))
                || (result.trim().endsWith("ogin:"))) {
            throw new Exception("Invalid username or password:" + username
                    + " " + password);
        }
    }

    public void sendCommand(String command) throws Exception {
        sendCommand(command, defaultPromt);
    }

    public String getResult() throws Exception {
        return getResult(defaultPromt);
    }

    /**
     * 往服务器输入命令
     *
     * @param command         命令指令
     * @param wantedEndString
     * @throws Exception
     */
    public void sendCommand(String command, List<String> wantedEndString)
            throws Exception {
        waitForString(wantedEndString);
        this.buffer.delete(0, this.buffer.length());
        // 输出输入的命令值
        // System.out.println(command + "\n");
        this.outputStream.write((command + "\n").getBytes());
        this.outputStream.flush();
    }

    public String getResult(List<String> endString) throws Exception {
        waitForString(endString);
        return this.buffer.toString();
    }

    private void waitForString(List<String> wantedEndString) throws Exception {
        int aword = 0;
        boolean matchOne = false;
        while (!(matchOne)) {
            for (int i = 0; i < wantedEndString.size(); ++i) {
                String back = this.buffer.toString().trim();
                if ((back.endsWith((String) wantedEndString.get(i))) && (this.inputStream.available() == 0)) {
                    matchOne = true;
                }
            }
            if (matchOne) {
                return;
            }
            aword = this.inputStream.read();
            // System.out.print((char) aword);
            if (aword < 0) {
                throw new Exception("Connection disconnect...");
            }
            this.buffer.append((char) aword);
        }
    }

    public boolean isClosed() {
        return (!(this.client.isConnected()));
    }

    /**
     * 链接状态
     */
    public static enum StatusEnum {
        SUCCESS, FAIL, ERROR, UNKNOWN
    }
}
