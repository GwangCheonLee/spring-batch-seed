package com.example.springbatchseed.batch.job;

import com.example.springbatchseed.another.entity.Another;
import com.example.springbatchseed.another.repository.AnotherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * SimpleTaskletJobConfig 클래스는 Tasklet을 사용하여 데이터를 처리하는 Batch Job을 설정합니다.
 */
@Slf4j
@Configuration
public class SimpleTaskletJobConfig {

    private static final String JOB_NAME = "simpleTaskletJob";
    private static final String STEP_NAME = "simpleTaskletStep";

    private final JobRepository jobRepository;
    private final AnotherRepository anotherRepository;
    private final PlatformTransactionManager transactionManager;

    /**
     * SimpleChunkJobConfig 생성자.
     *
     * @param jobRepository      Spring Batch의 JobRepository
     * @param anotherRepository  AnotherRepository
     * @param transactionManager TransactionManager
     */
    public SimpleTaskletJobConfig(
        JobRepository jobRepository,
        AnotherRepository anotherRepository,
        @Qualifier("anotherTransactionManager") PlatformTransactionManager transactionManager
    ) {
        this.jobRepository = jobRepository;
        this.anotherRepository = anotherRepository;
        this.transactionManager = transactionManager;
    }

    /**
     * SimpleTaskletJob을 생성합니다.
     *
     * @return Job 객체
     */
    @Bean
    public Job simpleTaskletJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
            .start(simpleTaskletStep())
            .build();
    }

    /**
     * SimpleTaskletStep을 생성합니다.
     *
     * @return Step 객체
     */
    @Bean
    public Step simpleTaskletStep() {
        return new StepBuilder(STEP_NAME, jobRepository)
            .tasklet(anotherTasklet(), transactionManager)
            .build();
    }

    /**
     * Tasklet을 생성하여 데이터를 처리하는 작업을 정의합니다.
     *
     * @return Tasklet 객체
     */
    @Bean
    public Tasklet anotherTasklet() {
        return (contribution, chunkContext) -> {
            Another another = anotherRepository.findById(1L).orElse(null);
            if (another != null) {
                another.setColumn1("Tasklet");
                another.setColumn2("Tasklet");
                anotherRepository.save(another);
                log.info("Another entity updated with id: 1");
            } else {
                log.warn("No Another entity found with id: 1");
            }

            return RepeatStatus.FINISHED;
        };
    }
}
