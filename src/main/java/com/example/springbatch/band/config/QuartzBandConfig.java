package com.example.springbatch.band.config;

import com.example.springbatch.band.job.QuartzBandJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzBandConfig {

    @Bean
    public Trigger quartzJobBandTrigger(JobDetail quartzBandDetail) {
        String everyFiveSecondsCron = "0/5 * * * * ?";

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();

        triggerBuilder.forJob(quartzBandDetail);
        triggerBuilder.withIdentity("quartzBandTrigger");
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(everyFiveSecondsCron));

        return triggerBuilder.build();
    }

    @Bean
    public JobDetail quartzBandDetail() {
        JobBuilder jobBuilder = JobBuilder.newJob(QuartzBandJob.class);

        jobBuilder.withIdentity("quartzBandDetail");
        jobBuilder.storeDurably();

        return jobBuilder.build();
    }
}
