package com.mars.schedule.quartz;

import com.mars.schedule.bean.SysScheduleInfo;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class QuartzService {
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个job
     * @param sysScheduleInfo     任务
     * @param jobTime      时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes     运行的次数 （<0:表示不限次数）
     */
    public void addJob(SysScheduleInfo sysScheduleInfo, int jobTime, int jobTimes) {
        try {
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(SystemJobFactory.class)
                    .withIdentity(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup())
                    .usingJobData("className",sysScheduleInfo.getClassName())
                    .usingJobData("methodName",sysScheduleInfo.getMethodName())
                    .usingJobData("params",sysScheduleInfo.getParams())
                    .build();

            // 使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup())
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();
            } else {
                trigger = TriggerBuilder
                        .newTrigger().withIdentity(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup()).withSchedule(SimpleScheduleBuilder
                                .repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startNow().build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个job
     *
     * @param sysScheduleInfo     任务
     */
    public void addJob(SysScheduleInfo sysScheduleInfo) {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(SystemJobFactory.class)
                    .withIdentity(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup())
                    .usingJobData("className",sysScheduleInfo.getClassName())
                    .usingJobData("methodName",sysScheduleInfo.getMethodName())
                    .usingJobData("params",sysScheduleInfo.getParams())
                    .build();

            // 定义调度触发规则
            // 使用cornTrigger规则
            // 触发器key
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(sysScheduleInfo.getCron())).startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改 一个job的 时间表达式
     *
     * @param sysScheduleInfo
     */
    public void updateJob(SysScheduleInfo sysScheduleInfo) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(sysScheduleInfo.getCron())).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务一个job
     *
     * @param sysScheduleInfo      任务
     */
    public void deleteJob(SysScheduleInfo sysScheduleInfo) {
        try {
            scheduler.deleteJob(new JobKey(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停一个job
     *
     * @param sysScheduleInfo
     */
    public void pauseJob(SysScheduleInfo sysScheduleInfo) {
        try {
            JobKey jobKey = JobKey.jobKey(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复一个job
     *
     * @param sysScheduleInfo
     */
    public void resumeJob(SysScheduleInfo sysScheduleInfo) {
        try {
            JobKey jobKey = JobKey.jobKey(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即执行一个job
     *
     * @param sysScheduleInfo
     */
    public void runAJobNow(SysScheduleInfo sysScheduleInfo) {
        try {
            JobKey jobKey = JobKey.jobKey(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup());
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    public List<Map<String, Object>> queryAllJob() {
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public List<Map<String, Object>> queryRunJob() {
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    public boolean isRunning(SysScheduleInfo sysScheduleInfo){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(sysScheduleInfo.getTaskName(), sysScheduleInfo.getTaskGroup());

            scheduler.getTriggerState(triggerKey);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
