package com.liuzy.module.scheduler.quartz;

import com.liuzy.module.scheduler.job.ScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @className: QuartzScheduler
 * @package: com.liuzy.module.scheduler.quartz
 * @describe: TODO
 * @auther: liuzhiyong
 * @date: 2018/5/4
 * @time: 下午 2:12
 */
@Configuration
public class QuartzScheduler {

    @Autowired
    private Scheduler scheduler;

    /**
     * @methodName: startJob
     * @param:
     * @describe: 启动任务
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:14
     */
    public void startJobAll() throws SchedulerException{
        startJob(scheduler);
        scheduler.start();
    }

    /**
     * @methodName: startJob
     * @param: scheduler
     * @describe: 启动单个Job
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:25
     */
    public void startJob(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity("job1", "group1").build();
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule("0/20 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1", "group1").withSchedule(cronSchedule).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * @methodName: getJobInfo
     * @param: [name, group]
     * @describe: 获取Job信息
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:32
     */
    public String getJobInfo(String name , String group) throws SchedulerException{
        TriggerKey triggerKey = new TriggerKey(name,group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s",cronTrigger.getCronExpression(),scheduler.getTriggerState(triggerKey));
    }

    /**
     * @methodName: pauseJob
     * @param: [name, group]
     * @describe: 暂停一个任务
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:39
     */
    public void pauseJob(String name,String group)throws SchedulerException{
        JobKey jobKey = new JobKey(name,group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null){
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * @methodName: pauseAllJob
     * @param: []
     * @describe: 暂停所有任务
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:44
     */
    public void pauseAllJob()throws SchedulerException{
        scheduler.pauseAll();
    }

    /**
     * @methodName: resumeJob
     * @param: [name, group]
     * @describe: 恢复一个任务
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:48
     */
    public void resumeJob(String name,String group)throws SchedulerException{
        JobKey jobKey = new JobKey(name,group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if(jobDetail == null){
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * @methodName: resumeAllJob
     * @param: []
     * @describe: 恢复所有任务
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:49
     */
    public void resumeAllJob()throws SchedulerException{
        scheduler.resumeAll();
    }

    /**
     * @methodName: deleteJob
     * @param: [name, group]
     * @describe: 删除一个任务
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 2:53
     */
    public void deleteJob(String name,String group)throws SchedulerException{
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if(jobDetail == null){
            return;
        }
        scheduler.deleteJob(jobKey);
    }

    /**
     * @methodName: modifyJob
     * @param: [name, group, time]
     * @describe: 修改某个任务的执行时间
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 3:01
     */
    public boolean modifyJob(String name,String group,String time)throws SchedulerException{
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldExpression = cronTrigger.getCronExpression();
        if(!oldExpression.equalsIgnoreCase(time)){
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }

        return date != null;
    }
}
