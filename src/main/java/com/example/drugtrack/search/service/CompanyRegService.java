package com.example.drugtrack.search.service;

import com.example.drugtrack.search.repository.CompanyRegRepository;
import com.example.drugtrack.tracking.entity.ApiDrugResponse;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * CompanyRegService는 회사의 사업자 등록번호를 기준으로 의약품 데이터를 조회하는 서비스 클래스입니다.
 * CompanyRegRepository를 사용하여 데이터베이스에서 관련 데이터를 검색합니다.
 */
@Service
public class CompanyRegService {

    // CompanyRegRepository 의존성 주입
    private final CompanyRegRepository companyRegRepository;

    /**
     * CompanyRegService 생성자.
     * CompanyRegRepository 객체를 주입받아 초기화합니다.
     *
     * @param companyRegRepository 의존성 주입을 위한 CompanyRegRepository 객체
     */
    public CompanyRegService(CompanyRegRepository companyRegRepository) {
        this.companyRegRepository = companyRegRepository;
    }

    /**
     * 사업자 등록번호를 기반으로 의약품 데이터를 조회하는 메서드.
     * 주어진 사업자 등록번호를 가진 ApiDrugResponse 데이터를 CompanyRegRepository에서 가져옵니다.
     *
     * @param startCompanyRegNumber 검색하려는 회사의 사업자 등록번호
     * @return 해당 사업자 등록번호에 속한 ApiDrugResponse 객체 리스트
     */
    public List<ApiDrugResponse> getResponseByCompanyRegNumber(String startCompanyRegNumber) {
        return companyRegRepository.findByStartCompanyRegNumber(startCompanyRegNumber);
    }
}
