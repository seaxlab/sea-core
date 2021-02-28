package com.github.spy.sea.core.component.queue.persist;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * MFQueuePool
 *
 * @author derek.wu
 * @date 2017-12-24
 * @since v1.0.0
 */
public class MFQueuePool {

    private static final Logger LOGGER = LoggerFactory.getLogger(MFQueuePool.class);
    private static final BlockingQueue<String> DELETING_QUEUE = new LinkedBlockingQueue<>();
    private static MFQueuePool instance = null;
    private String fileBackupPath;
    private Map<String, MFQueue> fQueueMap;
    private ScheduledExecutorService syncService;

    private MFQueuePool(String fileBackupPath) {
        this.fileBackupPath = fileBackupPath;
        File fileBackupDir = new File(fileBackupPath);
        if (!fileBackupDir.exists() && !fileBackupDir.mkdir()) {
            throw new IllegalArgumentException("can not create directory");
        }
        this.fQueueMap = scanDir(fileBackupDir);
        this.syncService = Executors.newSingleThreadScheduledExecutor();
        this.syncService.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                for (MFQueue MFQueue : fQueueMap.values()) {
                    MFQueue.sync();
                }
                deleteBlockFile();
            }
        }, 10L, 10L, TimeUnit.SECONDS);
    }

    public static void toClear(String filePath) {
        DELETING_QUEUE.add(filePath);
    }

    /**
     * 初始化
     * 1.初始化目录
     *
     * @param deployPath
     */
    public synchronized static void init(String deployPath) {
        if (instance == null) {
            instance = new MFQueuePool(deployPath);
        }
    }

    /**
     * 销毁资源
     */
    public synchronized static void destory() {
        if (instance != null) {
            instance.disposal();
            instance = null;
        }
    }

    /**
     * 获取队列
     *
     * @param queueName
     * @return
     */
    public synchronized static MFQueue getFQueue(String queueName) {
        if (StringUtils.isBlank(queueName)) {
            throw new IllegalArgumentException("empty queue name");
        }
        return instance.getQueueFromPool(queueName);
    }

    private void deleteBlockFile() {
        String blockFilePath = DELETING_QUEUE.poll();
        if (StringUtils.isNotBlank(blockFilePath)) {
            File delFile = new File(blockFilePath);
            try {
                if (!delFile.delete()) {
                    LOGGER.warn("block file:{} delete failed", blockFilePath);
                }
            } catch (SecurityException e) {
                LOGGER.error("security manager exists, delete denied");
            }
        }
    }

    private Map<String, MFQueue> scanDir(File fileBackupDir) {
        if (!fileBackupDir.isDirectory()) {
            throw new IllegalArgumentException("it is not a directory");
        }
        Map<String, MFQueue> exitsFQueues = new HashMap<>();
        File[] indexFiles = fileBackupDir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return MFQueueIndex.isIndexFile(name);
            }
        });
        if (ArrayUtils.isNotEmpty(indexFiles)) {
            for (File indexFile : indexFiles) {
                String queueName = MFQueueIndex.parseQueueName(indexFile.getName());
                exitsFQueues.put(queueName, new MFQueue(queueName, fileBackupPath));
            }
        }
        return exitsFQueues;
    }

    private void disposal() {
        this.syncService.shutdown();
        for (MFQueue MFQueue : fQueueMap.values()) {
            MFQueue.close();
        }
        while (!DELETING_QUEUE.isEmpty()) {
            deleteBlockFile();
        }
    }

    private MFQueue getQueueFromPool(String queueName) {
        if (fQueueMap.containsKey(queueName)) {
            return fQueueMap.get(queueName);
        }
        MFQueue MFQueue = new MFQueue(queueName, fileBackupPath);
        fQueueMap.put(queueName, MFQueue);
        return MFQueue;
    }

}
