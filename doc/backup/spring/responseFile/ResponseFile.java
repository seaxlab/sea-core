package com.github.seaxlab.core.spring.component.responseFile;

import lombok.Data;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.InputStream;

/**
 * Response file
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Data
public class ResponseFile {

  private File file;

  private InputStream inputStream;

  private String attachmentName;

  private MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

}
