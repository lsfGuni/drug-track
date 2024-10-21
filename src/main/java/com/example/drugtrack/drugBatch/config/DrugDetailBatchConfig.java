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
/**
 * DrugDetailBatchConfig는 의약품 데이터를 배치 처리하기 위한 Spring Batch 구성 클래스입니다.
 * 배치 작업(Job)과 단계(Step)를 설정하고, 각 단계에 대한 리더, 프로세서, 라이터, 리스너 등을 설정합니다.
 */
@Configuration
public class DrugDetailBatchConfig {

    private final JobRepository jobRepository;  // 배치 작업 저장소 (JobRepository)
    private final PlatformTransactionManager transactionManager; // 트랜잭션 관리자 (PlatformTransactionManager)
    private final DrugDetailItemReader drugDetailItemReader;  // 의약품 데이터를 읽어오는 리더
    private final DrugDetailItemProcessor drugDetailItemProcessor;  // 데이터를 처리하는 프로세서
    private final DrugDetailItemWriter drugDetailItemWriter; // 데이터를 저장하는 라이터
    private final DrugDetailStepListener drugDetailStepListener;  // 배치 단계에 대한 리스너 추가

    /**
     * 생성자 주입을 통해 필수 의존성을 주입받습니다.
     *
     * @param jobRepository 배치 작업 저장소
     * @param transactionManager 트랜잭션 관리자
     * @param drugDetailItemReader 의약품 데이터를 읽어오는 리더
     * @param drugDetailItemProcessor 데이터를 처리하는 프로세서
     * @param drugDetailItemWriter 데이터를 저장하는 라이터
     * @param drugDetailStepListener 배치 단계에 대한 리스너
     */
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


    /**
     * drugDetailJob 메서드는 배치 작업을 정의하는 메서드입니다.
     * 해당 작업은 drugDetailStep()에서 정의한 단계를 실행합니다.
     *
     * @return 배치 작업 (Job)
     */
    @Bean
    public Job drugDetailJob() {
        return new JobBuilder("drugDetailJob", jobRepository)
                .start(drugDetailStep()) // 배치 작업의 첫 번째 단계 설정
                .build();
    }

    /**
     * drugDetailStep 메서드는 배치 작업의 단계를 정의합니다.
     * 각 단계는 데이터의 읽기, 처리, 쓰기 과정을 포함하며, 단계가 성공적으로 완료되면 다음 단계로 넘어갑니다.
     *
     * chunk(100): 한번에 100개의 데이터를 처리합니다.
     * faultTolerant(): 오류 허용 설정, 재시도 및 스킵 기능을 활성화합니다.
     * retryLimit(2): 최대 2번까지 재시도합니다.
     * skipLimit(5): 최대 5개의 항목을 스킵할 수 있습니다.
     *
     * @return 배치 작업의 단계 (Step)
     */
    @Bean
    public Step drugDetailStep() {
        return new StepBuilder("drugDetailStep", jobRepository)
                .<DrugDetailResponse, DrugDetailResponse>chunk(100, transactionManager) // 한 번에 100개의 데이터 처리
                .listener(drugDetailStepListener)   // 배치 단계 리스너 추가
                .reader(drugDetailItemReader)  // 데이터 읽기 설정
                .processor(drugDetailItemProcessor) // 데이터 처리 설정
                .writer(drugDetailItemWriter)   // 데이터 저장 설정
                .faultTolerant()  // 오류 허용 설정
                .retry(Exception.class)     // 예외 발생 시 재시도
                .retryLimit(2)              // 최대 2번 재시도
                .skip(Exception.class)      // 실패 시 해당 항목을 스킵
                .skipLimit(5)               // 최대 5개 항목을 스킵 가능
                .build();
    }
}
