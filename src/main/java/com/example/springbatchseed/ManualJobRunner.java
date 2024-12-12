package com.example.springbatchseed;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManualJobRunner implements CommandLineRunner {

    private final Job simpleChunkJob;
    private final Job simpleTaskletJob;
    private final JobLauncher jobLauncher;

    @Override
    public void run(String... args) throws Exception {
        String executionDate = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("executionDate", executionDate)
            .toJobParameters();

        jobLauncher.run(simpleChunkJob, jobParameters);
        jobLauncher.run(simpleTaskletJob, jobParameters);
    }
}
