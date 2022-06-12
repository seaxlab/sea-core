package com.github.seaxlab.core.component.download.impl;

import com.github.seaxlab.core.component.download.AbstractDownloader;
import com.github.seaxlab.core.component.download.model.DownloaderDTO;
import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.util.IOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 单线程下载
 *
 * @author spy
 * @version 1.0 2021/2/25
 * @since 1.0
 */
@Slf4j
public class SimpleDownloader extends AbstractDownloader {
    @Override
    public void download(DownloaderDTO dto) throws Exception {
        HttpGet httpget = new HttpGet(dto.getRemoteFileUrl());
        try (CloseableHttpResponse response = HttpClientUtil.getHttpClient().execute(httpget)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                BufferedInputStream bis = new BufferedInputStream(entity.getContent());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(getLocalFileAbsolutePath(dto))));
                int inByte;
                while ((inByte = bis.read()) != -1) {
                    bos.write(inByte);
                }
                IOUtil.close(bis);
                IOUtil.close(bos);
            } else {
                log.warn("entity is null in http response.");
            }
        }
    }
}
