package com.example.springbatchseed.batch.job;

import com.example.springbatchseed.another.entity.Another;
import com.example.springbatchseed.another.entity.QAnother;
import com.example.springbatchseed.batch.itemreader.QuerydslPagingItemReader;
import com.example.springbatchseed.common.property.BatchProperties;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * SimpleChunkJobConfig 클래스는 QuerydslPagingItemReader를 사용하여 Another 엔티티를 Chunk 기반으로 처리하는 Batch
 * Job을설정합니다.
 */
@Slf4j
@Configuration
public class SimpleChunkJobConfig {

    private static final String JOB_NAME = "simpleChunkJob";
    private static final String STEP_NAME = "simpleChunkStep";
    private final int chunkSize;

    private final JobRepository jobRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;

    /**
     * SimpleChunkJobConfig 생성자.
     *
     * @param jobRepository        Spring Batch의 JobRepository
     * @param entityManagerFactory EntityManagerFactory
     * @param transactionManager   TransactionManager
     * @param properties           BatchProperties
     */
    public SimpleChunkJobConfig(
        JobRepository jobRepository,
        @Qualifier("anotherEntityManagerFactory") EntityManagerFactory entityManagerFactory,
        @Qualifier("anotherTransactionManager") PlatformTransactionManager transactionManager,
        BatchProperties properties) {
        this.jobRepository = jobRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.transactionManager = transactionManager;
        this.chunkSize = properties.getChunkSize();
    }

    /**
     * SimpleChunkJob을 생성합니다.
     *
     * @return Job 객체
     */
    @Bean
    public Job simpleChunkJob() {
        return new JobBuilder(JOB_NAME, jobRepository)
            .start(simpleChunkStep())
            .build();
    }

    /**
     * SimpleChunkStep을 생성합니다.
     *
     * @return Step 객체
     */
    @Bean
    public Step simpleChunkStep() {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<Another, Another>chunk(chunkSize, transactionManager)
            .reader(anotherReader())
            .processor(anotherProcessor())
            .writer(anotherWriter())
            .build();
    }


    /**
     * QuerydslPagingItemReader를 생성하여 Another 데이터를 읽어옵니다.
     *
     * @return QuerydslPagingItemReader 객체
     */
    @Bean
    public QuerydslPagingItemReader<Another> anotherReader() {
        return new QuerydslPagingItemReader<>(
            entityManagerFactory,
            chunkSize,
            queryFactory -> {
                QAnother another = QAnother.another;
                return queryFactory.selectFrom(another)
                    .where(another.column1.isNotNull()).orderBy(another.id.asc());
            });
    }

    /**
     * Another 데이터를 처리하는 Processor.
     *
     * @return ItemProcessor 객체
     */
    @Bean
    public ItemProcessor<Another, Another> anotherProcessor() {
        String executionTime = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return item -> {
            item.setColumn1(executionTime);
            item.setColumn2(executionTime);
            return item;
        };
    }

    /**
     * Another 데이터를 저장하는 Writer.
     *
     * @return JpaItemWriter 객체
     */
    @Bean
    public JpaItemWriter<Another> anotherWriter() {
        return new JpaItemWriterBuilder<Another>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }
}
