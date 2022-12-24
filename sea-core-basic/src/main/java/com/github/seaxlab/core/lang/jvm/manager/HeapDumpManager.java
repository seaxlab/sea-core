package com.github.seaxlab.core.lang.jvm.manager;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.util.ClassUtil;
import com.github.seaxlab.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.PlatformManagedObject;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/17/20
 * @since 1.0
 */
@Slf4j
public final class HeapDumpManager {

  private final Lock lock = new ReentrantLock();

  private int timeout = 30;
  private HeapDumper heapDumper;

  public HeapDumpManager() {

  }

  /**
   * result.value = dmp file absolute path.
   *
   * @param live
   * @return
   */
  public Result heapDump(Boolean live) {
    Result result = Result.fail();

    try {
      if (this.lock.tryLock(this.timeout, TimeUnit.SECONDS)) {
        try {
          result.value(dumpHeap((live != null) ? live : true));
          return result;
        } finally {
          this.lock.unlock();
        }
      }
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    } catch (IOException ex) {
      log.error("io exception", ex);
      result.setMsg("internal server error");
    } catch (HeapDumperUnavailableException ex) {
      log.error("unavailable exception", ex);
      result.setMsg("service unavailable");
    }
    result.setMsg("too many request.");
    return result;
  }


  /**
   * 输出到文件
   *
   * @param live
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  private String dumpHeap(boolean live) throws IOException, InterruptedException {
    if (this.heapDumper == null) {
      this.heapDumper = createHeapDumper();
    }
    File file = createTempFile(live);
    this.heapDumper.dumpHeap(file, live);
    log.info("sea heap dump file path={}", file.getAbsolutePath());

    return file.getAbsolutePath();
  }

  private File createTempFile(boolean live) throws IOException {
    String date = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm").format(LocalDateTime.now());
    File file = File.createTempFile("sea-heapdump" + date + (live ? "-live" : ""), ".hprof");
    file.delete();
    return file;
  }

  /**
   * Factory method used to create the {@link HeapDumper}.
   *
   * @return the heap dumper to use
   * @throws HeapDumperUnavailableException if the heap dumper cannot be created
   */
  protected HeapDumper createHeapDumper() throws HeapDumperUnavailableException {
    return new HotSpotDiagnosticMXBeanHeapDumper();
  }

  /**
   * Strategy interface used to dump the heap to a file.
   */
  @FunctionalInterface
  protected interface HeapDumper {

    /**
     * Dump the current heap to the specified file.
     *
     * @param file the file to dump the heap to
     * @param live if only <em>live</em> objects (i.e. objects that are reachable from
     *             others) should be dumped
     * @throws IOException          on IO error
     * @throws InterruptedException on thread interruption
     */
    void dumpHeap(File file, boolean live) throws IOException, InterruptedException;

  }

  /**
   * {@link HeapDumper} that uses {@code com.sun.management.HotSpotDiagnosticMXBean}
   * available on Oracle and OpenJDK to dump the heap to a file.
   */
  protected static class HotSpotDiagnosticMXBeanHeapDumper implements HeapDumper {

    private Object diagnosticMXBean;

    private Method dumpHeapMethod;

    @SuppressWarnings("unchecked")
    protected HotSpotDiagnosticMXBeanHeapDumper() {
      try {
        Class<?> diagnosticMXBeanClass = ClassUtil.load("com.sun.management.HotSpotDiagnosticMXBean", false);
        this.diagnosticMXBean = ManagementFactory
          .getPlatformMXBean((Class<PlatformManagedObject>) diagnosticMXBeanClass);
        this.dumpHeapMethod = ReflectUtil.findMethod(diagnosticMXBeanClass, "dumpHeap", String.class,
          Boolean.TYPE);
      } catch (Throwable ex) {
        throw new HeapDumperUnavailableException("Unable to locate HotSpotDiagnosticMXBean", ex);
      }
    }

    @Override
    public void dumpHeap(File file, boolean live) {
      ReflectUtil.invokeMethod(this.diagnosticMXBean, this.dumpHeapMethod, file.getAbsolutePath(), live);
    }

  }

  /**
   * Exception to be thrown if the {@link HeapDumper} cannot be created.
   */
  protected static class HeapDumperUnavailableException extends RuntimeException {

    public HeapDumperUnavailableException(String message, Throwable cause) {
      super(message, cause);
    }

  }


}
