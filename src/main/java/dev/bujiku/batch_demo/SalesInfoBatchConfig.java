package dev.bujiku.batch_demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
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
        return new FlatFileItemReaderBuilder<SalesInfoDTO>()
                .resource(new ClassPathResource("data/sales-info.csv"))
                .name("salesInfoItemReader")
                .targetType(SalesInfoDTO.class)
                .strict(true)
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .strict(true)
                .names("product", "seller", "sellerId", "price", "city", "category")
                .build();
    }

    @Bean
    public RepositoryItemWriter<SalesInfo> itemWriter() {
        var writer = new RepositoryItemWriter<SalesInfo>();
        writer.setRepository(salesInfoRepository);
        return writer;
    }

    public TaskExecutor taskExecutor() {
        var executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(500);
        executor.setVirtualThreads(true);
        return executor;
    }


    @Bean
    public Step fromFileToDB() {
        return new StepBuilder("readFromFileToDB", jobRepository)
                .<SalesInfoDTO, SalesInfo>chunk(1000, platformTransactionManager)
                // .<SalesInfoDTO, Future<SalesInfo>>chunk(1000, platformTransactionManager)
                .taskExecutor(taskExecutor())
                .reader(itemReader())
                .processor(salesInfoItemProcessor)
                .writer(itemWriter())
                .faultTolerant()
                //.skipPolicy(new AlwaysSkipItemSkipPolicy())
                .skipPolicy(new SalesInfoSkipPolicy())
                .listener(new SalesInfoItemReaderListener())
                .listener(new SalesInfoStepExecutionListener(salesInfoRepository))
                .build();
    }

    @Bean
    public Job importSalesInfoJob(Step step) {
        return new JobBuilder("importSalesInfo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fromFileToDB())
                .build();
    }

    @Bean
    public Job anotherJob(Step step) {
        return new JobBuilder("anotherJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fromFileToDB())
                .build();
    }

}
