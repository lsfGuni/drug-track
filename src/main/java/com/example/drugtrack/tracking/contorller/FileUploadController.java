package com.example.drugtrack.tracking.contorller;

import com.example.drugtrack.tracking.entity.FilesSave;
import com.example.drugtrack.tracking.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileUploadController는 CSV 파일을 업로드하고 해당 데이터를 데이터베이스와 블록체인에 저장하는 기능을 제공합니다.
 * API 요청을 처리하고, 파일 검증, CSV 파싱, 데이터 저장 및 블록체인 연동을 수행합니다.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 모든 도메인에서 접근을 허용
@Tag(name = "csv 출고데이터 저장 api", description = "csv 파일 출고데이터를 DB및 블록체인에 저장하는 API")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);  // 로깅 설정


    private final FilesSaveService filesSaveService;
    private final BlockchainService blockchainService;

    /**
     * FilesSaveService 및 BlockchainService를 주입받아 컨트롤러를 초기화합니다.
     *
     * @param filesSaveService CSV 데이터를 처리하는 서비스
     * @param blockchainService 블록체인에 데이터를 저장하는 서비스
     */
    public FileUploadController(FilesSaveService filesSaveService, BlockchainService blockchainService) {
        this.filesSaveService = filesSaveService;
        this.blockchainService = blockchainService;
    }

    /**
     * API의 상태를 확인하는 health check 엔드포인트입니다.
     * API가 정상적으로 동작하고 있는지 확인하는 용도로 사용됩니다.
     *
     * @return "API is running and communication is healthy" 메시지를 반환
     */
    @Operation(summary = "api health check", description = "api health check")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("API is running and communication is healthy", HttpStatus.OK);
    }


    /**
     * CSV 파일을 업로드하고, 해당 데이터를 데이터베이스와 블록체인에 저장하는 엔드포인트입니다.
     *
     * @param file 업로드된 CSV 파일
     * @param apiKey API 키 (요청 시 함께 전송)
     * @return CSV 데이터의 저장 상태에 대한 결과 메시지를 반환
     */
    @Operation(summary = "CSV 파일 업로드 요청", description = "CSV 파일을 업로드하고 데이터를 저장합니다.")
    @PostMapping("/files-save")
    public ResponseEntity<Map<String, String>> uploadCSVFile(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("api_key") String apiKey) {
        Map<String, String> response = new HashMap<>();
        try {
            // API 호출 로그
            logger.info("/files-save API 호출됨. 파일명: {}, 파일 크기: {} bytes", file.getOriginalFilename(), file.getSize());

            // 파일이 비어 있는지 확인
            if (file.isEmpty() || file.getSize() == 0) {
                logger.warn("업로드된 파일이 비어 있습니다.");
                response.put("result", "N");
                response.put("msg", "파일이 비어 있습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // CSV 데이터 파싱
            List<String[]> csvData = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
                CSVParser csvParser = CSVFormat.DEFAULT.parse(reader);
                // CSV 레코드를 순회하며 파싱
                for (CSVRecord csvRecord : csvParser) {
                    String[] rowData = new String[csvRecord.size()];
                    for (int i = 0; i < csvRecord.size(); i++) {
                        rowData[i] = csvRecord.get(i);
                    }
                    csvData.add(rowData);
                }
            }

            // 파싱 완료 로그
            logger.info("CSV 데이터 파싱 완료. 총 {}개의 레코드가 파싱되었습니다.", csvData.size());

            // 서비스 호출하여 CSV 데이터를 저장
            List<FilesSave> savedEntities = filesSaveService.saveCsvDataToDB(csvData, apiKey);

            // 블록체인에 각 레코드의 seq와 hashCode 저장
            List<Map<String, Object>> dataForBlockchain = new ArrayList<>();
            for (FilesSave savedEntity : savedEntities) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("seq", savedEntity.getSeq());
                entry.put("hashcode", savedEntity.getHashCode());
                dataForBlockchain.add(entry);
            }

            // 블록체인 서비스 호출
            blockchainService.storeBulkDataOnBlockchain(dataForBlockchain);

            // 데이터 저장 완료 로그
            logger.info("CSV 데이터 저장 완료.");

            // 성공 시 반환할 메시지
            response.put("result", "Y");
            response.put("msg", "데이터 업로드 완료");

            // 응답 로그
            logger.info("API 응답: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 에러 로그
            logger.error("파일 업로드 중 에러 발생: ", e);

            // 실패 시 반환할 메시지
            response.put("result", "N");
            response.put("msg", "에러발생: " + e.getMessage());

            // 응답 로그
            logger.info("API 응답: {}", response);

            return ResponseEntity.status(500).body(response);
        }
    }
}

