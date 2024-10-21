package com.example.drugtrack.drugBatch.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * DrugDetailBatchController는 Spring Batch를 사용하여 의약품 데이터를 배치로 처리하고 DB에 저장하는 API를 제공합니다.
 * 이 컨트롤러는 API 호출을 통해 배치 작업을 수동으로 실행할 수 있습니다.
 */
@RestController
@RequestMapping("/batch")
@Tag(name = "의약품 공공데이터 정보를 DB에 저장하는 API", description = "당일 의약품 데이터를 DB에 저장하는 API, batch 로 실행됨 예상시간 20분")
public class DrugDetailBatchController {

    private final JobLauncher jobLauncher;
    private final Job job;
    /**
     * 생성자 주입을 사용하여 JobLauncher와 Job을 주입받습니다.
     *
     * @param jobLauncher 배치 작업을 실행하는 JobLauncher
     * @param job 실행할 배치 작업
     */
    public DrugDetailBatchController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    /**
     * Batch 작업을 실행하는 API 엔드포인트입니다.
     * 이 메서드는 POST 요청을 통해 배치 작업을 트리거하며, 현재 시간을 파라미터로 전달하여 매번 새로운 작업을 실행하도록 합니다.
     *
     * @return 배치 작업의 성공 또는 실패 메시지를 반환
     */
    @PostMapping("/run")
    public String runBatchJob() {
        try {
            // 배치 작업에 필요한 파라미터를 설정 (현재 시간을 사용하여 중복 실행 방지)
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("Run from API", System.currentTimeMillis())
                    .toJobParameters();

            // JobLauncher를 사용하여 배치 작업 실행
            jobLauncher.run(job, jobParameters);
            return "Batch job has been invoked"; // 성공 시 반환 메시지
        } catch (Exception e) {
            e.printStackTrace(); // 오류 발생 시 스택 트레이스를 출력
            return "Batch job failed: " + e.getMessage(); // 실패 시 에러 메시지 반환
        }
    }
}
