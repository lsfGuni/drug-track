package com.example.drugtrack.drugBatch.batch;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DrugDetailItemProcessor implements ItemProcessor<DrugDetailResponse, DrugDetailResponse> {

    @Override
    public DrugDetailResponse process(DrugDetailResponse item) throws Exception {
        // 필요시 추가적인 가공 로직 작성
        return item;
    }
}
