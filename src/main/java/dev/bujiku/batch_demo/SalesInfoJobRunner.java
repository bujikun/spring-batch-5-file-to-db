package dev.bujiku.batch_demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SalesInfoJobRunner {
    private final JobLauncher jobLauncher;
    private final Job importSalesInfoJob;

//    @Scheduled(cron = "0/30 * * ? * *")
//    void runJob() {
//        log.info("ABOUT TO START A JOB");
//        var jobParams = new JobParametersBuilder()
//                .addLong("startedAt", Instant.now().toEpochMilli())
//                .toJobParameters();
//        try {
//            jobLauncher.run(importSalesInfoJob, jobParams);
//        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
//                 JobParametersInvalidException e) {
//            log.error("", e);
//        }
//    }

    @EventListener(classes = ApplicationReadyEvent.class)
    void run() {
        log.info("ABOUT TO START A JOB");
        var jobParams = new JobParametersBuilder()
                .addLong("startedAt", Instant.now().toEpochMilli())
                .toJobParameters();
        try {
            jobLauncher.run(importSalesInfoJob, jobParams);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error("", e);
        }
    }
}
