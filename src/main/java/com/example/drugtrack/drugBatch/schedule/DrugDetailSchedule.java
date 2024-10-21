package com.example.drugtrack.drugBatch.schedule;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * DrugDetailSchedule 클래스는 Spring의 스케줄러를 사용하여 특정 시간에 배치 작업을 자동으로 실행하는 설정을 제공합니다.
 * 이 클래스는 배치 작업을 정기적으로 실행할 수 있도록 구성되며, 실행 시간은 cron 표현식을 통해 설정할 수 있습니다.
 *
 * 현재 이 스케줄러는 사용 중이지 않으며, 활성화되면 매달 1일과 15일에 의약품 데이터를 처리하는 배치 작업이 실행됩니다.
 * TODO: application.yml 파일에서 스케줄러 설정을 관리하고 있으며, 스케줄러를 사용하려면 아래 설정을 추가하세요.
 */
@Configuration
public class DrugDetailSchedule {

    private final JobLauncher jobLauncher;  // 배치 작업을 실행하는 JobLauncher
    private final JobRegistry jobRegistry;  // 배치 작업을 관리하는 JobRegistry

    /**
     * 생성자 주입을 통해 JobLauncher와 JobRegistry를 주입받습니다.
     *
     * @param jobLauncher 배치 작업을 실행하는 JobLauncher
     * @param jobRegistry 배치 작업을 관리하는 JobRegistry
     */
    public DrugDetailSchedule(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    /**
     * runFirstJob 메서드는 스케줄에 따라 실행되는 배치 작업을 정의합니다.
     * 이 메서드는 매달 1일과 15일에 실행되며, JobParameters에 현재 시간을 포함하여 배치를 실행합니다.
     *
     * @throws Exception 배치 작업 실행 중 발생할 수 있는 예외
     */
    @Scheduled(cron = "0 0 0 1,15 * ?", zone = "Asia/Seoul") // 매 달 1일, 15일에 실행
    //@Scheduled(cron = "0 0 * * * ?", zone = "Asia/Seoul") // 매 시정각에 실행
    public void runFirstJob() throws Exception {

        // 현재 날짜와 시간을 포맷팅하여 JobParameters에 추가
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String date = dateFormat.format(new Date());

        // 배치 작업 실행에 필요한 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("매달1,15일실행", date)
                .toJobParameters();

        // 배치 작업 실행
        jobLauncher.run(jobRegistry.getJob("drugDetailJob"), jobParameters);
    }

    // TODO: 이 스케줄러는 현재 사용 중이지 않습니다. 스케줄러를 활성화하려면 application.yml 파일에 다음과 같이 설정하세요.
    // scheduling:
    //   enabled: true
}
