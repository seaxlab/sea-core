package com.github.seaxlab.core.spring.interceptor;

import com.github.seaxlab.core.config.ConfigurationFactory;
import com.github.seaxlab.core.util.StringUtil;
import com.github.seaxlab.core.web.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * print simple request log
 * <p>
 * cannot read requestBody
 * </p>
 *
 * @author spy
 * @version 1.0 2019-08-09
 * @since 1.0
 */
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {
    public static final String DEFAULT_BEFORE_MESSAGE_PREFIX = "";

    public static final String DEFAULT_BEFORE_MESSAGE_SUFFIX = "";

    public static final String DEFAULT_AFTER_MESSAGE_PREFIX = "After request [";

    public static final String DEFAULT_AFTER_MESSAGE_SUFFIX = "]";

    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 10000;


    private boolean includeQueryString = true;

    private boolean includeClientInfo = false;

    private boolean includeHeaders = false;

    private boolean includePayload = true;

    private int maxPayloadLength = DEFAULT_MAX_PAYLOAD_LENGTH;

    private String beforeMessagePrefix = DEFAULT_BEFORE_MESSAGE_PREFIX;

    private String beforeMessageSuffix = DEFAULT_BEFORE_MESSAGE_SUFFIX;

    private String afterMessagePrefix = DEFAULT_AFTER_MESSAGE_PREFIX;

    private String afterMessageSuffix = DEFAULT_AFTER_MESSAGE_SUFFIX;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean showRequestLog = ConfigurationFactory.getInstance().getBoolean("sea.core.spring.interceptor.request.log", true);

        if (showRequestLog) {
            String requestLog = getBeforeMessage(request);
            log.info(requestLog);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


    /**
     * Get the message to write to the log before the request.
     *
     * @see #createMessage
     */
    private String getBeforeMessage(HttpServletRequest request) {
        return createMessage(request, this.beforeMessagePrefix, this.beforeMessageSuffix);
    }

    /**
     * Get the message to write to the log after the request.
     *
     * @see #createMessage
     */
    private String getAfterMessage(HttpServletRequest request) {
        return createMessage(request, this.afterMessagePrefix, this.afterMessageSuffix);
    }


    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();

        String contentType = request.getContentType();

        if (StringUtil.isNotEmpty(prefix)) {
            msg.append("\n").append(prefix);
        }
        msg.append("\nuri=").append(request.getRequestURI());

        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append(";queryString=").append(queryString);
            }
        }


        msg.append(";method=").append(request.getMethod())
           .append(";contentType=").append(contentType)
           .append(";contentLength=").append(request.getContentLength())
           .append(";client=").append(request.getRemoteAddr());
        String userAgent = RequestUtil.getUserAgent(request);
        if (StringUtil.isNotEmpty(userAgent)) {
            msg.append(";userAgent=").append(userAgent);
        }

        if (isIncludeHeaders()) {
            msg.append(";headers=").append(new ServletServerHttpRequest(request).getHeaders());
        }

        if (isIncludePayload()) {
            String payload = getMessagePayload(request);
            if (payload != null) {
                msg.append(";payload=").append(payload);
            }
        }


//       NOTE:  requestBody can be read only once.!!
//        if (StringUtil.isNotEmpty(contentType) && contentType.toLowerCase().contains("json")) {
//            String requestBody = RequestUtil.getRequestBody(request);
//            if (requestBody != null) {
//
//                if (JSON.isValid(requestBody)) {
//                    msg.append(";requestBody=").append(JSON.parseObject(requestBody).toJSONString());
//                } else {
//                    log.warn("request body is not valid JSON format");
//                }
//
//            }
//        }

        if (StringUtil.isNotEmpty(suffix)) {
            msg.append("\n").append(suffix);
        }

        return msg.toString();
    }


    protected String getMessagePayload(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, getMaxPayloadLength());
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    return "[unknown]";
                }
            }
        }
        return null;
    }


    public boolean isIncludeQueryString() {
        return includeQueryString;
    }

    public void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    public boolean isIncludeClientInfo() {
        return includeClientInfo;
    }

    public void setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
    }

    public boolean isIncludeHeaders() {
        return includeHeaders;
    }

    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    public boolean isIncludePayload() {
        return includePayload;
    }

    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    public int getMaxPayloadLength() {
        return maxPayloadLength;
    }

    public void setMaxPayloadLength(int maxPayloadLength) {
        this.maxPayloadLength = maxPayloadLength;
    }
}
