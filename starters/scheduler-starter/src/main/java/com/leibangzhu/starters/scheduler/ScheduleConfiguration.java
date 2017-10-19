package com.leibangzhu.starters.scheduler;

import com.leibangzhu.starters.common.util.QibeiLogger;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Author: lili
 * Date: 2017/2/17
 * Time: 14:28
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class ScheduleConfiguration {

    private static final Logger logger = QibeiLogger.create(ScheduleConfiguration.class);

    @Lazy
    @Autowired
    List<Trigger> listOfTrigger;

    //@Autowired
    public DataSource defaultDataSource;

    @Bean(name = "quartzDataSource")
    public DataSource quartzDataSource(@Qualifier("quartzProperties")Properties properties) {

        if(StringUtils.isEmpty(properties.getProperty("org.quartz.dataSource.myDS.URL"))){

            if(null == defaultDataSource){

                if(logger.isInfoEnabled()) {

                    logger.error("'org.quartz.dataSource' is missing in application.properties.");
                }
            }else{

                if(logger.isInfoEnabled()) {
                    logger.info("Default data source is used for Quartz.");
                }
                return defaultDataSource;
            }
        }

        DataSourceBuilder builder = DataSourceBuilder.create();
        return builder
                .driverClassName(properties.getProperty("org.quartz.dataSource.myDS.driver"))
                .url(properties.getProperty("org.quartz.dataSource.myDS.URL"))
                .username(properties.getProperty("org.quartz.dataSource.myDS.user"))
                .password(properties.getProperty("org.quartz.dataSource.myDS.password"))
                .build();
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("quartzProperties")Properties properties, @Qualifier("quartzDataSource")DataSource dataSource, JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(properties);

        factory.setSchedulerName(properties.getProperty("quartz.scheduler"));

        // Here we will set all the trigger beans we have defined.
        if (null != listOfTrigger && !listOfTrigger.isEmpty()) {

            factory.setTriggers(listOfTrigger.toArray(new Trigger[listOfTrigger.size()]));
            logger.info(String.format("************ %s triggers are found. ************", listOfTrigger.size()));
        }

        return factory;
    }

    @Bean(name = "quartzProperties")
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("application.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
