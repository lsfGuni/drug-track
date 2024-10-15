package com.example.drugtrack.drugBatch.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@Tag(name = "의약품 공공데이터 정보를 DB에 저장하는 API", description = "당일 의약품 데이터를 DB에 저장하는 API, batch 로 실행됨 예상시간 20분")
public class DrugDetailBatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job drugDetailJob;

    @PostMapping("/run")
    public String runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("API로실행", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(drugDetailJob, jobParameters);
            return "Batch job has been invoked";
        } catch (Exception e) {
            e.printStackTrace();
            return "Batch job failed: " + e.getMessage();
        }
    }
}
