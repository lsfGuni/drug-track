package com.example.drugtrack.security.repository;

import com.example.drugtrack.security.entity.TermsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * TermsInfoRepository 인터페이스는 TermsInfo 엔티티에 대한 데이터베이스 작업을 처리합니다.
 * JpaRepository를 확장하여 CRUD 및 기타 데이터베이스 관련 작업을 수행합니다.
 */
public interface TermsInfoRepository extends JpaRepository<TermsInfo, Long> {
}
