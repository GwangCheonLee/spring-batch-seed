package com.example.springbatch.band.config;

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
public class BandBatchConfig {

    @Bean("bandJob")
    public Job configureSampleJob(JobRepository jobRepository, Step bandStep) {
        return new JobBuilder("bandJob", jobRepository)
                .start(bandStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step bandStep(JobRepository jobRepository, Tasklet bandTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("bandStep", jobRepository)
                .tasklet(bandTasklet, platformTransactionManager)
                .build();
    }

}
