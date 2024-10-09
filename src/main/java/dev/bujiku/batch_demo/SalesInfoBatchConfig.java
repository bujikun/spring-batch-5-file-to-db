package dev.bujiku.batch_demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Configuration
@RequiredArgsConstructor
public class SalesInfoBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final SalesInfoItemProcessor salesInfoItemProcessor;
    private final SalesInfoRepository salesInfoRepository;

    @Bean
    public ItemReader<SalesInfoDTO> itemReader() {
//        var reader = new FlatFileItemReader<SalesInfoDTO>();
//        reader.setResource(new ClassPathResource("data/sales-info.csv"));
//        reader.setName("salesInfoItemReader");
//        reader.setLineMapper(lineMapper());
//        reader.setLinesToSkip(1);
//        return reader;
        return new FlatFileItemReaderBuilder<SalesInfoDTO>()
                .resource(new ClassPathResource("data/sales-info.csv"))
                .name("salesInfoItemReader")
                .targetType(SalesInfoDTO.class)
                .strict(true)
                //.lineMapper(lineMapper())
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .strict(false)
                .names("product", "seller", "sellerId", "price", "city", "category")
                //.lineTokenizer(lineTokenizer())
                .build();
    }

//    public LineTokenizer lineTokenizer() {
//        var tokenizer = new DelimitedLineTokenizer();
//        tokenizer.setDelimiter(",");
//        tokenizer.setNames("product", "seller", "sellerId", "price", "city", "category");
//        tokenizer.setStrict(false);
//        return tokenizer;
//    }

    //@Bean
//    public LineMapper<SalesInfoDTO> lineMapper() {
//
//
//        var fieldSetMapper = new RecordFieldSetMapper<>(SalesInfoDTO.class);
//
//        var mapper = new DefaultLineMapper<SalesInfoDTO>();
//        // mapper.setLineTokenizer(tokenizer);
//        mapper.setFieldSetMapper(fieldSetMapper);
//
//        return mapper;
//    }

    @Bean
    public RepositoryItemWriter<SalesInfo> itemWriter() {
        var writer = new RepositoryItemWriter<SalesInfo>();
        writer.setRepository(salesInfoRepository);
        return writer;
    }

    //@Bean
    public TaskExecutor taskExecutor() {
        var executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(100);
        executor.setVirtualThreads(true);
        return executor;
    }

    @Bean
    public Step fromFileToDB() {
        return new StepBuilder("readFromFileToDB", jobRepository)
                .<SalesInfoDTO, SalesInfo>chunk(200, platformTransactionManager)
                .taskExecutor(taskExecutor())
                .reader(itemReader())
                .processor(salesInfoItemProcessor)
                .writer(itemWriter())
                .faultTolerant()
                .build();
    }

    @Bean
    public Job importSalesInfo(Step step) {
        return new JobBuilder("importSalesInfo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fromFileToDB())
                .build();
    }


}
