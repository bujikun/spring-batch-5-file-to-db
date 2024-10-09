package dev.bujiku.batch_demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * Execute logic before step starts
 *
 * @author Newton Bujiku
 * @since 2024
 */
@RequiredArgsConstructor
@Slf4j
public class SalesInfoStepExecutionListener implements StepExecutionListener {
    private final SalesInfoRepository salesInfoRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // StepExecutionListener.super.beforeStep(stepExecution);
        //stepExecution.
       // salesInfoRepository.deleteAll();
        log.info("NUMBER OF ITEMS IN DB BEFORE STEP: {}", salesInfoRepository.count());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("NUMBER OF ITEMS IN DB AFTER STEP: {}", salesInfoRepository.count());
        return stepExecution.getExitStatus();
    }
}
