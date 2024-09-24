package com.example.drugtrack.tracking.contorller;

import com.example.drugtrack.tracking.service.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 모든 도메인 허용

public class FileUploadController {

    private final CsvDataService csvDataService;  // CSV 데이터 관리 서비스
    private final ExcelDataService excelDataService;  // Excel 데이터 관리 서비스
    private final FileDBService fileDBService;
    private final FileDBBackService fileDBBackService;

    public FileUploadController(CsvDataService csvDataService, ExcelDataService excelDataService, FileDBService fileDBService
    ,FileDBBackService fileDBBackService) {
        this.csvDataService = csvDataService;
        this.excelDataService = excelDataService;
        this.fileDBService = fileDBService;
        this.fileDBBackService = fileDBBackService;

    }


    @PostMapping("/files-upload")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            MultipartFile file) {

        Map<String, String> response = new HashMap<>();

        // 파일이 비어 있는지 확인
        if (file.isEmpty()) {
            System.out.println("업로드된 파일이 비어 있습니다.");
            response.put("result", "N");
            response.put("msg", "파일이 비어 있습니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // 파일의 확장자 추출
        String filename = file.getOriginalFilename();
        System.out.println("업로드된 파일 이름: " + filename);

        if (filename == null || (!filename.endsWith(".csv") && !filename.endsWith(".xlsx"))) {
            System.out.println("지원되지 않는 파일 형식: " + filename);
            response.put("result", "N");
            response.put("msg", "지원되지 않는 파일 형식입니다. CSV 또는 XLSX 파일만 가능합니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // CSV 파일 처리
            if (filename.endsWith(".csv")) {
                List<String[]> csvData = parseCsvFile(file);
                System.out.println("CSV 파일이 성공적으로 처리되었습니다. 파싱된 데이터: ");
                csvData.forEach(row -> System.out.println(String.join(", ", row)));  // CSV 데이터를 콘솔에 출력

                csvDataService.storeCsvData(csvData);  // CSV 데이터를 서비스에 저장
                response.put("result", "Y");
                response.put("msg", "CSV 파일이 성공적으로 처리되었습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);

                // XLSX 파일 처리
            } else if (filename.endsWith(".xlsx")) {
                List<List<String>> excelData = parseExcelFile(file);
                System.out.println("XLSX 파일이 성공적으로 처리되었습니다. 파싱된 데이터:");
                excelData.forEach(row -> System.out.println(String.join(", ", row)));  // 엑셀 데이터를 콘솔에 출력

                // 파싱한 엑셀 데이터를 메모리에 저장
                excelDataService.storeExcelData(excelData);

                response.put("result", "Y");
                response.put("msg", "XLSX 파일이 성공적으로 처리되었습니다.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.put("result", "N");
            response.put("msg", "파일 처리 중 오류가 발생했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("result", "N");
        response.put("msg", "파일 처리에 실패했습니다.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // CSV 파일 파싱 로직
    private List<String[]> parseCsvFile(MultipartFile file) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");  // 콤마로 구분하여 CSV 데이터를 분리
                data.add(rowData);
            }
        }
        return data;  // 메모리에 담긴 CSV 데이터를 반환
    }

    // XLSX 파일 파싱 로직
    private List<List<String>> parseExcelFile(MultipartFile file) throws IOException {
        List<List<String>> data = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);  // 첫 번째 시트를 가져옴

        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(getCellValueAsString(cell));  // 셀 데이터를 문자열로 변환
            }
            data.add(rowData);
        }
        workbook.close();
        return data;  // 메모리에 담긴 엑셀 데이터를 반환
    }

    // 엑셀 셀 데이터를 문자열로 변환하는 유틸리티 메서드
    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    // CSV 데이터를 확인할 수 있는 API 엔드포인트
    @GetMapping("/csv-data")
    public ResponseEntity<List<String[]>> getCsvData() {
        List<String[]> csvData = csvDataService.getCsvData();  // 서비스에서 CSV 데이터 가져옴
        if (csvData == null || csvData.isEmpty()) {
            System.out.println("메모리에 저장된 CSV 데이터가 없습니다.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 데이터가 없는 경우 처리
        }
        System.out.println("메모리에 저장된 CSV 데이터 조회:");
        csvData.forEach(row -> System.out.println(String.join(", ", row)));  // 조회된 CSV 데이터를 콘솔에 출력
        return new ResponseEntity<>(csvData, HttpStatus.OK);  // 메모리에 있는 데이터를 반환
    }

    // 메모리에 저장된 엑셀 데이터를 확인하는 엔드포인트
    @GetMapping("/excel-data")
    public ResponseEntity<List<List<String>>> getExcelData() {
        List<List<String>> excelData = excelDataService.getExcelData();  // 서비스에서 데이터 가져옴
        if (excelData == null || excelData.isEmpty()) {
            System.out.println("메모리에 저장된 xlsx 데이터가 없습니다.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 데이터가 없는 경우
        }
        System.out.println("메모리에 저장된 엑셀 데이터 조회:");
        excelData.forEach(row -> System.out.println(String.join(", ", row)));  // 조회된 엑셀 데이터를 콘솔에 출력
        return new ResponseEntity<>(excelData, HttpStatus.OK);  // 메모리에 저장된 엑셀 데이터를 반환
    }

    // CSV 데이터를 데이터베이스에 저장하는 엔드포인트
    @PostMapping("/save-csv-data")
    public ResponseEntity<String> saveCsvDataToDB() {

        var csvData = csvDataService.getCsvData();  // 메모리에 저장된 CSV 데이터를 가져옴
        if (csvData == null || csvData.isEmpty()) {
            return new ResponseEntity<>("메모리에 저장된 CSV 데이터가 없습니다.", HttpStatus.NO_CONTENT);
        }

        // 데이터를 데이터베이스에 저장
        fileDBService.saveCsvDataToDB(csvData);
        return new ResponseEntity<>("CSV 데이터를 데이터베이스에 성공적으로 저장했습니다.", HttpStatus.OK);
    }

    // XLSX 데이터를 데이터베이스에 저장하는 엔드포인트
    @PostMapping("/save-excel-data")
    public ResponseEntity<String> saveExcelDataToDB() {
        var excelData = excelDataService.getExcelData();  // 메모리에 저장된 XLSX 데이터를 가져옴
        if (excelData == null || excelData.isEmpty()) {
            return new ResponseEntity<>("메모리에 저장된 엑셀 데이터가 없습니다.", HttpStatus.NO_CONTENT);
        }

        // 데이터를 데이터베이스에 저장
        fileDBService.saveExcelDataToDB(excelData);
        return new ResponseEntity<>("엑셀 데이터를 데이터베이스에 성공적으로 저장했습니다.", HttpStatus.OK);
    }

    @Autowired
    private CSVUploadClientService csvUploadClientService;


    @GetMapping("/upload-file-front")
    public ResponseEntity<String> uploadFileFromClient() {
        String response = csvUploadClientService.uploadCSVFile();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/files-save")
    public ResponseEntity<Map<String, String>> uploadCSVFile(@RequestParam("file") MultipartFile file) {

        Map<String, String> response = new HashMap<>();

        try {
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

            // 서비스 호출하여 CSV 데이터를 저장
            fileDBBackService.saveCsvDataToDB(csvData);

            // 성공 시 반환할 메시지
            response.put("result", "Y");
            response.put("msg", "데이터 업로드 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 실패 시 반환할 메시지
            response.put("result", "N");
            response.put("msg", "에러발생: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
