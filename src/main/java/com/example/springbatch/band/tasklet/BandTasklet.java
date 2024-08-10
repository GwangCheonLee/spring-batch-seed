package com.example.springbatch.band.tasklet;

import com.example.springbatch.band.entities.Band;
import com.example.springbatch.band.repositories.BandRepositorySupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BandTasklet implements Tasklet {

    private final BandRepositorySupport anotherRepository;

    public BandTasklet(BandRepositorySupport anotherRepository) {
        this.anotherRepository = anotherRepository;
    }


    @Override
    public RepeatStatus execute(org.springframework.batch.core.StepContribution contribution, org.springframework.batch.core.scope.context.ChunkContext chunkContext) throws Exception {
        log.info("Run BandTasklet");

        List<Band> bands = anotherRepository.findAllBand();

        log.info("Select all from band table");
        log.info("Band size: {}", bands.size());

        return RepeatStatus.FINISHED;
    }
}
