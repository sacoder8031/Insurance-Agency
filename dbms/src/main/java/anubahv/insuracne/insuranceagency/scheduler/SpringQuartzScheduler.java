package anubahv.insuracne.insuranceagency.scheduler;

import anubahv.insuracne.insuranceagency.configurations.AutoWiringSpringBeanJobFactory;
import anubahv.insuracne.insuranceagency.job.ExpiryCheckingJob;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Calendar;
import java.util.Date;

@Configuration
@EnableAutoConfiguration
public class SpringQuartzScheduler {
    private ApplicationContext applicationContext;

    @Autowired
    public SpringQuartzScheduler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
        System.out.println("running the scheduler!");
    }

    @Bean
    public JobDetailFactoryBean jobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(ExpiryCheckingJob.class);
        jobDetailFactoryBean.setDescription("Invoke Sample JOb Service...");
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail job){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(job);
        Date date = new Date();
        date.setTime(100);
        simpleTriggerFactoryBean.setStartTime(date);
        simpleTriggerFactoryBean.setRepeatInterval(86400000);
        simpleTriggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return simpleTriggerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory(){
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, DataSource quartzDataSource){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactoryBean.setJobFactory(springBeanJobFactory());
        schedulerFactoryBean.setJobDetails(job);
        schedulerFactoryBean.setTriggers(trigger);
        return schedulerFactoryBean;
    }
}
