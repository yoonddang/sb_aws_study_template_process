package com.template.process.configuration;

import com.template.common.configuration.properties.ProjectData;
import com.template.process.jobstore.AutowiringSpringBeanJobFactory;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Configuration
public class BatchConfiguration {
    private Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ProjectData projectData;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
        Map<String, Trigger> map = applicationContext.getBeansOfType(Trigger.class);

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("ll");
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(map.values().toArray(new Trigger[map.values().size()]));

        return factory;
    }
}
