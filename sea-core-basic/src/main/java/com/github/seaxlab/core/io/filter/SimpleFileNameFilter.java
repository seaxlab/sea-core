package com.github.seaxlab.core.io.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 文件名后缀全匹配（大小写）过滤
 *
 * @author spy
 * @version 1.0 2021-11-24
 * @since 1.0
 */
public class SimpleFileNameFilter implements FilenameFilter {

    private final String fileExtension;

    public SimpleFileNameFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(fileExtension);
    }

}
