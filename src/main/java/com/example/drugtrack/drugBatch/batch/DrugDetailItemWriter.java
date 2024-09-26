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
        // 검증 없이 새로운 데이터를 그대로 저장
        drugDetailResponseRepository.saveAll(items);
    }
}
