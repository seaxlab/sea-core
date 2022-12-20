package com.github.seaxlab.core.component.download;

import com.github.seaxlab.core.component.download.model.DownloaderDTO;
import com.github.seaxlab.core.component.download.model.DownloaderVO;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.http.common.HttpHeaderConst;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.util.BooleanUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.StringUtil;
import com.google.common.base.Stopwatch;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
@Slf4j
public abstract class AbstractDownloader<A extends DownloaderDTO> implements Downloader {

  @Override
  public Result<DownloaderVO> execute(DownloaderDTO dto) {
    log.info("download dto={}", dto);
    Result<DownloaderVO> result = Result.fail();
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

    try {
      log.info("download file begin.");
      download(dto);
      DownloaderVO vo = new DownloaderVO();
      vo.setFileUrl(getLocalFileAbsolutePath(dto));
      vo.setFileName(dto.getNewFileName());

      result.setSuccess(true);
      result.setData(vo);
    } catch (Exception e) {
      log.error("download exception.", e);
      result.setMsg(e.getMessage());
    } finally {
      log.info("download file completely.");
    }
    stopwatch.stop();
    log.info("download file={} ,cost={}ms", dto.getRemoteFileUrl(),
      stopwatch.elapsed(TimeUnit.MILLISECONDS));

    return result;
  }

  /**
   * 下载文件
   *
   * @param dto
   */
  public abstract void download(DownloaderDTO dto) throws Exception;

  /**
   * 获取新文件全路径
   *
   * @param dto downloaderDTO
   * @return
   */
  protected String getLocalFileAbsolutePath(DownloaderDTO dto) {
    return dto.getNewDir() + File.separator + dto.getNewFileName();
  }

  /**
   * 获取远端文件大小
   *
   * @param dto downloaderDTO
   * @return
   * @throws Exception
   */
  protected long getRemoteFileContentLength(DownloaderDTO dto) throws Exception {
    HttpHead httpHead = new HttpHead(dto.getRemoteFileUrl());
    CloseableHttpResponse response = HttpClientUtil.getHttpClient().execute(httpHead);

    if (!response.containsHeader(HttpHeaderConst.CONTENT_LENGTH)) {
      ExceptionHandler.publishMsg("Content-Length is not exist");
    }

    Header header = response.getLastHeader(HttpHeaderConst.CONTENT_LENGTH);

    long contentLength =
      StringUtil.isEmpty(header.getValue()) ? 0 : Long.valueOf(header.getValue());
    log.info("download file size={}", contentLength);
    return contentLength;
  }


  private String parseUrl(String fileUrl) {
    String url = "";
    try {
      url = URLDecoder.decode(fileUrl, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      log.error("decode exception", e);
    }
    return url;
  }


}
