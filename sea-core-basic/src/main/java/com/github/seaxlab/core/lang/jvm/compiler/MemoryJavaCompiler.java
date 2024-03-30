package com.github.seaxlab.core.lang.jvm.compiler;

import cn.hutool.core.io.FastByteArrayOutputStream;
import com.github.seaxlab.core.common.SymbolConst;
import lombok.extern.slf4j.Slf4j;

import javax.tools.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;

/**
 * memory java compiler
 *
 * @author spy
 * @version 1.0 2021/3/3
 * @since 1.0
 */
@Slf4j
public class MemoryJavaCompiler {
  /**
   * JavaCompiler
   */
  private static final JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();

  /**
   * Compiles the class with the given name and source code.
   *
   * @param className  The name of the class
   * @param sourceCode The source code for the class with name {@code className}
   * @return The resulting byte code from the compilation
   * @throws IllegalArgumentException if the compilation did not succeed
   */
  public static byte[] compile(String className, CharSequence sourceCode) {
    MemoryJavaFileObject file = new MemoryJavaFileObject(className, sourceCode);
    JavaCompiler.CompilationTask task = getCompilationTask(file);

    if (!task.call()) {
      throw new IllegalArgumentException("Could not compile " + className + " with source code :\t" + sourceCode);
    }

    return file.getByteCode();
  }

  private static JavaCompiler.CompilationTask getCompilationTask(MemoryJavaFileObject file) {
    return COMPILER.getTask(null, new FileManagerWrapper(file), null, null, null, Collections.singletonList(file));
  }

  private static class MemoryJavaFileObject extends SimpleJavaFileObject {
    private final String className;
    private final CharSequence sourceCode;
    private final FastByteArrayOutputStream byteCode;

    public MemoryJavaFileObject(String className, CharSequence sourceCode) {
      super(URI.create("string:///" + className.replace(SymbolConst.DOT, SymbolConst.SLASH) + Kind.SOURCE.extension), Kind.SOURCE);
      this.className = className;
      this.sourceCode = sourceCode;
      this.byteCode = new FastByteArrayOutputStream();
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
      return sourceCode;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
      return byteCode;
    }

    public byte[] getByteCode() {
      return byteCode.toByteArray();
    }

    public String getClassName() {
      return className;
    }
  }

  private static class FileManagerWrapper extends ForwardingJavaFileManager<StandardJavaFileManager> {
    private final MemoryJavaFileObject file;

    public FileManagerWrapper(MemoryJavaFileObject file) {
      super(COMPILER.getStandardFileManager(null, null, null));
      this.file = file;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
      if (!file.getClassName().equals(className)) {
        throw new IOException("Expected class with name " + file.getClassName() + ", but got " + className);
      }
      return file;
    }
  }

}
