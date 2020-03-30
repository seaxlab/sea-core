package com.github.spy.sea.core.util;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.model.SysJobVO;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
     * add job
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
            return addJob(scheduler, jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
        } catch (Exception e) {
            log.error("quartz add job error ", e);
            result.setErrorMessage("添加任务失败");
        }

        return result;
    }

    /**
     * add job
     *
     * @param scheduler
     * @param jobName
     * @param triggerName
     * @param jobClass
     * @param cron
     * @return
     */
    public static BaseResult addJob(Scheduler scheduler, String jobName, String triggerName, Class jobClass, String cron) {
        return addJob(scheduler, jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, jobClass, cron);
    }

    /**
     * add job
     *
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param jobClass
     * @param cron
     * @return
     */
    public static BaseResult addJob(Scheduler scheduler, String jobName, String jobGroupName,
                                    String triggerName, String triggerGroupName, Class jobClass, String cron) {
        Preconditions.checkNotNull(scheduler, "scheduler cannot be null");

        BaseResult result = BaseResult.fail();

        try {
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
     * modify job time
     *
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
            return modifyJobTime(getScheduler(), jobName, jobGroupName, triggerName, triggerGroupName, cron);
        } catch (Exception e) {
            log.error("fail to modify Job Time error ", e);
            result.setErrorMessage("修改定时任务时间异常");
        }

        return result;
    }

    /**
     * modify job time
     *
     * @param scheduler
     * @param jobName
     * @param triggerName
     * @param cron
     * @return
     */
    public static BaseResult modifyJobTime(Scheduler scheduler, String jobName, String triggerName, String cron) {
        return modifyJobTime(scheduler, jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, cron);
    }

    /**
     * modify job time
     *
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @return
     */
    public static BaseResult modifyJobTime(Scheduler scheduler, String jobName, String jobGroupName,
                                           String triggerName, String triggerGroupName, String cron) {
        Preconditions.checkNotNull(scheduler, "scheduler cannot be null");

        BaseResult result = BaseResult.fail();
        try {
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
            return removeJob(getScheduler(), jobName, jobGroupName, triggerName, triggerGroupName);
        } catch (Exception e) {
            log.error("fail to remove job", e);
            result.setErrorMessage("fail to remove job");
        }

        return result;
    }

    /**
     * remove job
     *
     * @param scheduler
     * @param jobName
     * @param triggerName
     * @return
     */
    public static BaseResult removeJob(Scheduler scheduler, String jobName, String triggerName) {
        return removeJob(scheduler, jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME);
    }

    /**
     * remove job
     *
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @return
     */
    public static BaseResult removeJob(Scheduler scheduler,
                                       String jobName, String jobGroupName,
                                       String triggerName, String triggerGroupName) {
        Preconditions.checkNotNull(scheduler, "scheduler cannot be null");
        BaseResult result = BaseResult.fail();

        try {
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
     * trigger job for default
     *
     * @param jobName
     * @return
     */
    public static BaseResult trigger(String jobName) {
        return trigger(getScheduler(), jobName, DEFAULT_JOB_GROUP_NAME);
    }

    /**
     * trigger job for default
     *
     * @param jobName
     * @param jobGroupName
     * @return
     */
    public static BaseResult trigger(String jobName, String jobGroupName) {
        return trigger(getScheduler(), jobName, jobGroupName);
    }

    /**
     * trigger job
     *
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     * @return
     */
    public static BaseResult trigger(Scheduler scheduler, String jobName, String jobGroupName) {
        Preconditions.checkNotNull(scheduler, "scheduler cannot be null");
        BaseResult result = BaseResult.fail();

        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

        try {
            scheduler.triggerJob(jobKey);
            result.setSuccess(true);
        } catch (SchedulerException e) {
            log.error("trigger job exception", e);
            result.setErrorMessage("fail to trigger job");
        }
        return result;
    }

    /**
     * query all jobs for default.
     *
     * @return
     */
    public static BaseResult queryAllJobs() {
        return queryAllJobs(getScheduler());
    }

    /**
     * query all jobs for scheduler
     *
     * @param scheduler
     * @return
     */
    public static BaseResult queryAllJobs(Scheduler scheduler) {
        BaseResult result = BaseResult.fail();

        try {

            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            List<SysJobVO> jobList = new ArrayList<>();


            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    SysJobVO job = new SysJobVO();
                    job.setJobName(jobKey.getName());
                    job.setJobGroup(jobKey.getGroup());
                    job.setDesc("触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    jobList.add(job);
                }
            }

            result.setData(jobList);
            result.setSuccess(true);
        } catch (SchedulerException e) {
            log.error("get job detail exception", e);
            result.setErrorMessage("get job detail exception");
        }

        return result;
    }


    /**
     * start default scheduler
     */
    public static void start() {
        try {
            start(getScheduler());
        } catch (Exception e) {
            log.error("[quartz] fail to start", e);
        }
    }

    /**
     * start scheduler
     *
     * @param scheduler
     */
    public static void start(Scheduler scheduler) {
        if (scheduler == null) {
            log.warn("scheduler is null, plz check.");
        }
        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
        } catch (Exception e) {
            log.error("[quartz] fail to start", e);
        }
    }

    /**
     * shutdown default scheduler
     */
    public static void shutdown() {
        try {
            shutdown(getScheduler());
        } catch (Exception e) {
            log.error("[quartz] fail to shutdown", e);
        }
    }


    /**
     * shutdown scheduler
     *
     * @param scheduler
     */
    public static void shutdown(Scheduler scheduler) {
        if (scheduler == null) {
            log.warn("scheduler is null, plz check.");
        }
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            log.error("[quartz] fail to shutdown", e);
        }
    }


    private static Scheduler getScheduler() {
        return createScheduler();
    }

    private static Scheduler createScheduler() {
        if (init) {
            return scheduler;
        }
        try {
            StdSchedulerFactory factory = new StdSchedulerFactory();
            factory.initialize(getBaseQuartzProperties());
            scheduler = factory.getScheduler();
//            scheduler.getListenerManager().addTriggerListener(schedulerFacade.newJobTriggerListener());
            init = true;
        } catch (final SchedulerException e) {
            log.error("fail to create scheduler", e);
        }
        return scheduler;
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
