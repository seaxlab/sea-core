package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * JVM进程util
 *
 * @author spy
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public final class ProcessUtil {

  private List<Process> processes;


  public ProcessUtil() {
    processes = new ArrayList<>();
  }

  /**
   * 启动新的进程
   *
   * @param optionsAsString
   * @param mainClass
   * @param arguments
   * @return
   * @throws IOException
   */
  public Process startNewJavaProcess(final String optionsAsString, final String mainClass, final String[] arguments)
    throws IOException {

    ProcessBuilder processBuilder = createProcess(optionsAsString, mainClass, arguments);
    Process process = processBuilder.start();
    processes.add(process);
    log.info("Process [{}] has started", process.toString());
    return process;
  }


  private ProcessBuilder createProcess(final String optionsAsString, final String mainClass, final String[] arguments) {
    String jvm = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
    String classpath = System.getProperty("java.class.path");
    //log.debug("classpath: " + classpath);
    // String workingDirectory = System.getProperty("user.dir");

    String[] options = optionsAsString.split(" ");
    List<String> command = new ArrayList<>();
    command.add(jvm);
    command.addAll(Arrays.asList(options));
    command.add(mainClass);
    command.addAll(Arrays.asList(arguments));

    ProcessBuilder processBuilder = new ProcessBuilder(command);
    Map<String, String> environment = processBuilder.environment();
    environment.put("CLASSPATH", classpath);
    return processBuilder;
  }


  public void killProcess(final Process process) {
    process.destroy();
  }

  /**
   * Kill all processes.
   */
  public void shutdown() {
    //log.debug("Killing " + processes.size() + " processes.");
    if (processes != null && !processes.isEmpty()) {
      for (Process process : processes) {
        killProcess(process);
      }
    }
  }
}
