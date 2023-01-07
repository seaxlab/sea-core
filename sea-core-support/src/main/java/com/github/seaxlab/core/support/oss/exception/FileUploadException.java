package com.github.seaxlab.core.support.oss.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * file upload exception
 *
 * @author spy
 * @version 1.0 2019-08-07
 * @since 1.0
 */
@Slf4j
public class FileUploadException extends RuntimeException {

  public FileUploadException() {
    super("File upload exception.");
  }

  public FileUploadException(String errorMsg) {
    super(errorMsg);
  }

  public FileUploadException(Throwable cause) {
    super(cause);
  }

}
