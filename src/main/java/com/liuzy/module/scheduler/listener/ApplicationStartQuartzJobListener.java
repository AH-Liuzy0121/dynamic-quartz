package com.liuzy.module.scheduler.listener;

import com.liuzy.module.scheduler.quartz.QuartzScheduler;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @className: ApplicationStartQuartzJobListener
 * @package: com.liuzy.module.scheduler.listener
 * @describe: 添加监听
 * @auther: liuzhiyong
 * @date: 2018/5/4
 * @time: 下午 3:20
 */
@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private QuartzScheduler quartzScheduler;

    /**
     * @methodName: onApplicationEvent
     * @param: [contextRefreshedEvent]
     * @describe: 初始化启动任务
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 3:25
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try{
            quartzScheduler.startJobAll();
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }
    
    /**
     * @methodName: scheduler 
     * @param: []
     * @describe: 注入初始的Scheduler
     * @auther: liuzhiyong
     * @date: 2018/5/4
     * @time: 下午 3:25
     */
    @Bean
    public Scheduler scheduler()throws SchedulerException{
        SchedulerFactory factory = new StdSchedulerFactory();
        return factory.getScheduler();
    }
}
