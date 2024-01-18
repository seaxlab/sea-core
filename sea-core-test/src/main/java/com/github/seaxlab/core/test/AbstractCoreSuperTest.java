package com.github.seaxlab.core.test;

import com.github.javafaker.Faker;
import com.github.seaxlab.core.test.service.SequenceService;
import com.github.seaxlab.core.test.service.impl.FileSequenceService;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/14
 * @since 1.0
 */
class AbstractCoreSuperTest {

  private Logger _log = LoggerFactory.getLogger(getClass());




}
