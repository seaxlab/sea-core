package com.github.seaxlab.core.component.download;

import com.github.seaxlab.core.component.download.dto.DownloaderReqDTO;
import com.github.seaxlab.core.component.download.dto.response.DownloaderRespDTO;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
public interface Downloader<A extends DownloaderReqDTO> {

  /**
   * download file
   *
   * @param dto
   * @return
   */
  DownloaderRespDTO execute(A dto);
}
