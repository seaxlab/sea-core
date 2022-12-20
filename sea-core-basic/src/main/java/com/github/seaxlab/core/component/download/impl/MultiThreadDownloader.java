package com.github.seaxlab.core.component.download.impl;

import com.github.seaxlab.core.component.download.AbstractDownloader;
import com.github.seaxlab.core.component.download.model.DownloaderDTO;
import com.github.seaxlab.core.http.common.HttpHeaderConst;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.IOUtil;
import com.github.seaxlab.core.util.ObjectUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

/**
 * multi thread downloader file.
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
@Slf4j
public class MultiThreadDownloader extends AbstractDownloader {

  @Override
  public void download(DownloaderDTO dto) throws Exception {
    int cpuCount = Runtime.getRuntime().availableProcessors();
    int threadNum = ObjectUtil.defaultIfNull(dto.getThreadCount(), cpuCount);
    threadNum = threadNum > cpuCount ? cpuCount : threadNum;

    long contentLength = getRemoteFileContentLength(dto);
    log.info("download file size={}", contentLength);

    if (contentLength == 0) {
      FileUtil.createNewFile(getLocalFileAbsolutePath(dto));
      return;
    }

    ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L,
      TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    //均分文件的大小
    long step = contentLength / threadNum;
    String dir = dto.getNewDir();
    String fileName = dto.getNewFileName();

    List<CompletableFuture<File>> futures = new ArrayList<>();
    for (int index = 0; index < threadNum; index++) {
      //计算出每个线程的下载开始位置和结束位置
      String start = step * index + "";
      String end = index == threadNum - 1 ? "" : (step * (index + 1) - 1) + "";
      // temp file
      String tempFilePath = dir + File.separator + "." + fileName + ".download." + index;

      CompletableFuture<File> future = CompletableFuture.supplyAsync(() -> {
        log.info("download temp file={} [{}~{}]", tempFilePath, start, end);
        File tempFile = null;
        try {
          HttpGet httpget = new HttpGet(dto.getRemoteFileUrl());
          httpget.addHeader(HttpHeaderConst.RANGE, "bytes=" + start + "-" + end);
          CloseableHttpResponse resp = HttpClientUtil.getHttpClient().execute(httpget);
          HttpEntity entity = resp.getEntity();
          tempFile = new File(tempFilePath);
          if (entity != null) {
            BufferedInputStream bis = new BufferedInputStream(entity.getContent());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            int inByte;
            while ((inByte = bis.read()) != -1) {
              bos.write(inByte);
            }
            IOUtil.close(bis);
            IOUtil.close(bos);
          } else {
            log.warn("entity is null in http response.");
          }
        } catch (Exception e) {
          log.error("download part file exception", e);
        }
        return tempFile;
      }, executorService).exceptionally(e -> {
        log.error("thread download exception", e);
        //TODO WARNING.
        return null;
      });
      futures.add(future);
    }

    //创建最终文件
    String tmpFilePath = dir + File.separator + fileName + ".download";
    File file = new File(tmpFilePath);
    FileChannel outChannel = new FileOutputStream(file).getChannel();

    futures.forEach(future -> {
      try {
        File tmpFile = future.get();
        FileChannel tmpIn = new FileInputStream(tmpFile).getChannel();
        //合并每个临时文件
        outChannel.transferFrom(tmpIn, outChannel.size(), tmpIn.size());
        tmpIn.close();
        tmpFile.delete(); //合并完成后删除临时文件
      } catch (InterruptedException | ExecutionException | IOException e) {
        log.error("merge file exception.", e);
      }
    });
    outChannel.close();
    executorService.shutdown();

    file.renameTo(new File(dir + File.separator + fileName));
  }
}
