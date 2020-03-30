package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.MyJob;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/30
 * @since 1.0
 */
@Slf4j
public class QuartzUtilTest extends BaseCoreTest {


    public static String JOB_NAME = "动态任务调度";
    public static String TRIGGER_NAME = "动态任务触发器";
    public static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";
    public static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP";

    @Test
    public void run30() throws Exception {
        System.out.println("【系统启动】开始(每1秒输出一次)...");
        QuartzUtil.addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, MyJob.class, "0/1 * * * * ?");

        Thread.sleep(5000);
        System.out.println("【修改时间】开始(每3秒输出一次)...");
        QuartzUtil.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/3 * * * * ?");

        Thread.sleep(10 * 1000);
        System.out.println("【移除定时】开始...");
        QuartzUtil.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
        System.out.println("【移除定时】成功");

        Thread.sleep(10 * 1000);
    }

}
