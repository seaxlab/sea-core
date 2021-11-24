package com.github.spy.sea.core.io.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * 简单文件过滤器
 *
 * @author spy
 * @version 1.02021-11-24
 * @since 1.0
 */
public class SimpleFileFilter implements FileFilter {

    private final String fileExtension;

    public SimpleFileFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().endsWith(fileExtension);
    }

}
