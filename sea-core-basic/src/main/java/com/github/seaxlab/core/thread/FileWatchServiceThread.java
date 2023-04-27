package com.github.seaxlab.core.thread;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * file watcher.
 */
@Slf4j
public class FileWatchServiceThread extends ServiceThread {

  private final List<String> watchFiles;
  private final List<String> fileCurrentHash;
  private final Listener listener;
  private static final int WATCH_INTERVAL = 500;
  private final MessageDigest md = MessageDigest.getInstance("MD5");

  public FileWatchServiceThread(final String[] watchFiles, final Listener listener) throws Exception {
    this.listener = listener;
    this.watchFiles = new ArrayList<>();
    this.fileCurrentHash = new ArrayList<>();

    for (String watchFile : watchFiles) {
      if (StringUtils.isNotEmpty(watchFile) && new File(watchFile).exists()) {
        this.watchFiles.add(watchFile);
        this.fileCurrentHash.add(hash(watchFile));
      }
    }
  }

  @Override
  public String getServiceName() {
    return "FileWatchService";
  }

  @Override
  public void run() {
    log.info(this.getServiceName() + " service started");

    while (!this.isStopped()) {
      try {
        this.waitForRunning(WATCH_INTERVAL);

        for (int i = 0; i < watchFiles.size(); i++) {
          String newHash;
          try {
            newHash = hash(watchFiles.get(i));
          } catch (Exception e) {
            log.warn(this.getServiceName() + " service has exception when calculate the file hash. ", e);
            continue;
          }
          if (!newHash.equals(fileCurrentHash.get(i))) {
            fileCurrentHash.set(i, newHash);
            listener.onChanged(watchFiles.get(i));
          }
        }
      } catch (Exception e) {
        log.warn(this.getServiceName() + " service has exception. ", e);
      }
    }
    log.info(this.getServiceName() + " service end");
  }

  private String hash(String filePath) throws IOException, NoSuchAlgorithmException {
    Path path = Paths.get(filePath);
    md.update(Files.readAllBytes(path));
    byte[] hash = md.digest();
    return Hex.encodeHexString(hash);
//        return UtilAll.bytes2string(hash);
  }

  public interface Listener {

    /**
     * Will be called when the target files are changed
     *
     * @param path the changed file path
     */
    void onChanged(String path);
  }
}
