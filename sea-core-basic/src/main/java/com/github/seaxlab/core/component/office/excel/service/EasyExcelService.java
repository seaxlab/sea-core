package com.github.seaxlab.core.component.office.excel.service;

import com.github.seaxlab.core.component.office.excel.ExcelService;
import com.github.seaxlab.core.component.office.excel.listener.ReadListener;
import java.io.File;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/5/6
 * @since 1.0
 */
@Slf4j
public class EasyExcelService implements ExcelService {

  @Override
  public void read(File file, ReadListener readListener) {

  }

  @Override
  public void read(InputStream inputStream, ReadListener readListener) {

  }
}
