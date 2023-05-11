package com.github.seaxlab.core.support.oss.manager;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-10
 * @since 1.0
 */
public interface FileUploadManager {

  /**
   * 根据文件路径上传文件
   *
   * @param localFile
   * @param bucketName
   * @param key
   * @return
   */
  String uploadByFilePath(String localFile, String bucketName, String key);

  /**
   * 上传文件
   *
   * @param file
   * @param bucketName
   * @param key
   * @return
   */
  String uploadByFile(File file, String bucketName, String key);

  /**
   * 上传MultipartFile数据
   *
   * @param file
   * @param bucketName
   * @param key
   * @return
   */
  String uploadByMultipartFile(MultipartFile file, String bucketName, String key);


  /**
   * 上传字符串
   *
   * @param content
   * @param bucketName
   * @param key
   * @return
   */
  String uploadByString(String content, String bucketName, String key);

  /**
   * 上传byte数组
   *
   * @param content
   * @param bucketName
   * @param key
   * @return
   */
  String uploadByByteArray(byte[] content, String bucketName, String key);

  /**
   * 上传网络流
   *
   * @param urlStr
   * @param bucketName
   * @param key
   * @return
   */
  String uploadByInputStream(String urlStr, String bucketName, String key);

}
