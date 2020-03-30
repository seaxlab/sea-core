package com.github.spy.sea.core.util;

import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/30
 * @since 1.0
 */
@Slf4j
public class QuartzUtil {

    private QuartzUtil() {

    }

    private static volatile boolean init = false;
    /**
     * default job group name if it is missing.
     */
    public static final String DEFAULT_JOB_GROUP_NAME = "default_job_group";
    /**
     * default trigger group name if it is missing.
     */
    public static final String DEFAULT_TRIGGER_GROUP_NAME = "default_trigger_group";

    /**
     * global scheduler factory
     */
//    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * global scheduler
     */
    private static Scheduler scheduler;

    /**
     * add job
     *
     * @param jobName
     * @param triggerName
     * @param jobClass
     * @param cron
     */
    public static BaseResult addJob(String jobName, String triggerName, Class jobClass, String cron) {
        return addJob(jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, jobClass, cron);
    }

    /**
     * 添加任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param cron             时间设置，参考quartz说明文档
     * @return BaseResult
     */
    public static BaseResult addJob(String jobName, String jobGroupName,
                                    String triggerName, String triggerGroupName, Class jobClass, String cron) {
        BaseResult result = BaseResult.fail();

        try {
            Scheduler scheduler = getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("quartz add job error ", e);
            result.setErrorMessage("添加任务失败");
        }

        return result;
    }

    /**
     * modify job time
     *
     * @param jobName
     * @param triggerName
     * @param cron
     */
    public static BaseResult modifyJobTime(String jobName, String triggerName, String cron) {
        return modifyJobTime(jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, cron);
    }

    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     * @Description: 修改一个任务的触发时间
     */
    public static BaseResult modifyJobTime(String jobName, String jobGroupName,
                                           String triggerName, String triggerGroupName, String cron) {

        BaseResult result = BaseResult.fail();
        try {
            Scheduler scheduler = getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                result.setErrorMessage("job不存在");
                return result;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            } else {
                log.info("cron expression are the same.");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("fail to modify Job Time error ", e);
            result.setErrorMessage("修改定时任务时间异常");
        }

        return result;
    }

    /**
     * remove one job for default.
     *
     * @param jobName
     * @param triggerName
     * @return
     */
    public static BaseResult removeJob(String jobName, String triggerName) {
        return removeJob(jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME);
    }

    /**
     * remove one job
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static BaseResult removeJob(String jobName, String jobGroupName,
                                       String triggerName, String triggerGroupName) {

        BaseResult result = BaseResult.fail();

        try {
            Scheduler scheduler = getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("fail to remove job", e);
            result.setErrorMessage("fail to remove job");
        }

        return result;
    }

    /**
     * 启动所有定时任务
     */
    public static void start() {
        try {
            Scheduler scheduler = getScheduler();
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (Exception e) {
            log.error("[quartz] fail to start", e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdown() {
        try {
            Scheduler scheduler = getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            log.error("[quartz] fail to shutdown", e);
        }
    }

    private static Scheduler getScheduler() throws SchedulerException {
        return createScheduler();
    }

    private static Scheduler createScheduler() {
        if (init) {
            return scheduler;
        }
        Scheduler result = null;
        try {
            StdSchedulerFactory factory = new StdSchedulerFactory();
            factory.initialize(getBaseQuartzProperties());
            result = factory.getScheduler();
//            result.getListenerManager().addTriggerListener(schedulerFacade.newJobTriggerListener());
        } catch (final SchedulerException e) {
            log.error("fail to create scheduler", e);
        }
        return result;
    }

    private static Properties getBaseQuartzProperties() {
        Properties result = new Properties();
        result.put("org.quartz.threadPool.class", org.quartz.simpl.SimpleThreadPool.class.getName());
        result.put("org.quartz.threadPool.threadCount", "5");
        result.put("org.quartz.scheduler.instanceName", "sea-core-quartz-scheduler");
        result.put("org.quartz.jobStore.misfireThreshold", "1");
//        result.put("org.quartz.plugin.shutdownhook.class", JobShutdownHookPlugin.class.getName());
//        result.put("org.quartz.plugin.shutdownhook.cleanShutdown", Boolean.TRUE.toString());
        return result;
    }


}
