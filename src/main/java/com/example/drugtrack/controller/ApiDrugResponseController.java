package com.example.drugtrack.controller;

import com.example.drugtrack.entity.ApiDrugResponse;
import com.example.drugtrack.service.ApiDrugResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drug-responses")
public class ApiDrugResponseController {

    @Autowired
    private ApiDrugResponseService service;

    @GetMapping
    public List<ApiDrugResponse> getAllResponses() {
        return service.getAllResponses();
    }

    @GetMapping("/{id}")
    public ApiDrugResponse getResponseById(@PathVariable Long id) {
        return service.getResponseById(id);
    }

    @PostMapping
    public ApiDrugResponse createResponse(@RequestBody ApiDrugResponse response) {
        return service.saveResponse(response);
    }

    @DeleteMapping("/{id}")
    public void deleteResponse(@PathVariable Long id) {
        service.deleteResponse(id);
    }
}
