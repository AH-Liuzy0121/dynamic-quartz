package com.liuzy.module.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: SendJob
 * @package: com.liuzy.module.scheduler.job
 * @describe: TODO
 * @auther: liuzhiyong
 * @date: 2018/5/4
 * @time: 下午 2:09
 */
public class SendJob implements Job{


    private static final Logger logger = LoggerFactory.getLogger(SendJob.class);

    public void before(){
        logger.info(" 开始执行任务 ");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        before();
        logger.info("开始执行时间： " + System.currentTimeMillis());

        //TODO 需要执行的任务

        logger.info("结束执行时间： " + System.currentTimeMillis());
        after();
    }

    public void after(){
        logger.info(" 结束执行任务 ");
    }
}
