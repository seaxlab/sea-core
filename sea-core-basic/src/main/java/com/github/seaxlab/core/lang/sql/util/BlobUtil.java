package com.github.seaxlab.core.lang.sql.util;

import com.github.seaxlab.core.common.CoreConst;
import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Blob utils.
 */
@Slf4j
public final class BlobUtil {

  private BlobUtil() {

  }

  /**
   * String 2 blob blob.
   *
   * @param str the str
   * @return the blob
   */
  public static Blob string2blob(String str) {
    if (str == null) {
      return null;
    }

    try {
      return new SerialBlob(str.getBytes(CoreConst.DEFAULT_CHARSET));
    } catch (Exception e) {
      log.warn("string to blob exception", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  /**
   * Blob 2 string string.
   *
   * @param blob the blob
   * @return the string
   */
  public static String blob2string(Blob blob) {
    if (blob == null) {
      log.warn("blob is null");
      return null;
    }

    try {
      return new String(blob.getBytes((long) 1, (int) blob.length()), CoreConst.DEFAULT_CHARSET);
    } catch (Exception e) {
      log.warn("blob to string exception", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  /**
   * Byte array to blob
   *
   * @param bytes the byte array
   * @return the blob
   */
  public static Blob bytes2Blob(byte[] bytes) {
    if (bytes == null) {
      log.warn("bytes is null");
      return null;
    }

    try {
      return new SerialBlob(bytes);
    } catch (Exception e) {
      log.warn("byte to blob exception", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }

  /**
   * Blob to byte array.
   *
   * @param blob the blob
   * @return the byte array
   */
  public static byte[] blob2Bytes(Blob blob) {
    if (blob == null) {
      log.warn("blob is null.");
      return new byte[0];
    }

    try {
      return blob.getBytes((long) 1, (int) blob.length());
    } catch (Exception e) {
      log.warn("blob to byte exception", e);
      throw new BaseAppException(ErrorMessageEnum.METHOD_EXECUTION_ERROR);
    }
  }
}
