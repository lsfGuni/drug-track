package com.example.drugtrack.tracking.service;

import com.example.drugtrack.tracking.entity.FileDB;
import com.example.drugtrack.tracking.repository.FileDBRepository;
import com.opencsv.CSVWriter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

@Service
public class CSVUploadClientService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public String uploadCSVFile() {
        String serverUrl = "http://localhost:8082/api/files-save-db";

        try {

            List<FileDB> fileDBList = fileDBRepository.findAll();

            // Create a temporary CSV file
            File csvFile = File.createTempFile("Temp", ".csv");

            // Write data to the CSV file
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
                // Write CSV headers (example, adjust based on your DB columns)
                String[] header = {
                        "거래처명", "거래처사업장등록번호", "출고일자", "제품명",
                        "GTIN14", "제조번호", "유효일자", "시리얼 번호", "바코드 데이터", "Aggregation 정보"
                };
                writer.writeNext(header);

                // Write data rows, similar to your saveCsvDataToDB method
                for (FileDB fileDB : fileDBList) {
                    String[] data = {
                            fileDB.getStartCompanyName(),
                            fileDB.getStartCompanyRegNumber(),
                            fileDB.getDeliveryDate(),
                            fileDB.getProductName(),
                            fileDB.getGs1Code(),
                            fileDB.getMfNumber(),
                            fileDB.getExpDate(),
                            fileDB.getSerialNumbers(),
                            fileDB.getBarcodeData(),
                            fileDB.getAggData()
                    };
                    writer.writeNext(data);
                }
            }

            // Upload the CSV file to the server
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost uploadFile = new HttpPost(serverUrl);

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addBinaryBody(
                        "file",
                        csvFile,
                        ContentType.APPLICATION_OCTET_STREAM,
                        csvFile.getName()
                );

                HttpEntity multipart = builder.build();
                uploadFile.setEntity(multipart);

                HttpResponse response = httpClient.execute(uploadFile);
                HttpEntity responseEntity = response.getEntity();

                if (responseEntity != null) {
                    return EntityUtils.toString(responseEntity);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading file: " + e.getMessage();
        }
        return "No response from server";
    }
}
