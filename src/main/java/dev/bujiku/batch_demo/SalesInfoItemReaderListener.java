package dev.bujiku.batch_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 * Listener used when reading from a file
 * @author Newton Bujiku
 * @since 2024
 */
@Slf4j
public class SalesInfoItemReaderListener implements ItemReadListener<SalesInfoDTO> {
    @Override
    public void onReadError(Exception ex) {
        if (ex instanceof FlatFileParseException e) {
            log.error("FAILED TO READ =====> {}", e.getInput());
        }
        //
    }


}
