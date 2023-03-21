package com.github.seaxlab.core.util;

import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.exception.ExceptionHandler;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * quartz util
 *
 * @author spy
 * @version 1.0 2020/3/30
 * @since 1.0
 */
@Slf4j
public final class QuartzUtil {

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
  public static void addJob(String jobName, String triggerName, Class jobClass, String cron) {
    addJob(jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, jobClass, cron);
  }

  /**
   * add job
   *
   * @param jobName
   * @param triggerName
   * @param jobClass
   * @param cron
   * @param dataMap     额外参数
   * @return
   */
  public static void addJob(String jobName, String triggerName, Class jobClass, String cron,
    Map<String, Object> dataMap) {
    addJob(getScheduler(), jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, jobClass, cron,
      dataMap);
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
   * @return Result
   */
  public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
    Class jobClass, String cron) {
    Scheduler scheduler = getScheduler();
    addJob(scheduler, jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
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
  public static void addJob(Scheduler scheduler, String jobName, String triggerName, Class jobClass, String cron) {
    addJob(scheduler, jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, jobClass, cron);
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
  public static void addJob(Scheduler scheduler, String jobName, String jobGroupName, String triggerName,
    String triggerGroupName, Class jobClass, String cron) {
    addJob(scheduler, jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron, null);
  }

  /**
   * 添加job
   *
   * @param scheduler
   * @param jobName
   * @param jobGroupName
   * @param triggerName
   * @param triggerGroupName
   * @param jobClass
   * @param cron
   * @param dataMap          额外数据
   * @return
   */
  public static void addJob(Scheduler scheduler, String jobName, String jobGroupName, String triggerName,
    String triggerGroupName, Class jobClass, String cron, Map<String, Object> dataMap) {
    log.info("[add job] jobName={},jobGroupName={},triggerName={},triggerGroupName={}", jobName, jobGroupName,
      triggerName, triggerGroupName);

    Preconditions.checkNotNull(scheduler, "scheduler cannot be null");

    // 任务名，任务组，任务执行类
    JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    if (dataMap != null) {
      jobDetail.getJobDataMap().putAll(dataMap);
    }
    // 触发器
    TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    // 触发器名,触发器组
    triggerBuilder.withIdentity(triggerName, triggerGroupName);
    triggerBuilder.startNow();
    // 触发器时间设定
    triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    // 创建Trigger对象
    CronTrigger trigger = (CronTrigger) triggerBuilder.build();

    addJob(scheduler, jobDetail, trigger);
  }

  /**
   * 在默认调度器中，添加定时任务
   *
   * @param jobDetail job info
   * @param trigger   trigger
   * @return
   */
  public static void addJob(JobDetail jobDetail, Trigger trigger) {
    addJob(getScheduler(), jobDetail, trigger);
  }

  /**
   * 添加job
   *
   * @param scheduler 调度器
   * @param jobDetail job info
   * @param trigger   trigger
   * @return
   */
  public static void addJob(Scheduler scheduler, JobDetail jobDetail, Trigger trigger) {
    try {
      // 调度容器设置JobDetail和Trigger
      scheduler.scheduleJob(jobDetail, trigger);

      // 启动
      if (!scheduler.isShutdown()) {
        scheduler.start();
      }
    } catch (Exception e) {
      log.error("fail to add job.", e);
      ExceptionHandler.publish(ErrorMessageEnum.SYS_EXCEPTION);
    }
  }


  /**
   * modify job time
   *
   * @param jobName
   * @param triggerName
   * @param cron
   */
  public static void modifyJobTime(String jobName, String triggerName, String cron) {
    modifyJobTime(jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, cron);
  }

  /**
   * 修改一个任务的触发时间
   *
   * @param jobName
   * @param jobGroupName
   * @param triggerName      触发器名
   * @param triggerGroupName 触发器组名
   * @param cron             时间设置，参考quartz说明文档
   * @return result
   */
  public static void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
    String cron) {

    modifyJobTime(getScheduler(), jobName, jobGroupName, triggerName, triggerGroupName, cron);
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
  public static void modifyJobTime(Scheduler scheduler, String jobName, String triggerName, String cron) {
    modifyJobTime(scheduler, jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME, cron);
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
  public static void modifyJobTime(Scheduler scheduler, String jobName, String jobGroupName, String triggerName,
    String triggerGroupName, String cron) {
    Preconditions.checkNotNull(scheduler, "scheduler cannot be null");

    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
      CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
      if (trigger == null) {
        ExceptionHandler.publishMsg("触发器不存在");
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
    } catch (SchedulerException e) {
      log.error("fail to modify Job Time error ", e);
      ExceptionHandler.publishMsg("修改定时任务时间异常");
    }
  }

  /**
   * remove one job for default.
   *
   * @param jobName
   * @param triggerName
   * @return
   */
  public static void removeJob(String jobName, String triggerName) {
    removeJob(jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME);
  }

  /**
   * remove one job
   *
   * @param jobName
   * @param jobGroupName
   * @param triggerName
   * @param triggerGroupName
   */
  public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
    removeJob(getScheduler(), jobName, jobGroupName, triggerName, triggerGroupName);
  }

  /**
   * remove job
   *
   * @param scheduler
   * @param jobName
   * @param triggerName
   * @return
   */
  public static void removeJob(Scheduler scheduler, String jobName, String triggerName) {
    removeJob(scheduler, jobName, DEFAULT_JOB_GROUP_NAME, triggerName, DEFAULT_TRIGGER_GROUP_NAME);
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
  public static void removeJob(Scheduler scheduler, String jobName, String jobGroupName, String triggerName,
    String triggerGroupName) {
    Preconditions.checkNotNull(scheduler, "scheduler cannot be null");

    log.info("[remove job] jobName={},jobGroupName={},triggerName={},triggerGroupName={}", jobName, jobGroupName,
      triggerName, triggerGroupName);

    try {
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

      scheduler.pauseTrigger(triggerKey);// 停止触发器
      scheduler.unscheduleJob(triggerKey);// 移除触发器
      scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
    } catch (SchedulerException e) {
      log.error("fail to remove job", e);
      ExceptionHandler.publishMsg("移除任务失败");
    }

  }

  /**
   * trigger job for default
   *
   * @param jobName
   * @return
   */
  public static void trigger(String jobName) {
    trigger(getScheduler(), jobName, DEFAULT_JOB_GROUP_NAME);
  }

  /**
   * trigger job for default
   *
   * @param jobName
   * @param jobGroupName
   * @return
   */
  public static void trigger(String jobName, String jobGroupName) {
    trigger(getScheduler(), jobName, jobGroupName);
  }

  /**
   * trigger job
   *
   * @param scheduler
   * @param jobName
   * @param jobGroupName
   * @return
   */
  public static void trigger(Scheduler scheduler, String jobName, String jobGroupName) {
    Preconditions.checkNotNull(scheduler, "scheduler cannot be null");
    JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

    try {
      scheduler.triggerJob(jobKey);
    } catch (SchedulerException e) {
      log.error("trigger job exception", e);
      ExceptionHandler.publishMsg("触发失败");
    }
  }

  /**
   * query all jobs for default.
   *
   * @return
   */
  public static List<SysJob> queryAllJobs() {
    return queryAllJobs(getScheduler());
  }

  /**
   * query all jobs for scheduler
   *
   * @param scheduler
   * @return
   */
  public static List<SysJob> queryAllJobs(Scheduler scheduler) {

    try {

      GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
      Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
      List<SysJob> jobList = new ArrayList<>();

      for (JobKey jobKey : jobKeys) {
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        for (Trigger trigger : triggers) {
          SysJob job = new SysJob();
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

      return jobList;
    } catch (SchedulerException e) {
      log.error("get job detail exception", e);
      ExceptionHandler.publishMsg("获取job信息异常");
    }

    return ListUtil.empty();
  }


  /**
   * check exist job
   *
   * @param jobName
   * @return
   */
  public static boolean checkExistJob(String jobName) {
    return checkExistJob(getScheduler(), jobName, DEFAULT_JOB_GROUP_NAME);
  }

  /**
   * check exist job
   *
   * @param jobName
   * @param jobGroupName
   * @return
   */
  public static boolean checkExistJob(String jobName, String jobGroupName) {
    return checkExistJob(getScheduler(), jobName, jobGroupName);
  }

  /**
   * check exist job
   *
   * @param scheduler
   * @param jobName
   * @param jobGroupName
   * @return
   */
  public static boolean checkExistJob(Scheduler scheduler, String jobName, String jobGroupName) {

    try {
      JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
      return scheduler.checkExists(jobKey);
    } catch (Exception e) {
      log.error("fail to check exist job", e);
    }
    return false;
  }


  /**
   * check exist trigger
   *
   * @param triggerName
   * @return
   */
  public static boolean checkExistTrigger(String triggerName) {
    return checkExistTrigger(getScheduler(), triggerName, DEFAULT_TRIGGER_GROUP_NAME);
  }


  /**
   * check exist trigger
   *
   * @param triggerName
   * @param triggerGroupName
   * @return
   */
  public static boolean checkExistTrigger(String triggerName, String triggerGroupName) {
    return checkExistTrigger(getScheduler(), triggerName, triggerGroupName);
  }

  /**
   * check exist trigger
   *
   * @param scheduler
   * @param triggerName
   * @param triggerGroupName
   * @return
   */
  public static boolean checkExistTrigger(Scheduler scheduler, String triggerName, String triggerGroupName) {
    try {
      TriggerKey key = TriggerKey.triggerKey(triggerName, triggerGroupName);
      return scheduler.checkExists(key);
    } catch (Exception e) {
      log.error("fail to check exist job", e);
    }
    return false;
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
      return;
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
      return;
    }
    try {
      if (!scheduler.isShutdown()) {
        scheduler.shutdown();
      }
    } catch (Exception e) {
      log.error("[quartz] fail to shutdown", e);
    }
  }

  // static class
  @Data
  public static class SysJob implements Serializable {

    private String jobName;

    private String jobGroup;

    private String desc;

    private String status;

    private String cronExpression;

  }

  // -----------------------private--------------------------
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

    int cpuCount = Runtime.getRuntime().availableProcessors();
    cpuCount = cpuCount > 16 ? 16 : cpuCount;
    cpuCount = cpuCount < 10 ? 10 : cpuCount;

    Properties result = new Properties();
    result.put("org.quartz.threadPool.class", org.quartz.simpl.SimpleThreadPool.class.getName());
    result.put("org.quartz.threadPool.threadCount", "" + cpuCount);
    result.put("org.quartz.scheduler.instanceName", "sea-core-quartz-scheduler");
    // misfireThreshold是用来设置调度引擎对触发器超时的忍耐时间, 单位ms
    // 当一个触发器超时时间如果大于misfireThreshold的值 就认为这个触发器真正的超时(也叫Misfires)
//        result.put("org.quartz.jobStore.misfireThreshold", "5000");
//        result.put("org.quartz.plugin.shutdownhook.class", JobShutdownHookPlugin.class.getName());
//        result.put("org.quartz.plugin.shutdownhook.cleanShutdown", Boolean.TRUE.toString());
    return result;
  }


}
