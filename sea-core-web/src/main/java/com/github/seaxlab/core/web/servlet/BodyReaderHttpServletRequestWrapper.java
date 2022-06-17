package com.github.seaxlab.core.web.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 复制 HttpServletRequest 的输入流，不然拿出 body 参数后验签后，到 Controller 时，接收参数会为null<br/>
 * 注意需在Filter中使用，spring mvc interceptor中使用无效<br/><br/>
 * <pre>
 * HttpServletRequest request = (HttpServletRequest) req;
 * HttpServletRequest wrapperRequest = new BodyReaderHttpServletRequestWrapper(request);
 * //这里wrapperRequest，对request body中数据读取
 * // 交给下一个filter，可以重复读取request body流内容
 * chain.doFilter(wrapperRequest, response);
 * </pre>
 *
 * @author spy
 * @version 1.0 2022/4/28
 * @since 1.0
 */
@Slf4j
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        String sessionStream = getBodyString(request);
        body = sessionStream.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public int read() {

                return bais.read();
            }

            @Override
            public boolean isFinished() {

                return false;
            }

            @Override
            public boolean isReady() {

                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }


    // -----------------private

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    private String getBodyString(final ServletRequest request) {

        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = cloneInputStream(request.getInputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("io exception", e);
        }
        return sb.toString();
    }

    /**
     * 复制输入流</br>
     *
     * @param inputStream
     * @return input stream
     */
    private InputStream cloneInputStream(ServletInputStream inputStream) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            log.error("io exception", e);
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }


}
