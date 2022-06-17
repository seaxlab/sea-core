package com.github.seaxlab.core.support.oss.manager.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.support.oss.exception.FileUploadException;
import com.github.seaxlab.core.support.oss.manager.FileUploadManager;
import com.github.seaxlab.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * aliyun file upload manager
 *
 * @author spy
 * @version 1.0 2019-08-07
 * @since 1.0
 */
@Slf4j
public class AliyunFileUploadManager implements FileUploadManager {

    private String endpoint = "";
    private String accessKeyId = "";
    private String accessKeySecret = "";

    /**
     * 根据文件路径上传文件
     *
     * @param localFile
     * @param bucketName
     * @param key
     * @return
     */
    @Override
    public String uploadByFilePath(String localFile, String bucketName, String key) {
        try {
            OSSClient ossClient = getOssClient();
            InputStream inputStream = new FileInputStream(localFile);
            PutObjectResult ret = ossClient.putObject(bucketName, key, inputStream);

            return getFileUrl(bucketName, key);
        } catch (OSSException e) {
            log.error("uploadByFilePath", e);
            throw new FileUploadException(e.getErrorMessage());
        } catch (Exception e) {
            log.error("uploadByFilePath", e);
            throw new FileUploadException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @param bucketName
     * @param key
     * @return
     */
    @Override
    public String uploadByFile(File file, String bucketName, String key) {
        try {
            OSSClient ossClient = getOssClient();
            PutObjectResult ret = ossClient.putObject(bucketName, key, file);

            return getFileUrl(bucketName, key);
        } catch (OSSException e) {
            log.error("uploadByFile", e);
            throw new FileUploadException(e.getErrorMessage());
        } catch (Exception e) {
            log.error("uploadByFile", e);
            throw new FileUploadException(e);
        }
    }

    /**
     * 上传MultipartFile数据
     *
     * @param file
     * @param bucketName
     * @param key
     * @return
     */
    @Override
    public String uploadByMultipartFile(MultipartFile file, String bucketName, String key) {
        try {
            OSSClient ossClient = getOssClient();
            InputStream inputStream = file.getInputStream();
            PutObjectResult ret = ossClient.putObject(bucketName, key, inputStream);

            return getFileUrl(bucketName, key);

        } catch (OSSException e) {
            log.error("uploadByMultipartFile", e);
            throw new FileUploadException(e.getErrorMessage());
        } catch (Exception e) {
            log.error("uploadByMultipartFile", e);
            throw new FileUploadException(e);
        }
    }


    /**
     * 上传字符串
     *
     * @param content
     * @param bucketName
     * @param key
     * @return
     */
    @Override
    public String uploadByString(String content, String bucketName, String key) {
        try {
            OSSClient ossClient = getOssClient();

            PutObjectResult ret = ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));

            return getFileUrl(bucketName, key);
        } catch (OSSException e) {
            log.error("uploadByString", e);
            throw new FileUploadException(e.getErrorMessage());
        } catch (Exception e) {
            log.error("uploadByString", e);
            throw new FileUploadException(e);
        }
    }

    /**
     * 上传byte数组
     *
     * @param content
     * @param bucketName
     * @param key
     * @return
     */
    @Override
    public String uploadByByteArray(byte[] content, String bucketName, String key) {
        try {
            OSSClient ossClient = getOssClient();
            PutObjectResult ret = ossClient.putObject(bucketName, key, new ByteArrayInputStream(content));

            return getFileUrl(bucketName, key);
        } catch (OSSException e) {
            log.error("uploadByByteArray", e);
            throw new FileUploadException(e.getErrorMessage());
        } catch (Exception e) {
            log.error("uploadByByteArray", e);
            throw new FileUploadException(e);
        }
    }

    /**
     * 上传网络流
     *
     * @param urlStr
     * @param bucketName
     * @param key
     * @return
     */
    @Override
    public String uploadByInputStream(String urlStr, String bucketName, String key) {
        try {
            OSSClient ossClient = getOssClient();
            InputStream inputStream = new URL(urlStr).openStream();
            PutObjectResult ret = ossClient.putObject(bucketName, key, inputStream);

            return getFileUrl(bucketName, key);

        } catch (OSSException e) {
            log.error("uploadByInputStream", e);
            throw new FileUploadException(e.getErrorMessage());
        } catch (Exception e) {
            log.error("uploadByInputStream", e);
            throw new FileUploadException(e);
        }
    }


    private OSSClient getOssClient() {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        OSSClient ossClient = new OSSClient(endpoint, credentialsProvider, null);

        return ossClient;
    }

    /**
     * get file url in oss
     *
     * @param bucketName
     * @param key
     * @return
     */
    private String getFileUrl(String bucketName, String key) {
        StringBuilder urlStrBuilder = new StringBuilder(CoreConst.SCHEME_HTTPS)
                .append("://")
                .append(bucketName)
                .append(".")
                .append(endpoint);

        return PathUtil.join(urlStrBuilder.toString(), key);
    }


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

}
