package com.github.spy.sea.core.component.download;

import com.github.spy.sea.core.component.download.model.DownloaderDTO;
import com.github.spy.sea.core.component.download.model.DownloaderVO;
import com.github.spy.sea.core.model.BaseResult;

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
    BaseResult<DownloaderVO> execute(A dto);
}
