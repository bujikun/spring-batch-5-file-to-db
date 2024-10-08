package dev.bujiku.batch_demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Component
@RequiredArgsConstructor
public class SalesInfoItemProcessor implements ItemProcessor<SalesInfoDTO, SalesInfo> {
    private final SalesInfoMapper salesInfoMapper;

    @Override
    public SalesInfo process(SalesInfoDTO item) throws Exception {
        //validate
        //filter
        var salesInfo = salesInfoMapper.mapToEntity(item);
        salesInfo.setSeller(item.seller().toUpperCase(Locale.ROOT));
        return salesInfo;
    }
}
