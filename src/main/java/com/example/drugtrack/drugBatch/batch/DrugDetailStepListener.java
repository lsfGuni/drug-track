package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.repository.DrugDetailResponseRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DrugDetailStepListener extends StepExecutionListenerSupport {

    private final DrugDetailResponseRepository drugDetailResponseRepository;

    @Autowired
    public DrugDetailStepListener(DrugDetailResponseRepository drugDetailResponseRepository) {
        this.drugDetailResponseRepository = drugDetailResponseRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // 배치 시작 전에 기존 데이터를 삭제
        drugDetailResponseRepository.deleteAll();
        System.out.println("All existing data has been deleted.");
    }
}
