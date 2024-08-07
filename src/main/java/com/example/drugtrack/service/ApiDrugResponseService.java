package com.example.drugtrack.service;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.repository.ApiDrugResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiDrugResponseService {

    @Autowired
    private ApiDrugResponseRepository repository;

    public List<ApiDrugResponse> getAllResponses() {
        return repository.findAll();
    }

    public ApiDrugResponse getResponseById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiDrugResponse saveResponse(ApiDrugResponse response) {
        return repository.save(response);
    }

    public void deleteResponse(Long id) {
        repository.deleteById(id);
    }
}
