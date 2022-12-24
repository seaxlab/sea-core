package com.github.seaxlab.core.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * File util
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public final class FileUtil {

  /**
   * The Unix separator character.
   */
  private static final char UNIX_SEPARATOR = '/';

  /**
   * The Windows separator character.
   */
  private static final char WINDOWS_SEPARATOR = '\\';

  private FileUtil() {
  }

  /**
   * get file name
   *
   * @param path
   * @return
   */
  public static String getName(final String path) {
    if (path == null || path.isEmpty()) {
      return path;
    }
    int index1 = path.lastIndexOf(UNIX_SEPARATOR);
    int index2 = path.lastIndexOf(WINDOWS_SEPARATOR);
    int index = Math.max(index1, index2);
    return path.substring(index + 1);
  }

  /**
   * check file or directory exist
   *
   * @param path
   * @return
   */
  public static boolean exist(String path) {
    return new File(path).exists();
  }

  /**
   * 确保目录存在
   *
   * @param path
   */
  public static void ensureDir(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
  }


  /**
   * 创建文件，若文件夹不存在则自动创建文件夹，若文件存在则删除旧文件
   *
   * @param path 待创建文件路径
   */
  public static File createNewFile(String path) {
    File file = new File(path);
    try {
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      if (file.exists()) {
        log.info("delete file={}", path);
        file.delete();
      }
      file.createNewFile();
    } catch (IOException e) {
      log.error("io exception", e);
    }
    return file;
  }


  /**
   * read file from classpath
   *
   * @param path
   * @return
   */
  public static String readFormClasspath(String path) {
    //重点：要方式从 file:/x/x/x.jar!/xx.properties读取文件
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    // you should close input stream.
    try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
      return IOUtil.toString(inputStream, StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.error("io exception", e);
    }
    return StringUtil.EMPTY;
//        return readFileToString(PathUtil.getPathFromClassPath(path));
  }

  /**
   * 将本地文件转成流
   *
   * @param filePath
   * @return
   */
  public static InputStream toInputStreamByFilePath(String filePath) {
    try {
      return new FileInputStream(filePath);
    } catch (Exception e) {
      log.error("convert to file input stream error", e);
    }
    return null;
  }

  /**
   * 网络地址转流
   *
   * @param url
   * @return
   */
  public static InputStream toInputStreamByUrl(String url) {
    try {
      return new URL(url).openStream();
    } catch (Exception e) {
      log.error("convert to input stream error", e);
    }
    return null;
  }

  /**
   * 读取文件字节数组
   */
  public static byte[] readFileToBytes(String path) {
    InputStream inputStream = toInputStreamByFilePath(path);
    if (inputStream != null) {
      byte[] data = new byte[1024];
      ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
      int n = 0;
      try {
        while ((n = inputStream.read(data)) != -1) {
          buffer.append(data, 0, n);
        }
        inputStream.close();
      } catch (IOException e) {
        log.error("io exception", e);
      }
      return buffer.toByteArray();
    }
    return null;
  }

  /**
   * 读取文件内容
   */
  public static String readFileToString(String path) {
    return readFileToString(path, Charsets.UTF_8);
  }

  /**
   * 读取文件
   *
   * @param path
   * @param charset
   * @return
   */
  public static String readFileToString(String path, Charset charset) {
    byte[] dataBytes = readFileToBytes(path);
    if (dataBytes != null) {
      return new String(dataBytes, charset);
    }
    return null;
  }

  /**
   * 返回行数组
   *
   * @param path 文件路径
   * @return 行数组
   */
  public static String[] readFileByLines2(String path) {
    String content = readFileByLines(path);
    return content.split("\r\n");
  }

  /**
   * 以行为单位读取文件，常用于读面向行的格式化文件
   *
   * @param path 文件路径
   * @return file content
   */
  public static String readFileByLines(String path) {
    File file = new File(path);
    if (!file.exists() || file.isDirectory()) {
      return StringUtil.empty();
    }
    StringBuffer buffer = new StringBuffer();
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    try {
      fileReader = new FileReader(file);
      bufferedReader = new BufferedReader(fileReader);
      String tempString = null;
      while ((tempString = bufferedReader.readLine()) != null) {
        buffer.append(tempString).append(System.getProperty("line.separator"));
      }
    } catch (Exception e) {
      log.error("io exception", e);
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (Exception e) {
          log.error("io exception", e);
        }
      }
      if (fileReader != null) {
        try {
          fileReader.close();
        } catch (Exception e) {
          log.error("io exception", e);
        }
      }
    }
    return buffer.toString();
  }

  /**
   * 将文件输入流写入文件
   */
  public static boolean writeFileFromInputStream(InputStream inStream, String path) {
    boolean result = true;
    try {
      File file = createNewFile(path);
      FileOutputStream out = new FileOutputStream(file);
      byte[] data = new byte[1024];
      int num = 0;
      while ((num = inStream.read(data, 0, data.length)) != -1) {
        out.write(data, 0, num);
      }
      out.close();
      data = null;
    } catch (Exception e) {
      result = false;
      log.error("io exception", e);
    }
    return result;
  }

  /**
   * write file
   *
   * @param path
   * @param content
   * @return
   */
  public static boolean writeFile(String path, String content) {
    boolean result = true;
    try {
      Files.asCharSink(new File(path), Charsets.UTF_8).write(content);
    } catch (Exception e) {
      result = false;
      log.error("fail to write file", e);
    }
    return result;
  }

  /**
   * write content to file
   *
   * @param path    file path
   * @param content content to write
   * @return
   */
  public static boolean write(String path, String content) {
    boolean result = true;
    try {
      Files.asCharSink(new File(path), Charsets.UTF_8).write(content);
    } catch (Exception e) {
      result = false;
      log.error("fail to write file", e);
    }
    return result;
  }

  /**
   * append content
   *
   * @param path    target file
   * @param content append content
   * @return
   */
  public static boolean append(String path, String content) {
    boolean result = true;
    try {
      // jdk 原生方式
      //Files.write(Paths.get("myfile.txt"), "the text".getBytes(), StandardOpenOption.APPEND);
      Files.asCharSink(new File(path), Charsets.UTF_8, FileWriteMode.APPEND).write(content);
    } catch (Exception e) {
      result = false;
      log.error("fail to file append", e);
    }
    return result;
  }


  /**
   * 根据给出路径自动选择复制文件或整个文件夹
   *
   * @param src  :源文件或文件夹路径
   * @param dest :目标文件或文件夹路径
   */
  public static void copyFiles(String src, String dest) {
    File srcFile = new File(src);
    if (srcFile.exists()) {
      if (srcFile.isFile()) {
        writeFileFromInputStream(toInputStreamByFilePath(src), dest);
      } else {
        File[] subFiles = srcFile.listFiles();
        if (subFiles.length == 0) {
          File subDir = new File(dest);
          subDir.mkdirs();
        } else {
          for (File subFile : subFiles) {
            String subDirPath = dest + System.getProperty("file.separator") + subFile.getName();
            copyFiles(subFile.getAbsolutePath(), subDirPath);
          }
        }
      }
    }
  }

  /**
   * 根据给出路径自动选择删除文件或整个文件夹
   *
   * @param path 文件或文件夹路径
   */
  public static void deleteFiles(String path) {
    log.debug("file path={}", path);

    File file = new File(path);
    if (!file.exists()) {
      return;
    }
    if (file.isFile()) {
      file.delete();// 删除文件
    } else {
      File[] subFiles = file.listFiles();
      for (File subfile : subFiles) {
        // 删除当前目录下的子目录
        deleteFiles(subfile.getAbsolutePath());
      }
      file.delete();// 删除当前目录
    }
  }

  /**
   * clean dir
   *
   * @param directory
   * @throws IOException
   */
  public static void cleanDir(String directory) throws IOException {
    cleanDir(new File(directory));
  }

  /**
   * clean dir
   *
   * @param directory
   * @throws IOException
   */
  public static void cleanDir(File directory) throws IOException {
    String message;
    if (!directory.exists()) {
      message = directory + " does not exist";
      throw new IllegalArgumentException(message);
    } else if (!directory.isDirectory()) {
      message = directory + " is not a directory";
      throw new IllegalArgumentException(message);
    } else {
      IOException exception = null;
      File[] files = directory.listFiles();
      if (files != null) {
        int length = files.length;

        for (int i = 0; i < length; ++i) {
          File file = files[i];
          try {
            FileUtils.forceDelete(file);
          } catch (IOException var8) {
            exception = var8;
          }
        }

        if (null != exception) {
          throw exception;
        }
      }
    }
  }

  /**
   * size of dir
   *
   * @param directory
   * @return
   */
  public static long sizeOfDir(String directory) {
    return sizeOfDir(new File(directory));
  }

  /**
   * size of dir.
   *
   * @param directory
   * @return
   */
  public static long sizeOfDir(File directory) {
    String message;
    if (!directory.exists()) {
      message = directory + " does not exist";
      throw new IllegalArgumentException(message);
    } else if (!directory.isDirectory()) {
      message = directory + " is not a directory";
      throw new IllegalArgumentException(message);
    } else {
      long size = 0L;
      File[] files = directory.listFiles();
      int length = files.length;

      for (int i = 0; i < length; ++i) {
        File file = files[i];
        if (file.isDirectory()) {
          size += sizeOfDir(file);
        } else {
          size += file.length();
        }
      }

      return size;
    }
  }


  /**
   * list files
   *
   * @param path
   * @param fileExtensionList
   * @return
   */
  public static File[] listFiles(final File path, final List<String> fileExtensionList) {
    Preconditions.checkNotNull(path, "path is null");
    Preconditions.checkNotNull(fileExtensionList, "fileExtensionList is null");
    final String[] fileExtensions = fileExtensionList.toArray(new String[0]);
    return listFiles(path, fileExtensions);
  }

  /**
   * list files
   *
   * @param path
   * @param fileExtensions
   * @return
   */
  public static File[] listFiles(final File path, final String[] fileExtensions) {
    Preconditions.checkNotNull(path, "path is null");
    Preconditions.checkNotNull(fileExtensions, "fileExtensions is null");

    return path.listFiles(pathname -> {
      String path1 = pathname.getName();
      for (String extension : fileExtensions) {
        if (path1.lastIndexOf(extension) != -1) {
          return true;
        }
      }
      return false;
    });
  }


  /**
   * get file attr
   * <p>
   * lastModifiedTime, lastAccessTime, creationTime
   * </p>
   *
   * @param file
   * @return
   */
  public static Optional<BasicFileAttributes> getAttr(final File file) {
    Path path = file.toPath();
    BasicFileAttributes fileAttr;
    try {
      fileAttr = java.nio.file.Files.readAttributes(path, BasicFileAttributes.class);
    } catch (IOException e) {
      log.error("io exception.", e);
      fileAttr = null;
    }
    return Optional.ofNullable(fileAttr);
  }


  /**
   * 按行分割文件
   *
   * @param sourceFilePath      为源文件路径
   * @param targetDirectoryPath 文件分割后存放的目标目录
   * @param rows                为多少行一个文件
   */
  public static int splitByLine(String sourceFilePath, String targetDirectoryPath, int rows) {
    String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1, sourceFilePath.lastIndexOf("."));//源文件名
    String splitFileName = targetDirectoryPath + File.separator + sourceFileName + "-%s.txt";//切割后的文件名
    File targetDirectory = new File(targetDirectoryPath);
    if (!targetDirectory.exists()) {
      targetDirectory.mkdirs();
    }

    PrintWriter pw = null;//字符输出流
    String tempLine;
    int lineNum = 0;//本次行数累计 , 达到rows开辟新文件
    int splitFileIndex = 1;//当前文件索引

    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)))) {
      pw = new PrintWriter(String.format(splitFileName, splitFileIndex));
      while ((tempLine = br.readLine()) != null) {
        if (lineNum > 0 && lineNum % rows == 0) {//需要换新文件
          pw.flush();
          pw.close();
          pw = new PrintWriter(String.format(splitFileName, ++splitFileIndex));
        }
        pw.write(tempLine + "\n");
        lineNum++;
      }
      return splitFileIndex;
    } catch (Exception e) {
      log.error("fail to split file by line", e);
      return -1;
    } finally {
      if (null != pw) {
        pw.flush();
        pw.close();
      }
    }
  }

  /**
   * split file by file count.
   *
   * @param sourceFile     source file
   * @param targetDir      target dir
   * @param targetFileName target file name.
   * @param fileCount      size per file
   * @param overwrite      overwrite exist file.
   * @return
   */
  public static List<File> splitByFileCount(File sourceFile, File targetDir, String targetFileName, int fileCount, boolean overwrite) {
    if (sourceFile == null) {
      throw new IllegalArgumentException("source file is null.");
    }
    if (!sourceFile.exists()) {
      throw new IllegalArgumentException("source file is not exists");
    }
    if (!sourceFile.isFile()) {
      throw new IllegalArgumentException("source file is not file.");
    }

    long maxFileSize = sourceFile.length() / fileCount;
    boolean allMatch = sourceFile.length() % fileCount == 0;

    List<File> files = new ArrayList<>();

    targetDir = targetDir == null ? sourceFile.getParentFile() : targetDir;
    if (targetDir.exists()) {
      if (!targetDir.isDirectory()) {
        throw new IllegalArgumentException("target dir is not a directory, plz check.");
      }
    } else {
      targetDir.mkdirs();
    }

    targetFileName = StringUtil.isEmpty(targetFileName) ? sourceFile.getName() : targetFileName;

    long hasTotalWriteFileSize = 0;
    int count = 1, data;

    //RandomAccessFile infile = new RandomAccessFile(filename, "r");
    try (InputStream in = new BufferedInputStream(new FileInputStream(sourceFile))) {
      data = in.read();
      while (data != -1) {
        int leng = 0;
        File partFile = new File(targetDir.getAbsolutePath(), targetFileName + ".part" + count);
        files.add(partFile);
        log.info("write split file={}", partFile.getAbsolutePath());

        //RandomAccessFile outfile = new RandomAccessFile(filename, "rw");
        OutputStream outfile = new BufferedOutputStream(new FileOutputStream(partFile));
        while (data != -1 && leng < maxFileSize) {
          outfile.write(data);
          leng++;
          data = in.read();
        }

        if ((!allMatch) && fileCount == count) {
          while (data != -1) {
            outfile.write(data);
            data = in.read();
          }
        }

        hasTotalWriteFileSize += leng;
        outfile.close();
        count++;
      }
    } catch (Exception e) {
      log.error("io exception", e);
    }

    return files;
  }

  /**
   * split file by file size
   *
   * @param sourceFile     source file
   * @param targetDir      target dir
   * @param targetFileName target file name
   * @param eachFileSize   size per file
   * @param overwrite      overwrite exist file.
   * @return
   */
  public static List<File> splitByFileSize(File sourceFile, File targetDir, String targetFileName, final long eachFileSize, boolean overwrite) {
    if (sourceFile == null) {
      throw new IllegalArgumentException("source file is null.");
    }
    if (!sourceFile.exists()) {
      throw new IllegalArgumentException("source file is not exists");
    }
    if (!sourceFile.isFile()) {
      throw new IllegalArgumentException("source file is not file.");
    }

    List<File> files = new ArrayList<>();

    targetDir = targetDir == null ? sourceFile.getParentFile() : targetDir;
    if (targetDir.exists()) {
      if (!targetDir.isDirectory()) {
        throw new IllegalArgumentException("target dir is not a directory, plz check.");
      }
    } else {
      targetDir.mkdirs();
    }

    targetFileName = StringUtil.isEmpty(targetFileName) ? sourceFile.getName() : targetFileName;

    long hasTotalWriteFileSize = 0;
    int count = 1, data;

    //RandomAccessFile infile = new RandomAccessFile(filename, "r");
    try (InputStream in = new BufferedInputStream(new FileInputStream(sourceFile))) {
      data = in.read();
      while (data != -1) {
        int leng = 0;
        File partFile = new File(targetDir.getAbsolutePath(), targetFileName + ".part" + count);
        files.add(partFile);
        log.info("write split file={}", partFile.getAbsolutePath());

        //RandomAccessFile outfile = new RandomAccessFile(filename, "rw");
        OutputStream outfile = new BufferedOutputStream(new FileOutputStream(partFile));
        while (data != -1 && leng < eachFileSize) {
          outfile.write(data);
          leng++;
          data = in.read();
        }
        hasTotalWriteFileSize += leng;
        outfile.close();
        count++;
      }
    } catch (Exception e) {
      log.error("io exception", e);
    }

    return files;
  }


  /**
   * merge files to one file.
   * 重点: sourceFiles必须是有序的
   *
   * @param sourceFiles multi source file （must be ordered）
   * @param targetFile  target file
   * @param overwrite   overwrite file if target file exist.
   * @return
   */
  public static boolean merge(List<File> sourceFiles, File targetFile, boolean overwrite) throws IOException {
    if (ListUtil.isEmpty(sourceFiles)) {
      throw new IllegalArgumentException("source files is empty.");
    }
    Preconditions.checkNotNull(targetFile, "targetFile is null.");

    if (targetFile.exists() && overwrite) {
      targetFile.delete();
    }

    String tmpFilePath = targetFile.getAbsolutePath() + ".merge";
    File file = new File(tmpFilePath);
    FileChannel outChannel = new FileOutputStream(file).getChannel();

    sourceFiles.forEach(sourceFile -> {
      if (sourceFile == null) {
        log.warn("current source file item is null.");
        return;
      }
      try (FileChannel tmpIn = new FileInputStream(sourceFile).getChannel()) {
        //合并每个临时文件
        outChannel.transferFrom(tmpIn, outChannel.size(), tmpIn.size());
      } catch (IOException e) {
        log.error("merge file exception.", e);
      }
    });
    outChannel.close();

    file.renameTo(targetFile);

    return true;
  }

  /**
   * 获取一个文件的行数
   *
   * @param filePath
   * @return
   */
  public static long getLineNumber(String filePath) {
    return getLineNumber(new File(filePath));
  }

  /**
   * 获取一个文件的行数
   *
   * @param file
   * @return
   */
  public static long getLineNumber(File file) {
    if (!file.exists()) {
      log.warn("file is not exist");
      return -1;
    }

    if (!file.isFile()) {
      log.warn("file is not a file, plz check.");
      return -1;
    }
    //         return Files.lines(Paths.get(filePath)).count(); //jdk8 原始方法较慢
    try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file))) {
      lineNumberReader.skip(file.length());
      int lineNumber = lineNumberReader.getLineNumber();
      return lineNumber + 1;//实际上是读取换行符数量 , 所以需要+1
    } catch (Exception e) {
      log.error("fail to get line number", e);
      return -1;
    }
  }


}
