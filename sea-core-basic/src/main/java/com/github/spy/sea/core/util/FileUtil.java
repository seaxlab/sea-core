package com.github.spy.sea.core.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * File util
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public final class FileUtil {

    private FileUtil() {
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
        return readFileToString(PathUtil.getPathFromClassPath(path));
    }


    /**
     * 获取文件输入流
     */
    public static InputStream readFileToInputStream(String path) {
        InputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            log.error("io exception", e);
        }
        return inputStream;
    }

    /**
     * 读取文件字节数组
     */
    public static byte[] readFileToBytes(String path) {
        InputStream inputStream = readFileToInputStream(path);
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
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String path) {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            return null;
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

    public static boolean writeFile(String path, String content) {
        boolean result = true;
        try {
            Files.write(content, new File(path), Charsets.UTF_8);
        } catch (Exception e) {
            result = false;
            log.info("write file error", e);
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
                writeFileFromInputStream(readFileToInputStream(src), dest);
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


}
