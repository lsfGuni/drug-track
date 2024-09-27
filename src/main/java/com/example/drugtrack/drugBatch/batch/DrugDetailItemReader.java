package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import com.example.drugtrack.drugBatch.service.DrugDetailService;
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
        // 배치 작업이 시작될 때마다 총 페이지 수를 초기화
        if (currentPage == 1) {
            initializeTotalPages();  // 매번 배치 시작할 때 페이지 수를 다시 계산
        }

        // 데이터 읽어오기 로직
        if (currentBatch.isEmpty() && currentPage <= totalPages) {
            try {
                currentBatch = drugDetailService.getDrugInfoPage(currentPage, pageSize).get();
                currentPage++;
            } catch (Exception e) {
                System.err.println("Error fetching page " + currentPage + ": " + e.getMessage());
                currentBatch = new ArrayList<>();  // 빈 리스트로 처리
                Logger logger = LoggerFactory.getLogger(DrugDetailItemReader.class);
                logger.error("Error fetching data for page {}: {}", currentPage, e.getMessage());
                return null;
            }
        }

        return currentBatch.isEmpty() ? null : currentBatch.remove(0);
    }

    public void initializeTotalPages() {
        int totalCount = drugDetailService.getTotalCount();
        totalPages = (int) Math.ceil((double) totalCount / pageSize);
    }
}
