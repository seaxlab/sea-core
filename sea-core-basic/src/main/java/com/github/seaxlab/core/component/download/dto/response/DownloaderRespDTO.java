package com.github.seaxlab.core.component.download.dto.response;

import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
@Data
public class DownloaderRespDTO {

  private String fileUrl;
  private String fileName;
  private String bizKey;
}
