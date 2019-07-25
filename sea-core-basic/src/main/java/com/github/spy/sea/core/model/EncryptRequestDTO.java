package com.github.spy.sea.core.model;

import com.github.spy.sea.core.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 加密请求
 * <p>请求签名</p>
 * <p>内容加密</p>
 *
 * @author spy
 * @version 1.0 2019-07-22
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncryptRequestDTO implements Serializable {

    /**
     * 服务商提供的appId
     */
    private String appId;

    /**
     * 请求内容
     */
    private String bizContent;

    /**
     * 支持的加密版本
     */
    private String version;

    /**
     * 请求时间戳 yyyyMMddHHmmss
     */
    private String timestamp;

    /**
     * 请求签名值
     */
    private String sign;


    public BaseResult isValid() {
        BaseResult result = BaseResult.fail();

        if (StringUtil.isEmpty(appId)) {
            result.setErrorMessage("appId不能为空");
            return result;
        }

        if (StringUtil.isEmpty(bizContent)) {
            result.setErrorMessage("bizContent不能为空");
            return result;
        }


        if (StringUtil.isEmpty(version)) {
            result.setErrorMessage("version不能为空");
            return result;
        }


        if (StringUtil.isEmpty(timestamp)) {
            result.setErrorMessage("timestamp不能为空");
            return result;
        }

        if (StringUtil.isEmpty(sign)) {
            result.setErrorMessage("sign不能为空");
            return result;
        }

        result.setSuccess(true);
        return result;
    }
}
