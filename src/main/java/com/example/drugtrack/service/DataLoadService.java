package com.example.drugtrack.service;

import com.example.drugtrack.entity.FileDB;
import com.example.drugtrack.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataLoadService {

    @Autowired
    private FileDBRepository fileDBRepository;

    private Map<Long, FileDB> memoryCache = new ConcurrentHashMap<>();


    // 데이터베이스에서 데이터를 메모리로 로드하는 메서드
    public void loadDataToMemory() {
        List<FileDB> dataList = fileDBRepository.findAll();
        dataList.forEach(data -> memoryCache.put(data.getId(), data));
    }

    // 메모리에서 데이터 조회
    public FileDB getDataFromMemory(Long id) {
        return memoryCache.get(id);
    }

    // 메모리에서 모든 데이터 조회
    public List<FileDB> getAllDataFromMemory() {
        return List.copyOf(memoryCache.values());
    }
}
