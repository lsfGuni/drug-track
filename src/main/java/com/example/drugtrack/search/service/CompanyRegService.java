package com.example.drugtrack.search.service;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.search.repository.CompanyRegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyRegService {

    @Autowired
    private CompanyRegRepository repository;

    public List<ApiDrugResponse> getResponseByCompanyRegNumber(String startCompanyRegNumber) {
        return repository.findByStartCompanyRegNumber(startCompanyRegNumber);
    }
}
