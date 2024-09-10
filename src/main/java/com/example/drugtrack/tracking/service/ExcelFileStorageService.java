package com.example.drugtrack.tracking.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ExcelFileStorageService {

    private final String storageDirectory = "C:/Users/ibiz/Desktop/excel-files/";

    public boolean storeFile(MultipartFile file) {
        try {
            // 파일이 저장될 디렉토리가 존재하지 않으면 생성
            Path directoryPath = Paths.get(storageDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // 파일 저장
            Path filePath = directoryPath.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
