package dev.bujiku.batch_demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Configuration(proxyBeanMethods = false)
@EnableBatchProcessing
@RequiredArgsConstructor
public class SalesInfoBatchConfig {

    @Bean
    public FlatFileItemReader<SalesInfo> itemReader(LineMapper<SalesInfo> lineMapper) {
        var reader = new FlatFileItemReader<SalesInfo>();
        reader.setResource(new ClassPathResource("data/sales-info.csv"));
        reader.setName("salesInfoItemReader");
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public LineMapper<SalesInfo> lineMapper() {

        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("product", "seller", "sellerId", "price", "city", "category");
        tokenizer.setStrict(false);

        var mapper = new DefaultLineMapper<SalesInfo>();
        mapper.setLineTokenizer(tokenizer);
        return mapper;
    }

}
