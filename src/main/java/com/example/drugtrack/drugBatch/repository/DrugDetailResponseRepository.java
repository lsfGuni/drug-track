package com.example.drugtrack.drugBatch.repository;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * DrugDetailResponseRepository는 DrugDetailResponse 엔티티와 상호작용하는 JPA 레포지토리입니다.
 * 기본적인 CRUD 작업을 처리하며, 추가로 의약품 일련번호(itemSeq)를 기준으로 데이터를 조회하는 메서드를 제공합니다.
 */
public interface DrugDetailResponseRepository extends JpaRepository<DrugDetailResponse, Long> {

    /**
     * 의약품 일련번호(itemSeq)를 기준으로 DrugDetailResponse 엔티티를 조회합니다.
     *
     * @param itemSeq 의약품 일련번호
     * @return 의약품 일련번호와 일치하는 DrugDetailResponse 엔티티를 Optional로 반환
     */
    Optional<DrugDetailResponse> findByItemSeq(String itemSeq);
}
