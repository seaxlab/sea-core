package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.message.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/31
 * @since 1.0
 */
@Slf4j
public class FileUtilTest extends BaseCoreTest {

    @Test
    public void testGetName() throws Exception {
        String filePath = "/path/a.txt";
        log.info("name={}", FileUtil.getName(filePath));

        filePath = "c:\\dir\\b.txt";
        log.info("name={}", FileUtil.getName(filePath));
    }

    @Test
    public void testReadFromClasspath() throws Exception {
        String txt = FileUtil.readFormClasspath("util/users.json");

        log.info("txt={}", txt);
    }

    @Test
    public void test36() throws Exception {
        InputStream inputStream = FileUtil.toInputStreamByUrl("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");

        log.info("input stream={}", inputStream);
    }

    @Test
    public void listFileTest() throws Exception {
        File[] files = FileUtil.listFiles(new File(getUserHome() + "/logs"), new String[]{".log"});
        for (int i = 0; i < files.length; i++) {
            log.info("files={}", files[i]);
        }
    }

    String path = System.getProperty("user.home") + "/sea/pandora-lite/sample-1.0.0-SNAPSHOT.jar";
    String filePath = "file:" + path;
    String jarFile = "jar:" + filePath;
    String libFile = jarFile + "!/lib/slf4j-api-1.7.30.jar";

    @Test
    public void listFileFailTest2() throws Exception {
        String jarFile = "file:" + getUserHome() + "/sea/pandora-lite/sample-1.0.0-SNAPSHOT.jar";
        File file = new File(jarFile + "!/lib/slf4j-api-1.7.30.jar");

        log.info("file={}", file.exists());
    }

    @Test
    public void listFileTest2() throws Exception {
        File file = new File(path);
        JarFile jarFile = new JarFile(file);

        Set<URL> urls = jarFile.stream()
                               .filter(jarEntry -> jarEntry.getName().endsWith(".jar"))
                               .map(jarEntry -> {
                                   String libPath = MessageUtil.format("{}!/{}", path, jarEntry.getName());
                                   log.info("jar={}, size={}", jarEntry.getName(), ByteUnitUtil.format(jarEntry.getSize()));

                                   URL url = null;
                                   try {
                                       // 获取jar中的jar
                                       url = new URL("jar", null, "file:" + libPath);
                                       JarURLConnection conn = (JarURLConnection) url.openConnection();
                                       JarFile libJarFile = conn.getJarFile();
                                       log.info("lib jar file={}", libJarFile.getName());
//
//
//                                       File f1 = new File(url.getFile());
//                                       JarFile jf = new JarFile(f1);
//                                       log.info("{} size={}", jf.getName(), jf.size());
                                   } catch (Exception e) {
                                       log.error("io exception.", e);
                                   }
                                   return url;
                               }).collect(Collectors.toSet());


        log.info("urls={}", urls);
//        JarFile libJarFile = new JarFile();

    }


    @Test
    public void test44() throws Exception {
//        /var/folders/zf/h34bgq7n195gly0fxb3p3j180000gn/T/
//        /var/folders/zf/h34bgq7n195gly0fxb3p3j180000gn/T/
        log.info("java.temp.dir={}", System.getProperty("java.io.tmpdir"));
    }


    @Test
    public void sizeOfTest() throws Exception {

        String logDir = getUserHome() + "/logs";
        long size = FileUtil.sizeOfDir(logDir);
        log.info("size={},human size={}", size, ByteUnitUtil.format(size));
    }


    @Test
    public void testSplitByLine() throws Exception {
        String filePath = getUserHome() + "/sea/split-file-line.txt";
        String targetDir = getUserHome() + "/sea/";
        FileUtil.deleteFiles(filePath);
        File file = FileUtil.createNewFile(filePath);
        List<String> lineList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            lineList.add("" + i);
        }

        IOUtil.writeLines(file, ArrayUtil.toArray(lineList, String.class));

        FileUtil.splitByLine(filePath, targetDir, 30);
    }

    @Test
    public void splitByFileSizeTest() throws Exception {
        File file = new File(getUserHome() + "/sea/download-bigfile.zip");
        File targetFile = new File(getUserHome() + "/sea/split-by-file-size");
        FileUtil.splitByFileSize(file, targetFile, "abc.zip", 10 * CoreConst.ONE_MB, true);
    }

    @Test
    public void splitByFileCountTest() throws Exception {
        File file = new File(getUserHome() + "/sea/download-bigfile.zip");
        File targetFile = new File(getUserHome() + "/sea/split-by-file-count");
        FileUtil.splitByFileCount(file, targetFile, "abc.zip", 2, true);
    }

    @Test
    public void mergeFileTest() throws Exception {
        List<File> files = new ArrayList<>();
        File file1 = new File(getUserHome() + "/sea/split-by-file-count/abc.zip.part1");
        File file2 = new File(getUserHome() + "/sea/split-by-file-count/abc.zip.part2");
        files.add(file1);
        files.add(file2);

        FileUtil.merge(files, new File(getUserHome(), "/sea/merge-file.zip"), true);

    }


    @Test
    public void testGetLineNumber() throws Exception {
        String filePath = getUserHome() + "/sea/split-file-line.txt";
        log.info("file line number={}", FileUtil.getLineNumber(filePath));
    }
}
