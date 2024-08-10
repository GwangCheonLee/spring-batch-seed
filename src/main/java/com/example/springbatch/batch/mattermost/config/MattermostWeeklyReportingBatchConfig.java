package com.example.springbatch.batch.mattermost.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class MattermostWeeklyReportingBatchConfig {

    @Bean("mattermostWeeklyReportingJob")
    public Job configureSampleJob(JobRepository jobRepository, Step mattermostWeeklyReportingStep) {
        return new JobBuilder("mattermostWeeklyReportingJob", jobRepository)
                .start(mattermostWeeklyReportingStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step mattermostWeeklyReportingStep(JobRepository jobRepository, Tasklet mattermostWeeklyReportingTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("mattermostWeeklyReportingStep", jobRepository)
                .tasklet(mattermostWeeklyReportingTasklet, platformTransactionManager)
                .build();
    }

}
