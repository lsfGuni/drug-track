package com.example.drugtrack.drugBatch.batch;

import jakarta.transaction.Transactional;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/**
 * DrugDetailStepListener는 Spring Batch의 Step 리스너로, 배치 작업의 단계(Step) 전에 수행할 작업을 정의하는 클래스입니다.
 * 주로 배치 단계가 시작되기 전에 필요한 작업을 처리하며, 예를 들어 데이터베이스 테이블을 초기화하는 작업을 수행합니다.
 */
@Component
public class DrugDetailStepListener extends StepExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate; // JDBC 작업을 수행하기 위한 JdbcTemplate

    /**
     * 생성자 주입을 통해 JdbcTemplate을 주입받습니다.
     * JdbcTemplate은 SQL 실행을 간편하게 처리하기 위한 도구입니다.
     *
     * @param jdbcTemplate 데이터베이스 작업을 수행할 JdbcTemplate
     */
    public DrugDetailStepListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 배치 작업의 각 단계(Step) 전에 호출되는 메서드입니다.
     * 이 메서드는 배치 작업이 시작되기 전에 'api_drug_list' 테이블을 초기화(TRUNCATE)합니다.
     *
     * @param stepExecution 배치 단계에 대한 정보가 포함된 StepExecution 객체
     */
    @Override
    @Transactional
    public void beforeStep(StepExecution stepExecution) {
        // 'api_drug_list' 테이블의 데이터를 모두 삭제 (TRUNCATE)
        jdbcTemplate.execute("TRUNCATE TABLE api_drug_list");
        System.out.println("Table 'api_drug_list' has been truncated."); // 테이블이 초기화되었음을 로그로 출력
    }
}
