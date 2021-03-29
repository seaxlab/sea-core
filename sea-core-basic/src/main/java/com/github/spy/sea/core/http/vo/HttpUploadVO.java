package com.github.spy.sea.core.http.vo;

import com.github.spy.sea.core.model.BaseResponseVO;
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
    private String content;
}
