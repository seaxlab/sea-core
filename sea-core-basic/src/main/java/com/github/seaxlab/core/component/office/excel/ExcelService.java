package com.github.seaxlab.core.component.office.excel;

import com.github.seaxlab.core.component.office.excel.listener.ReadListener;
import java.io.File;
import java.io.InputStream;

/**
 * excel service
 *
 * @author spy
 * @version 1.0 2023/05/06
 * @since 1.0
 */
public interface ExcelService {

  void read(File file, ReadListener readListener);

  void read(InputStream inputStream, ReadListener readListener);
}
