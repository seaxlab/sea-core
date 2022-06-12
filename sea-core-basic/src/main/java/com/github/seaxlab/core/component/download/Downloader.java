package com.github.seaxlab.core.component.download;

import com.github.seaxlab.core.component.download.model.DownloaderDTO;
import com.github.seaxlab.core.component.download.model.DownloaderVO;
import com.github.seaxlab.core.model.Result;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
public interface Downloader<A extends DownloaderDTO> {

    /**
     * download file
     *
     * @param dto
     * @return
     */
    Result<DownloaderVO> execute(A dto);
}
