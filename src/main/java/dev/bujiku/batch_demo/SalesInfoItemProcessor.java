package dev.bujiku.batch_demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SalesInfoItemProcessor implements ItemProcessor<SalesInfoDTO, SalesInfo>,
        ItemProcessListener<SalesInfoDTO, SalesInfo> {
    private final SalesInfoMapper salesInfoMapper;

    @Override
    public SalesInfo process(SalesInfoDTO item) throws Exception {
        //validate
        //filter
        // log.info("PROCESSING ====> {}", item);
        var salesInfo = salesInfoMapper.mapToEntity(item);
        salesInfo.setSeller(item.seller().toUpperCase(Locale.ROOT));
        return salesInfo;
    }

    @Override
    public void beforeProcess(SalesInfoDTO item) {
        // ItemProcessListener.super.beforeProcess(item);
        //log.info("PASSED =====> {}", item);
    }

    @Override
    public void afterProcess(SalesInfoDTO item, SalesInfo result) {
        ItemProcessListener.super.afterProcess(item, result);
    }

    @Override
    public void onProcessError(SalesInfoDTO item, Exception e) {
        //ItemProcessListener.super.onProcessError(item, e);
        log.error("FAILED TO PROCESS =====> {}", item);
    }
}
