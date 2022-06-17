package com.github.seaxlab.core.http.jdk;


import com.github.seaxlab.core.util.IOUtil;
import com.github.seaxlab.core.util.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * jdk http client util
 */
public class HttpUtil {

    /**
     * http get
     *
     * @param url           url
     * @param headers       headers
     * @param paramValues   key=value
     * @param encoding      encoding
     * @param readTimeoutMs read timeout
     * @return
     * @throws IOException
     */
    public static HttpResult get(String url, List<String> headers, List<String> paramValues,
                                 String encoding, long readTimeoutMs) throws IOException {
        String encodedContent = encodingParams(paramValues, encoding);
        url += (null == encodedContent) ? "" : ("?" + encodedContent);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout((int) readTimeoutMs);
            conn.setReadTimeout((int) readTimeoutMs);
            setHeaders(conn, headers, encoding);

            conn.connect();
            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                resp = IOUtil.toString(conn.getInputStream(), encoding);
            } else {
                resp = IOUtil.toString(conn.getErrorStream(), encoding);
            }
            return new HttpResult(respCode, resp);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static String encodingParams(List<String> paramValues, String encoding)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if (null == paramValues) {
            return null;
        }

        for (Iterator<String> iter = paramValues.iterator(); iter.hasNext(); ) {
            sb.append(iter.next()).append("=");
            sb.append(URLEncoder.encode(iter.next(), encoding));
            if (iter.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    private static void setHeaders(HttpURLConnection conn, List<String> headers, String encoding) {
        if (null != headers) {
            for (Iterator<String> iter = headers.iterator(); iter.hasNext(); ) {
                conn.addRequestProperty(iter.next(), iter.next());
            }
        }
        //conn.addRequestProperty("Client-Version", MQVersion.getVersionDesc(MQVersion.CURRENT_VERSION));
        conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);

        String ts = String.valueOf(System.currentTimeMillis());
        conn.addRequestProperty("Sea-Core-Client-RequestTS", ts);
    }

    /**
     * post
     *
     * @param url           url
     * @param headers       headers
     * @param paramValues   key=value
     * @param encoding      encoding
     * @param readTimeoutMs read timeout
     * @return the http response of given http post request
     */
    public static HttpResult post(String url, List<String> headers, List<String> paramValues,
                                  String encoding, long readTimeoutMs) throws IOException {
        String encodedContent = encodingParams(paramValues, encoding);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout((int) readTimeoutMs);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            setHeaders(conn, headers, encoding);

            conn.getOutputStream().write(encodedContent.getBytes(StandardCharsets.UTF_8));

            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                resp = IOUtil.toString(conn.getInputStream(), encoding);
            } else {
                resp = IOUtil.toString(conn.getErrorStream(), encoding);
            }
            return new HttpResult(respCode, resp);
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
    }

    /**
     * check url support range
     *
     * @param url request url
     * @return boolean
     * @throws Exception
     */
    public static boolean isSupportRange(String url) throws Exception {
        HttpURLConnection connection = getHttpUrlConnection(url);
        String acceptRanges = connection.getHeaderField("Accept-Ranges");
        closeHttpUrlConnection(connection);

        if (StringUtil.isEmpty(acceptRanges)) {
            return false;
        }
        if ("bytes".equalsIgnoreCase(acceptRanges)) {
            return true;
        }
        return false;
    }

    /**
     * 获取远程文件大小
     *
     * @param netUrl request url
     * @return file content length (byte)
     */
    public static int getFileContentLength(String netUrl) throws Exception {
        HttpURLConnection connection = getHttpUrlConnection(netUrl);
        int contentLength = connection.getContentLength();
        closeHttpUrlConnection(connection);

        return contentLength;
    }

    // -----------------private -------------

    /**
     * 获取连接
     *
     * @param netUrl request url
     */
    private static HttpURLConnection getHttpUrlConnection(String netUrl) throws Exception {
        URL url = new URL(netUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        httpURLConnection.setConnectTimeout(10 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        return httpURLConnection;
    }

    /**
     * close connection
     *
     * @param connection http connection
     */
    private static void closeHttpUrlConnection(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }


    public static class HttpResult {
        final public int code;
        final public String content;

        public HttpResult(int code, String content) {
            this.code = code;
            this.content = content;
        }
    }
}
