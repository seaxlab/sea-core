package com.github.seaxlab.core.lang.logging;

import com.github.seaxlab.core.lang.logging.inner.*;

public class ClientLogger {

    private static final String MODULE = "sea-test";

    public static final String CLIENT_LOG_USE_SLF4J = MODULE + ".client.logUseSlf4j";
    public static final String CLIENT_LOG_ROOT = MODULE + ".client.logRoot";
    public static final String CLIENT_LOG_MAX_INDEX = MODULE + ".client.logFileMaxIndex";
    public static final String CLIENT_LOG_FILE_SIZE = MODULE + ".client.logFileMaxSize";
    public static final String CLIENT_LOG_LEVEL = MODULE + ".client.logLevel";
    public static final String CLIENT_LOG_ADDITIVE = MODULE + ".client.log.additive";
    public static final String CLIENT_LOG_FILENAME = MODULE + ".client.logFileName";
    public static final String CLIENT_LOG_ASYNC_QUEUE_SIZE = MODULE + ".client.logAsyncQueueSize";
    // appender
    public static final String CLIENT_APPENDER_NAME = MODULE + "ClientAppender";

    private static final InternalLogger CLIENT_LOGGER;

    private static final boolean CLIENT_USE_SLF4J;


    static {
        CLIENT_USE_SLF4J = Boolean.parseBoolean(System.getProperty(CLIENT_LOG_USE_SLF4J, "false"));
        if (!CLIENT_USE_SLF4J) {
            InternalLoggerFactory.setCurrentLoggerType(InnerLoggerFactory.LOGGER_INNER);
            CLIENT_LOGGER = createLogger(MODULE + "-client");
            // you can create multi logger.
//            createLogger("mqCommon");
//            createLogger("mqRemoting");
        } else {
            CLIENT_LOGGER = InternalLoggerFactory.getLogger(MODULE + "-client");
        }
    }

    /**
     * open api for log
     *
     * @return Internal Logger
     */
    public static InternalLogger getLog() {
        return CLIENT_LOGGER;
    }

    /**
     * create client appender
     *
     * @return
     */
    private static synchronized Appender createClientAppender() {
        String clientLogRoot = System.getProperty(CLIENT_LOG_ROOT, System.getProperty("user.home") + "/logs/sea/core");
        String clientLogMaxIndex = System.getProperty(CLIENT_LOG_MAX_INDEX, "10");
        String clientLogFileName = System.getProperty(CLIENT_LOG_FILENAME, "sea_core.log");
        String maxFileSize = System.getProperty(CLIENT_LOG_FILE_SIZE, "1073741824");
        String asyncQueueSize = System.getProperty(CLIENT_LOG_ASYNC_QUEUE_SIZE, "1024");

        String logFileName = clientLogRoot + "/" + clientLogFileName;

        int maxFileIndex = Integer.parseInt(clientLogMaxIndex);
        int queueSize = Integer.parseInt(asyncQueueSize);

        Layout layout = LoggingBuilder.newLayoutBuilder()
                                      .withDefaultLayout()
                                      .build();

        Appender clientAppender = LoggingBuilder.newAppenderBuilder()
                                                .withDailyFileRollingAppender(logFileName, "'.'yyyyMMdd")
//                                              .withRollingFileAppender(logFileName, maxFileSize, maxFileIndex)
                                                .withAsync(false, queueSize)
                                                .withName(CLIENT_APPENDER_NAME)
                                                .withLayout(layout)
                                                .build();

        Logger.getRootLogger().addAppender(clientAppender);
        return clientAppender;
    }

    /**
     * create logger
     *
     * @param loggerName
     * @return
     */
    private static InternalLogger createLogger(final String loggerName) {
        String clientLogLevel = System.getProperty(CLIENT_LOG_LEVEL, "INFO");
        boolean additive = "true".equalsIgnoreCase(System.getProperty(CLIENT_LOG_ADDITIVE));

        InternalLogger logger = InternalLoggerFactory.getLogger(loggerName);
        InnerLoggerFactory.InnerLogger innerLogger = (InnerLoggerFactory.InnerLogger) logger;
        Logger realLogger = innerLogger.getLogger();

        realLogger.addAppender(new AppenderProxy());
        realLogger.setLevel(Level.toLevel(clientLogLevel));
        realLogger.setAdditivity(additive);
        return logger;
    }

    static class AppenderProxy extends Appender {
        private Appender proxy;

        @Override
        protected void append(LoggingEvent event) {
            if (null == proxy) {
                proxy = ClientLogger.createClientAppender();
            }
            proxy.doAppend(event);
        }

        @Override
        public void close() {
            if (null != proxy) {
                proxy.close();
            }
        }
    }
}
