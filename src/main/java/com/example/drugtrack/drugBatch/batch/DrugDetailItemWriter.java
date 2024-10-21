package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import com.example.drugtrack.drugBatch.repository.DrugDetailResponseRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

/**
 * DrugDetailItemWriter는 Spring Batch의 Writer 역할을 하는 클래스입니다.
 * 이 클래스는 배치 작업에서 처리된 데이터를 데이터베이스에 저장하는 책임을 가집니다.
 */
@Configuration
public class DrugDetailItemWriter implements ItemWriter<DrugDetailResponse> {

    private final DrugDetailResponseRepository drugDetailResponseRepository; // 데이터를 저장할 레포지토리

    /**
     * 생성자 주입을 통해 DrugDetailResponseRepository를 주입받습니다.
     * DrugDetailResponseRepository는 데이터를 데이터베이스에 저장하는 기능을 제공합니다.
     *
     * @param drugDetailResponseRepository 데이터를 저장할 레포지토리
     */
    public DrugDetailItemWriter(DrugDetailResponseRepository drugDetailResponseRepository) {
        this.drugDetailResponseRepository = drugDetailResponseRepository;
    }

    /**
     * write 메서드는 배치 작업에서 처리된 데이터 항목들을 데이터베이스에 저장합니다.
     * 이 메서드는 주어진 데이터 청크(Chunk)를 모두 저장하며, 저장 중 발생하는 예외를 처리합니다.
     *
     * @param items 배치 작업에서 처리된 DrugDetailResponse 객체들의 청크
     * @throws Exception 데이터 저장 중 오류가 발생할 경우 예외 발생
     */
    @Override
    public void write(Chunk<? extends DrugDetailResponse> items) throws Exception {
        try {
            // 레포지토리를 사용하여 DrugDetailResponse 항목들을 데이터베이스에 저장
            drugDetailResponseRepository.saveAll(items);
        } catch (Exception e) {
            // 데이터 저장 중 오류가 발생하면 예외를 던짐. 이 예외는 재시도와 스킵 처리를 트리거할 수 있음.
            throw new Exception("Error saving batch of DrugDetailResponse", e);
        }
    }
}
