package com.github.seaxlab.core.web.util;

import com.github.seaxlab.core.web.BaseCoreWebTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/16
 * @since 1.0
 */
@Slf4j
public class RequestUtilTest extends BaseCoreWebTest {

    @Test
    public void run16() throws Exception {

        Map<String, String> headers = new HashMap<>();
        headers.put("HTTP_CLIENT_IP", "10.122.2.103");
        headers.put("Content-Type", "text/html");

        Iterator<String> iterator = headers.keySet().iterator();
        Enumeration headerNames = new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public String nextElement() {
                return iterator.next();
            }
        };

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getHeaderNames()).thenReturn(headerNames);
        when(request.getHeader(anyString())).thenReturn("10.12.10.222");


        log.info("ip={}", RequestUtil.getClientIpAddress(request));
    }
}
