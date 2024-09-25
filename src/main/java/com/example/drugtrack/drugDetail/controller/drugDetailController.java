package com.example.drugtrack.drugDetail.controller;

import com.example.drugtrack.drugDetail.service.DrugDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class drugDetailController {

    @Autowired
    private DrugDetailService drugDetailService;

    @GetMapping("/fetch-and-save")
    public String fetchAndSave() {
        drugDetailService.fetchAndSaveApiResponse();
        return "Data fetch and save process started.";
    }
}
