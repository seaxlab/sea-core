package com.github.spy.sea.core.support.notify.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/12/25
 * @since 1.0
 */
@Slf4j
public final class DingDingUtil {

    /**
     * access_token方式
     */
    public static final String URL_SIMPLE = "https://oapi.dingtalk.com/robot/send?access_token={}";

    /**
     * 签名方式url
     */
    public static final String URL_SIGN = "https://oapi.dingtalk.com/robot/send?access_token={}&timestamp={}&sign={}";

    /**
     * 生成签名
     *
     * @param timestamp
     * @param secret
     * @return
     */
    public static String getSign(Long timestamp, String secret) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");

        } catch (Exception e) {
            log.error("make dingding sign error", e);
        }

        return null;
    }
}
