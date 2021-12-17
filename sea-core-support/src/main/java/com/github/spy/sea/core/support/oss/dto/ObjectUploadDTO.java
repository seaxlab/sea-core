package com.github.spy.sea.core.support.oss.dto;

import com.github.spy.sea.core.support.oss.enums.AclEnum;
import lombok.Data;

import java.io.File;
import java.io.InputStream;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/17
 * @since 1.0
 */
@Data
public class ObjectUploadDTO {
    private String bucket;
    private String key;

    private File file;
    private InputStream inputStream;

    private AclEnum aclEnum;
}
