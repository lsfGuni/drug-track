package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import com.example.drugtrack.drugBatch.service.DrugDetailService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DrugDetailItemReader implements ItemReader<DrugDetailResponse> {

    private final DrugDetailService drugDetailService;
    private int currentPage = 1;
    private final int pageSize = 100;
    private int totalPages = 0;
    private List<DrugDetailResponse> currentBatch = new ArrayList<>();

    @Autowired
    public DrugDetailItemReader(DrugDetailService drugDetailService) {
        this.drugDetailService = drugDetailService;
    }

    @Override
    public DrugDetailResponse read() throws Exception {
        // 배치 작업에서 currentBatch가 비었을 때 새로 데이터를 읽어옴
        if (currentBatch.isEmpty() && currentPage <= totalPages) {
            try {
                // 예외 발생 시 이를 잡아서 처리할 수 있음
                currentBatch = drugDetailService.getDrugInfoPage(currentPage, pageSize).get();
                currentPage++;
            } catch (Exception e) {
                // 예외 발생 시 처리 로직 (로그 기록 등)
                System.err.println("Error fetching page " + currentPage + ": " + e.getMessage());
                currentBatch = new ArrayList<>();  // 빈 리스트로 처리
                // 로그 남기기
                Logger logger = LoggerFactory.getLogger(DrugDetailItemReader.class);
                logger.error("Error fetching data for page {}: {}", currentPage, e.getMessage());
                // 예외 발생 시 null 반환하여 배치 중단 방지
                return null;
            }
        }

        return currentBatch.isEmpty() ? null : currentBatch.remove(0);
    }

    @PostConstruct
    public void initializeTotalPages() {
        int totalCount = drugDetailService.getTotalCount();
        totalPages = (int) Math.ceil((double) totalCount / pageSize);
    }
}
