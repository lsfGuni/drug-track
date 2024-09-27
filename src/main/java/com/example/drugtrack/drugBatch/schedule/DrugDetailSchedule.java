package com.example.drugtrack.drugBatch.schedule;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DrugDetailSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public DrugDetailSchedule(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Scheduled(cron = "0 0 0 1,15 * ?", zone = "Asia/Seoul") // 매 달 1일, 15일에 실행
    //@Scheduled(cron = "0 0 * * * ?", zone = "Asia/Seoul") // 매 시정각에 실행
    public void runFirstJob() throws Exception {

        // 현재 날짜를 사용한 파라미터 생성
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String date = dateFormat.format(new Date());

        // jobParameters 정의
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("매달1,15일실행", date)
                .toJobParameters();

        // 배치 실행
        jobLauncher.run(jobRegistry.getJob("drugDetailJob"), jobParameters);
    }

}
