package com.example.drugtrack.drugBatch.config;

import com.example.drugtrack.drugBatch.batch.DrugDetailItemProcessor;
import com.example.drugtrack.drugBatch.batch.DrugDetailItemReader;
import com.example.drugtrack.drugBatch.batch.DrugDetailItemWriter;
import com.example.drugtrack.drugBatch.batch.DrugDetailStepListener;
import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DrugDetailBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DrugDetailItemReader drugDetailItemReader;
    private final DrugDetailItemProcessor drugDetailItemProcessor;
    private final DrugDetailItemWriter drugDetailItemWriter;
    private final DrugDetailStepListener drugDetailStepListener;  // StepListener 추가

    // 생성자 주입
    public DrugDetailBatchConfig(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 DrugDetailItemReader drugDetailItemReader,
                                 DrugDetailItemProcessor drugDetailItemProcessor,
                                 DrugDetailItemWriter drugDetailItemWriter, DrugDetailStepListener drugDetailStepListener) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.drugDetailItemReader = drugDetailItemReader;
        this.drugDetailItemProcessor = drugDetailItemProcessor;
        this.drugDetailItemWriter = drugDetailItemWriter;
        this.drugDetailStepListener = drugDetailStepListener;
    }

    @Bean
    public Job drugDetailJob() {
        return new JobBuilder("drugDetailJob", jobRepository)
                .start(drugDetailStep())
                .build();
    }

    @Bean
    public Step drugDetailStep() {
        return new StepBuilder("drugDetailStep", jobRepository)
                .<DrugDetailResponse, DrugDetailResponse>chunk(100, transactionManager)
                .reader(drugDetailItemReader)
                .processor(drugDetailItemProcessor)
                .writer(drugDetailItemWriter)
                .listener(drugDetailStepListener)  // StepListener 추가
                .build();
    }
}
