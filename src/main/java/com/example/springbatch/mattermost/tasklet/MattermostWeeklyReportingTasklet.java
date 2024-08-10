package com.example.springbatch.mattermost.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MattermostWeeklyReportingTasklet implements Tasklet {


    @Override
    public RepeatStatus execute(org.springframework.batch.core.StepContribution contribution, org.springframework.batch.core.scope.context.ChunkContext chunkContext) throws Exception {
        log.info("Run MattermostWeeklyReportingTasklet");

        return RepeatStatus.FINISHED;
    }
}
