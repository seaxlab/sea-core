package com.github.seaxlab.core.test.service.impl;

import com.github.seaxlab.core.test.lock.FileLock;
import com.github.seaxlab.core.test.service.SequenceService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/16
 * @since 1.0
 */
@Slf4j
public class FileSequenceService implements SequenceService {

  @Override
  public long next(String namespace) {
    String lockKey = "seq_" + namespace;

    FileLock lock = new FileLock(lockKey);
    try {
      lock.lock();

      String userHome = System.getProperty("user.home");
      String path = userHome + "/sea/sequence";
      File file = new File(path);
      if (!file.exists()) {
        file.mkdirs();
      }
      long nextValue;
      File seqFile = new File(path + "/" + namespace);
      if (!seqFile.exists()) {
        seqFile.createNewFile();
        nextValue = 1;
      } else {
        //++

        String content = Files.asCharSource(seqFile, Charsets.UTF_8).read();

        try {
          nextValue = Long.valueOf(content) + 1;
        } catch (Exception e) {
          log.error("read file fail, reset to zero.");
          nextValue = 0;
        }

      }
      //write to file
      Files.write("" + nextValue, seqFile, Charsets.UTF_8);
      return nextValue;
    } catch (Exception e) {
      log.error("exception.", e);
    } finally {
      lock.unlock();
    }


    return 0;
  }
}
