package com.github.spy.sea.core.spring.component.responseFile;

import com.github.spy.sea.core.exception.Precondition;
import com.github.spy.sea.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Slf4j
public class ResponseFileHandler implements HandlerMethodReturnValueHandler {

    private static final int DEFAULT_BUF_LENGTH = 2 << 12;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getParameterType().isAssignableFrom(ResponseFile.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        ResponseFile responseFile = (ResponseFile) returnValue;
        HttpServletResponse response = getHttpServletResponse(webRequest, responseFile);

        try (OutputStream os = response.getOutputStream(); InputStream in = responseFile.getInputStream()) {
            byte[] buffer = new byte[DEFAULT_BUF_LENGTH];
            int byteRead;
            while ((byteRead = in.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, byteRead);
            }
            os.flush();
        } catch (Exception e) {
            log.error("response out put stream exception", e);
        }
    }

    // ------------- private -----

    private HttpServletResponse getHttpServletResponse(NativeWebRequest webRequest, ResponseFile responseFile) throws UnsupportedEncodingException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Precondition.checkState(request != null && response != null, "");

        response.reset();
        response.setHeader(HttpHeaders.CONNECTION, "close");
        response.setHeader(HttpHeaders.CONTENT_TYPE, responseFile.getMediaType().toString());

        if (responseFile.getMediaType() == MediaType.APPLICATION_OCTET_STREAM) {
            String encodeName = URLEncoder.encode(responseFile.getAttachmentName(), StandardCharsets.UTF_8.name());
            String value = MessageUtil.format("attachment;filename=\"{}\";filename*={}' '{}", encodeName, StandardCharsets.UTF_8.name(), encodeName);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, value);
        }

        return response;
    }
}
