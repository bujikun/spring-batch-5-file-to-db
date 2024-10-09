package dev.bujiku.batch_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 * Skip policy to be used if an error is encountered during processing
 * @author Newton Bujiku
 * @since 2024
 */
@Slf4j
public class SalesInfoSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        log.error("", t);
        if (t instanceof FlatFileParseException e) {
            log.error("LINE NUMBER: {}", e.getLineNumber());
            log.error("INPUT : {}", e.getInput());
        } else {
           // log.error("SOME OTHER ERROR: {}", t.getClass().getName());
        }
        return true;
    }
}
