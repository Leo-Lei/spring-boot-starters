package com.leibangzhu.starters.scheduler;

import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.lang.reflect.Method;
import java.text.ParseException;

/**
 * Author: lili
 * Date: 2017/2/17
 * Time: 15:17
 */

public abstract class BaseScheduleJob implements Job {

    private final static Logger logger = LeibangzhuLogger.create(BaseScheduleJob.class);
    private final String jobName;
    private final String triggerName;

    public BaseScheduleJob(String jobName, String triggerName) throws Exception {

        this.jobName = jobName;
        this.triggerName = triggerName;

        validate();
    }

    private void validate() throws Exception {

        int vaildTriggers = 0;
        for (Method method : this.getClass().getDeclaredMethods()){

            if(method.getReturnType().equals(CronTrigger.class) &&
                    null != method.getDeclaredAnnotation(Bean.class)){

                vaildTriggers++;
            }
        }

        if (0 == vaildTriggers){

            throw new ScheduleException(String.format("No valid trigger (Bean) is defined in job class '%s'.", this.getClass()));
        } else if (1 < vaildTriggers){

            throw new ScheduleException(String.format("Only one trigger is supported, but there found %s in job class '%s'.", vaildTriggers, this.getClass()));
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        logger.info(String.format("Scheduled job '%s' is starting.", this.getClass().getName()));
        try {

            run(jobExecutionContext);
        }catch (Exception e){

            logger.error(String.format("Error while executing scheduled job '%s'.", this.getClass().getName()), e);
        }
        logger.info(String.format("Scheduled job '%s' is finished.", this.getClass().getName()));
    }

    protected CronTrigger createCronTrigger(String cronExpression) throws ParseException {

        JobDetailFactoryBean jobDetailFactoryBean = Scheduler.createJobDetail(jobName, this.getClass());
        jobDetailFactoryBean.afterPropertiesSet();

        CronTriggerFactoryBean cronTriggerFactoryBean = Scheduler.createCronTrigger(triggerName, jobDetailFactoryBean.getObject(), cronExpression);
        cronTriggerFactoryBean.afterPropertiesSet();

        return cronTriggerFactoryBean.getObject();
    }

    protected abstract void run(JobExecutionContext jobExecutionContext) throws Exception;
}
