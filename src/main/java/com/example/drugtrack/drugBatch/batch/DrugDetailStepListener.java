package com.example.drugtrack.drugBatch.batch;

import jakarta.transaction.Transactional;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DrugDetailStepListener extends StepExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DrugDetailStepListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void beforeStep(StepExecution stepExecution) {
        // 배치 시작 전에 TRUNCATE 명령 실행
        jdbcTemplate.execute("TRUNCATE TABLE api_drug_list");
        System.out.println("Table 'api_drug_list' has been truncated.");
    }
}
