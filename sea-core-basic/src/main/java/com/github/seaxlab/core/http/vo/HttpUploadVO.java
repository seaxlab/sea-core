package com.github.seaxlab.core.http.vo;

import com.github.seaxlab.core.model.BaseResponseVO;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/29
 * @since 1.0
 */
@Data
public class HttpUploadVO extends BaseResponseVO {

    /**
     * 服务端响应内容（目前），具体内容，由调用方来反序列化成对象
     */
    private String content;
}
