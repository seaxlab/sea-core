package com.github.seaxlab.core.lang.jvm.classloader;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

/**
 * clean classloader for new it can resolve multi version jar problem.
 *
 * @author spy
 * @version 1.0 2020/8/22
 * @since 1.0
 */
@Slf4j
public class StandardClassLoader extends URLClassLoader {

  /**
   * @param jarPath jar path, eg: /Users/abc
   */
  public StandardClassLoader(String jarPath) {
    // set Parent is null
    super(new URL[]{}, null);

    loadResource(jarPath);
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    log.info("custom standard class loader load class:{}", name);
    return super.loadClass(name);
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    try {
      return super.findClass(name);
    } catch (ClassNotFoundException e) {
      log.error("fail to find class, then use default class loader.", e);
      return StandardClassLoader.class.getClassLoader().loadClass(name);
    }
  }

  private void loadResource(String jarPath) {
    // 加载对应版本目录下的 Jar 包
    tryLoadJarInDir(jarPath);
    // 加载对应版本目录下的 lib 目录下的 Jar 包
    tryLoadJarInDir(jarPath + File.separator + "lib");
  }

  private void tryLoadJarInDir(String dirPath) {
    File dir = new File(dirPath);
    // 自动加载目录下的jar包
    if (dir.exists() && dir.isDirectory()) {
      File[] subFiles = dir.listFiles();
      if (Objects.isNull(subFiles)) {
        log.warn("files is empty in current directory.");
        return;
      }

      for (File file : subFiles) {
        if (file.isFile() && file.getName().endsWith(".jar")) {
          this.addURL(file);
        }
      }
    }
  }

  private void addURL(File file) {
    try {
      super.addURL(new URL("file", null, file.getCanonicalPath()));
    } catch (Exception e) {
      log.error("fail to add url", e);
    }
  }

}