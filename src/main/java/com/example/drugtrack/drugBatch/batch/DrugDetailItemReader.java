package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import com.example.drugtrack.drugBatch.service.DrugDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * DrugDetailItemReader는 Spring Batch에서 의약품 데이터를 읽어오는 역할을 하는 Reader 클래스입니다.
 * 외부 API로부터 데이터를 페이지 단위로 불러오며, 읽은 데이터를 배치 프로세싱 단계에 전달합니다.
 */
@Component
public class DrugDetailItemReader implements ItemReader<DrugDetailResponse> {

    private final DrugDetailService drugDetailService; // 의약품 데이터를 가져오는 서비스
    private int currentPage = 1; // 현재 읽고 있는 페이지 번호
    private final int pageSize = 100; // 페이지당 데이터 개수
    private int totalPages = 0; // 총 페이지 수
    private List<DrugDetailResponse> currentBatch = new ArrayList<>(); // 현재 배치에 해당하는 데이터 목록

    /**
     * 생성자 주입을 통해 DrugDetailService를 주입받습니다.
     * DrugDetailService는 API 호출을 통해 데이터를 가져오는 역할을 담당합니다.
     *
     * @param drugDetailService 의약품 데이터를 가져오는 서비스
     */
    public DrugDetailItemReader(DrugDetailService drugDetailService) {
        this.drugDetailService = drugDetailService;
    }

    /**
     * read 메서드는 배치 프로세스에서 호출되어 데이터를 하나씩 반환합니다.
     * 데이터가 없으면 null을 반환하여 배치 작업이 완료되었음을 알립니다.
     *
     * @return DrugDetailResponse 객체 또는 데이터가 없을 경우 null
     * @throws Exception 데이터 읽기 중 오류가 발생할 경우 예외 발생
     */
    @Override
    public DrugDetailResponse read() throws Exception {
        // 배치 작업이 시작될 때마다 총 페이지 수를 초기화
        if (currentPage == 1) {
            initializeTotalPages();  // 매번 배치 시작할 때 페이지 수를 다시 계산
        }

        // 현재 배치가 비어 있고, 아직 모든 페이지를 읽지 않은 경우
        if (currentBatch.isEmpty() && currentPage <= totalPages) {
            try {
                // API로부터 데이터를 가져와 currentBatch에 저장
                currentBatch = drugDetailService.getDrugInfoPage(currentPage, pageSize).get();
                currentPage++;
            } catch (Exception e) {
                // 데이터 페칭 중 오류가 발생할 경우
                System.err.println("Error fetching page " + currentPage + ": " + e.getMessage());
                currentBatch = new ArrayList<>();  // 오류 발생 시 빈 리스트로 처리
                Logger logger = LoggerFactory.getLogger(DrugDetailItemReader.class);
                logger.error("Error fetching data for page {}: {}", currentPage, e.getMessage());
                return null;
            }
        }

        // 읽을 데이터가 없으면 null을 반환하여 작업이 종료됨을 알림
        return currentBatch.isEmpty() ? null : currentBatch.remove(0); // 데이터가 있으면 하나씩 반환
    }


    /**
     * initializeTotalPages 메서드는 배치 작업이 시작될 때 API로부터 데이터를 가져올 총 페이지 수를 계산합니다.
     */
    public void initializeTotalPages() {
        int totalCount = drugDetailService.getTotalCount(); // 전체 데이터 개수를 가져옴
        totalPages = (int) Math.ceil((double) totalCount / pageSize);  // 총 페이지 수 계산
    }
}
