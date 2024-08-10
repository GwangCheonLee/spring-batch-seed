package com.example.springbatch.quartz.mattermost.config;

import com.example.springbatch.quartz.mattermost.job.QuartzMattermostWeeklyReportingJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzMattermostWeeklyReportingConfig {

    @Bean
    public Trigger quartzJoMattermostWeeklyReportingTrigger(JobDetail quartzMattermostWeeklyReportingDetail) {
        String everyFiveSecondsCron = "0/5 * * * * ?";

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();

        triggerBuilder.forJob(quartzMattermostWeeklyReportingDetail);
        triggerBuilder.withIdentity("quartzMattermostWeeklyReportingTrigger");
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(everyFiveSecondsCron));

        return triggerBuilder.build();
    }

    @Bean
    public JobDetail quartzMattermostWeeklyReportingDetail() {
        JobBuilder jobBuilder = JobBuilder.newJob(QuartzMattermostWeeklyReportingJob.class);

        jobBuilder.withIdentity("quartzMattermostWeeklyReportingDetail");
        jobBuilder.storeDurably();

        return jobBuilder.build();
    }
}
