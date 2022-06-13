package com.github.seaxlab.core.web.filter.request;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.util.HtmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * XSS Http Servlet request
 *
 * @author spy
 * @version 1.0 2022/5/12
 * @since 1.0
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StringUtils.isNotEmpty(value)) {
            // remove html tag in data
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (StringUtils.isNotEmpty(value)) {
                    value = HtmlUtil.filter(value);
                }
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameters = super.getParameterMap();
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                String[] values = parameters.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if (StringUtils.isNotEmpty(value)) {
                        value = HtmlUtil.filter(value);
                    }
                    values[i] = value;
                }
                map.put(key, values);
            }
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (StringUtils.isNotEmpty(value)) {
            // remove html tag from data
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream in = super.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        BufferedReader buffer = new BufferedReader(reader);
        String line = buffer.readLine();
        // read bytes to buffer reader.
        StringBuffer body = new StringBuffer();
        try {
            while (line != null) {
                body.append(line);
                line = buffer.readLine();
            }
        } finally {
            in.close();
            reader.close();
            buffer.close();
        }

        // convert string to map
        //JSONObject jsonObj = JSON.parseObject(body.toString());

        Map<String, Object> map = JSON.parseObject(body.toString(), Map.class);
        // check map each value
        Map<String, Object> temp = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            Object obj = map.get(key);
            if (obj instanceof String) {
                if (StringUtils.isNotEmpty(obj.toString())) {
                    obj = HtmlUtil.filter(obj.toString());
                }
            }
            temp.put(key, obj);
        }
        //Map对象转换成json格式的字符串
        String json = JSON.toJSONString(temp);
        //创建IO流冲json中读数据
        ByteArrayInputStream res = new ByteArrayInputStream(json.getBytes());

        return new ServletInputStream() {
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

            @Override
            public int read() throws IOException {
                return res.read();
            }
        };
    }
}
