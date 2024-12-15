package com.example.springbatchseed;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

class ManualJobRunnerTest {

    @Test
    void testManualJobRunner() throws Exception {
        // Mock dependencies
        Job simpleChunkJob = mock(Job.class);
        Job simpleTaskletJob = mock(Job.class);
        JobLauncher jobLauncher = mock(JobLauncher.class);

        // Create ManualJobRunner with mocked dependencies
        ManualJobRunner manualJobRunner = new ManualJobRunner(simpleChunkJob, simpleTaskletJob,
            jobLauncher);

        // Execute the method to be tested
        manualJobRunner.run();

        // Verify that the jobs are launched
        verify(jobLauncher, times(1)).run(eq(simpleChunkJob), any());
        verify(jobLauncher, times(1)).run(eq(simpleTaskletJob), any());
    }
}
