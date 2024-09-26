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

    // 하루에 한 번 (매일 자정) 실행
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    public void runFirstJob() throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("크론식실행-매일자정", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("drugDetailJob"), jobParameters);
    }
}
