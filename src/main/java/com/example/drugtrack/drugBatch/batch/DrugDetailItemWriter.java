package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import com.example.drugtrack.drugBatch.repository.DrugDetailResponseRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DrugDetailItemWriter implements ItemWriter<DrugDetailResponse> {

    private final DrugDetailResponseRepository drugDetailResponseRepository;

    public DrugDetailItemWriter(DrugDetailResponseRepository drugDetailResponseRepository) {
        this.drugDetailResponseRepository = drugDetailResponseRepository;
    }

    @Override
    public void write(Chunk<? extends DrugDetailResponse> items) throws Exception {
        try {
            drugDetailResponseRepository.saveAll(items);
        } catch (Exception e) {
            // 재시도와 스킵이 이 예외 처리에서 발생
            throw new Exception("Error saving batch of DrugDetailResponse", e);
        }
    }
}
