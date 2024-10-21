package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
/**
 * DrugDetailItemProcessor는 Spring Batch에서 아이템을 처리하는 단계인 Processor 역할을 수행하는 클래스입니다.
 * 이 클래스는 읽어들인 데이터를 가공하거나 변환하는 로직을 추가할 수 있으며,
 * 배치 작업에서 데이터를 처리한 후 다음 단계로 전달합니다.
 */
@Component
public class DrugDetailItemProcessor implements ItemProcessor<DrugDetailResponse, DrugDetailResponse> {

    /**
     * process 메서드는 읽어들인 데이터를 가공하는 로직을 처리합니다.
     * 현재는 가공 없이 데이터를 그대로 반환하고 있으며, 필요 시 추가적인 데이터 변환 또는 필터링 로직을 구현할 수 있습니다.
     *
     * @param item 읽어들인 DrugDetailResponse 객체
     * @return 가공된 DrugDetailResponse 객체
     * @throws Exception 처리 중 오류가 발생할 경우 발생하는 예외
     */
    @Override
    public DrugDetailResponse process(DrugDetailResponse item) throws Exception {
        // 필요시 추가적인 가공 로직 작성
        return item;  // 가공 없이 원본 데이터를 그대로 반환
    }
}
