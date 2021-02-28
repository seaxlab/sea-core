package com.github.spy.sea.core.component.download.model;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
@Data
public class DownloaderDTO {

    /**
     * remote file url
     */
    private String remoteFileUrl;

    /**
     * auth header key
     */
    private String authHeaderKey;

    /**
     * auth header value.
     */
    private String authHeaderValue;

    /**
     * local file dir
     */
    private String newDir;

    /**
     * local file name
     */
    private String newFileName;

    /**
     * overwrite exist file.
     */
    private Boolean overwrite;

    /**
     * 开启多少个线程下载
     */
    private Integer threadCount;

    /**
     * 业务键，回传给调用方
     */
    private String bizKey;
}
