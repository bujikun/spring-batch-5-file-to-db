package dev.bujiku.batch_demo;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Configuration(proxyBeanMethods = false)
@EnableBatchProcessing
@RequiredArgsConstructor
public class SalesInfoBatchConfig {
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public ItemReader<SalesInfoDTO> itemReader(LineMapper<SalesInfoDTO> lineMapper) {
        var reader = new FlatFileItemReader<SalesInfoDTO>();
        reader.setResource(new ClassPathResource("data/sales-info.csv"));
        reader.setName("salesInfoItemReader");
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public LineMapper<SalesInfoDTO> lineMapper() {

        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("product", "seller", "sellerId", "price", "city", "category");
        tokenizer.setStrict(false);

        // var fieldSetMapper = new BeanWrapperFieldSetMapper<SalesInfoDTO>();
        //fieldSetMapper.setTargetType(SalesInfoDTO.class);


        var mapper = new DefaultLineMapper<SalesInfoDTO>();
        mapper.setLineTokenizer(tokenizer);
        //mapper.setFieldSetMapper(fieldSetMapper);
        return mapper;
    }

    public JpaItemWriter<SalesInfo> itemWriter() {
        return new JpaItemWriterBuilder<SalesInfo>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }


}
