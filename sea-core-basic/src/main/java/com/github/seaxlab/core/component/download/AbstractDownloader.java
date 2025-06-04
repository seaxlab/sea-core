package com.github.seaxlab.core.component.download;

import com.github.seaxlab.core.component.download.dto.DownloaderReqDTO;
import com.github.seaxlab.core.component.download.dto.response.DownloaderRespDTO;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.http.common.HttpHeaderConst;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.util.BooleanUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
@Slf4j
public abstract class AbstractDownloader<A extends DownloaderReqDTO> implements Downloader<A> {

  @Override
  public DownloaderRespDTO execute(DownloaderReqDTO dto) {
    log.info("download dto={}", dto);
    Stopwatch stopwatch = Stopwatch.createStarted();

    String fileUrl = parseUrl(dto.getRemoteFileUrl());
    dto.setRemoteFileUrl(fileUrl);

    FileUtil.ensureDir(dto.getNewDir());
    String localFilePath = getLocalFileAbsolutePath(dto);
    if (FileUtil.exist(localFilePath)) {
      if (BooleanUtil.isTrue(dto.getOverwrite())) {
        log.info("local file exist, overwrite=true, so delete it.");
        FileUtil.deleteFiles(localFilePath);
      }
    }
    DownloaderRespDTO respDTO = new DownloaderRespDTO();

    try {
      log.info("download file begin.");
      download(dto);
      respDTO.setFileUrl(getLocalFileAbsolutePath(dto));
      respDTO.setFileName(dto.getNewFileName());
    } catch (Exception e) {
      log.warn("download exception", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_EXCEPTION);
    } finally {
      stopwatch.stop();
      log.info("download file completely, file={} ,cost={}ms", dto.getRemoteFileUrl(),
        stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    return respDTO;
  }

  /**
   * 下载文件
   *
   * @param dto
   */
  public abstract void download(DownloaderReqDTO dto) throws Exception;

  /**
   * 获取新文件全路径
   *
   * @param dto downloaderDTO
   * @return
   */
  protected String getLocalFileAbsolutePath(DownloaderReqDTO dto) {
    return dto.getNewDir() + File.separator + dto.getNewFileName();
  }

  /**
   * 获取远端文件大小
   *
   * @param dto downloaderDTO
   * @return
   * @throws Exception
   */
  protected long getRemoteFileContentLength(DownloaderReqDTO dto) throws Exception {
    HttpHead httpHead = new HttpHead(dto.getRemoteFileUrl());

    try (CloseableHttpResponse response = HttpClientUtil.getHttpClient().execute(httpHead)) {

      if (!response.containsHeader(HttpHeaderConst.CONTENT_LENGTH)) {
        ExceptionHandler.publishMsg("Content-Length is not exist");
      }

      Header header = response.getLastHeader(HttpHeaderConst.CONTENT_LENGTH);

      long contentLength =
        StringUtil.isEmpty(header.getValue()) ? 0 : Long.parseLong(header.getValue());
      log.info("download file size={}", contentLength);
      return contentLength;
    }
  }


  private String parseUrl(String fileUrl) {
    String url = "";
    url = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8);
    return url;
  }


}
