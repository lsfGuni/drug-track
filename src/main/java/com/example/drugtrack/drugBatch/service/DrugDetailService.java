package com.example.drugtrack.drugBatch.service;

import com.example.drugtrack.drugBatch.entity.DrugDetailResponse;
import com.example.drugtrack.drugBatch.repository.DrugDetailResponseRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class DrugDetailService {

    private static final Logger logger = LoggerFactory.getLogger(DrugDetailService.class);

    @Autowired
    private DrugDetailResponseRepository drugDetailResponseRepository;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String serviceKey;

    @Value("${api.type}")
    private String responseType;

    private final RestTemplate restTemplate = new RestTemplate();

    private int cachedTotalCount = -1;

    public int getTotalCount() {
        if (cachedTotalCount != -1) {
            return cachedTotalCount;
        }

        String url = String.format("%s?serviceKey=%s&type=%s&pageNo=1&numOfRows=1", apiUrl, serviceKey, responseType);
        String response = restTemplate.getForObject(url, String.class);
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject body = jsonResponse.getJSONObject("body");
            cachedTotalCount = body.getInt("totalCount");
            return cachedTotalCount;
        } catch (JSONException e) {
            logger.error("Error parsing JSON response for totalCount: {}", e.getMessage());
            return 0;
        }
    }


    private void setApiResponseFields(DrugDetailResponse apiResponse, DrugDetailResponse item) {
        apiResponse.setItemSeq(item.getItemSeq());
        apiResponse.setItemName(item.getItemName());
        apiResponse.setEntpName(item.getEntpName());
        apiResponse.setItemPermitDate(item.getItemPermitDate());
        apiResponse.setCnsngManuf(item.getCnsngManuf());
        apiResponse.setEtcOtcCode(item.getEtcOtcCode());
        apiResponse.setChart(item.getChart());
        apiResponse.setBarCode(item.getBarCode());
        apiResponse.setMaterialName(item.getMaterialName());
        apiResponse.setEeDocId(item.getEeDocId());
        apiResponse.setUdDocId(item.getUdDocId());
        apiResponse.setNbDocId(item.getNbDocId());
        apiResponse.setInsertFile(item.getInsertFile());
        apiResponse.setStorageMethod(item.getStorageMethod());
        apiResponse.setValidTerm(item.getValidTerm());
        apiResponse.setReexamTarget(item.getReexamTarget());
        apiResponse.setReexamDate(item.getReexamDate());
        apiResponse.setPackUnit(item.getPackUnit());
        apiResponse.setEdiCode(item.getEdiCode());
        apiResponse.setDocText(item.getDocText());
        apiResponse.setPermitKindName(item.getPermitKindName());
        apiResponse.setEntpNo(item.getEntpNo());
        apiResponse.setMakeMaterialFlag(item.getMakeMaterialFlag());
        apiResponse.setNewdrugClassName(item.getNewdrugClassName());
        apiResponse.setIndutyType(item.getIndutyType());
        apiResponse.setCancelDate(item.getCancelDate());
        apiResponse.setCancelName(item.getCancelName());
        apiResponse.setChangeDate(item.getChangeDate());
        apiResponse.setNarcoticKindCode(item.getNarcoticKindCode());
        apiResponse.setGbnName(item.getGbnName());
        apiResponse.setTotalContent(item.getTotalContent());
        apiResponse.setEeDocData(truncateString(removeXmlTags(item.getEeDocData()), 3000));
        apiResponse.setUdDocData(truncateString(removeXmlTags(item.getUdDocData()), 3000));
        apiResponse.setNbDocData(truncateString(removeXmlTags(item.getNbDocData()), 3000));
        apiResponse.setPnDocData(truncateString(removeXmlTags(item.getPnDocData()), 3000));
        apiResponse.setMainItemIngr(item.getMainItemIngr());
        apiResponse.setIngrName(item.getIngrName());
        apiResponse.setAtcCode(item.getAtcCode());
        apiResponse.setItemEngName(item.getItemEngName());
        apiResponse.setEntpEngName(item.getEntpEngName());
        apiResponse.setMainIngrEng(item.getMainIngrEng());
        apiResponse.setBizrno(item.getBizrno());
    }


    @Async
    public CompletableFuture<List<DrugDetailResponse>> getDrugInfoPage(int pageNo, int pageSize) {
        String url = String.format("%s?serviceKey=%s&type=%s&pageNo=%d&numOfRows=%d", apiUrl, serviceKey, responseType, pageNo, pageSize);
        List<DrugDetailResponse> itemsList = new ArrayList<>();
        String response = restTemplate.getForObject(url, String.class);
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject body = jsonResponse.getJSONObject("body");
            JSONArray items = body.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject itemJson = items.getJSONObject(i);
                DrugDetailResponse apiResponse = new DrugDetailResponse();

                // JSONObject로부터 값을 추출하여 ApiResponse 객체에 설정
                apiResponse.setItemSeq(itemJson.optString("ITEM_SEQ"));
                apiResponse.setItemName(itemJson.optString("ITEM_NAME"));
                apiResponse.setEntpName(itemJson.optString("ENTP_NAME"));
                apiResponse.setItemPermitDate(itemJson.optString("ITEM_PERMIT_DATE"));
                apiResponse.setCnsngManuf(itemJson.optString("CNSGN_MANUF"));
                apiResponse.setEtcOtcCode(itemJson.optString("ETC_OTC_CODE"));
                apiResponse.setChart(itemJson.optString("CHART"));
                apiResponse.setBarCode(itemJson.optString("BAR_CODE"));
                apiResponse.setMaterialName(itemJson.optString("MATERIAL_NAME"));
                apiResponse.setEeDocId(itemJson.optString("EE_DOC_ID"));
                apiResponse.setUdDocId(itemJson.optString("UD_DOC_ID"));
                apiResponse.setNbDocId(itemJson.optString("NB_DOC_ID"));
                apiResponse.setInsertFile(itemJson.optString("INSERT_FILE"));
                apiResponse.setStorageMethod(itemJson.optString("STORAGE_METHOD"));
                apiResponse.setValidTerm(itemJson.optString("VALID_TERM"));
                apiResponse.setReexamTarget(itemJson.optString("REEXAM_TARGET"));
                apiResponse.setReexamDate(itemJson.optString("REEXAM_DATE"));
                apiResponse.setPackUnit(itemJson.optString("PACK_UNIT"));
                apiResponse.setEdiCode(itemJson.optString("EDI_CODE"));
                apiResponse.setDocText(itemJson.optString("DOC_TEXT"));
                apiResponse.setPermitKindName(itemJson.optString("PERMIT_KIND_NAME"));
                apiResponse.setEntpNo(itemJson.optString("ENTP_NO"));
                apiResponse.setMakeMaterialFlag(itemJson.optString("MAKE_MATERIAL_FLAG"));
                apiResponse.setNewdrugClassName(itemJson.optString("NEWDRUG_CLASS_NAME"));
                apiResponse.setIndutyType(itemJson.optString("INDUTY_TYPE"));
                apiResponse.setCancelDate(itemJson.optString("CANCEL_DATE"));
                apiResponse.setCancelName(itemJson.optString("CANCEL_NAME"));
                apiResponse.setChangeDate(itemJson.optString("CHANGE_DATE"));
                apiResponse.setNarcoticKindCode(itemJson.optString("NARCOTIC_KIND_CODE"));
                apiResponse.setGbnName(itemJson.optString("GBN_NAME"));
                apiResponse.setTotalContent(itemJson.optString("TOTAL_CONTENT"));
                apiResponse.setEeDocData(truncateString(removeXmlTags(itemJson.optString("EE_DOC_DATA")), 3000));
                apiResponse.setUdDocData(truncateString(removeXmlTags(itemJson.optString("UD_DOC_DATA")), 3000));
                apiResponse.setNbDocData(truncateString(removeXmlTags(itemJson.optString("NB_DOC_DATA")), 3000));
                apiResponse.setPnDocData(truncateString(removeXmlTags(itemJson.optString("PN_DOC_DATA")), 3000));
                apiResponse.setMainItemIngr(itemJson.optString("MAIN_ITEM_INGR"));
                apiResponse.setIngrName(itemJson.optString("INGR_NAME"));
                apiResponse.setAtcCode(itemJson.optString("ATC_CODE"));
                apiResponse.setItemEngName(itemJson.optString("ITEM_ENG_NAME"));
                apiResponse.setEntpEngName(itemJson.optString("ENTP_ENG_NAME"));
                apiResponse.setMainIngrEng(itemJson.optString("MAIN_INGR_ENG"));
                apiResponse.setBizrno(itemJson.optString("BIZRNO"));

                // 완성된 ApiResponse 객체를 리스트에 추가
                itemsList.add(apiResponse);
            }
        } catch (JSONException e) {
            logger.error("Error parsing JSON response for page {}: {}", pageNo, e.getMessage());
        }
        return CompletableFuture.completedFuture(itemsList);
    }



    public void fetchAndSaveApiResponse() {
        long startTime = System.currentTimeMillis();
        logger.info("Starting to fetch and save API response...");

        int totalCount = getTotalCount();
        int pageSize = 100;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<CompletableFuture<List<DrugDetailResponse>>> futures = new ArrayList<>();
        for (int pageNo = 1; pageNo <= totalPages; pageNo++) {
            logger.info("Fetching page {}/{}", pageNo, totalPages);
            futures.add(getDrugInfoPage(pageNo, pageSize));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<DrugDetailResponse> allItems = new ArrayList<>();
        for (CompletableFuture<List<DrugDetailResponse>> future : futures) {
            try {
                allItems.addAll(future.get());
            } catch (Exception e) {
                logger.error("Error retrieving data from future: {}", e.getMessage());
            }
        }

        int batchSize = 500;
        for (int i = 0; i < allItems.size(); i += batchSize) {
            int end = Math.min(i + batchSize, allItems.size());
            List<DrugDetailResponse> batchList = allItems.subList(i, end);
            saveOrUpdateBatch(batchList);
            logger.info("Saved batch from index {} to {}", i, end);
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        logger.info("Total records fetched and saved: {}", allItems.size());
        logger.info("Time taken: {} seconds", duration);
    }

    private void saveOrUpdateBatch(List<DrugDetailResponse> batchList) {
        for (DrugDetailResponse newItem : batchList) {
            Optional<DrugDetailResponse> existingItemOptional = drugDetailResponseRepository.findByItemSeq(newItem.getItemSeq());
            if (existingItemOptional.isPresent()) {
                DrugDetailResponse existingItem = existingItemOptional.get();
                updateExistingItem(existingItem, newItem);
                drugDetailResponseRepository.save(existingItem);
            } else {
                drugDetailResponseRepository.save(newItem);
            }
        }
    }
    private void updateExistingItem(DrugDetailResponse existingItem, DrugDetailResponse newItem) {
        setApiResponseFields(existingItem, newItem);
    }


    private String removeXmlTags(String xml) {
        if (xml == null || xml.isEmpty()) {
            return xml;
        }

        Document doc = Jsoup.parse(xml);
        Element body = doc.body();
        return body.text();
    }

    private String truncateString(String str, int byteLimit) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        byte[] strBytes = str.getBytes();
        if (strBytes.length <= byteLimit) {
            return str;
        }

        int endIndex = 0;
        int byteCount = 0;

        for (int i = 0; i < str.length(); i++) {
            byteCount += String.valueOf(str.charAt(i)).getBytes().length;
            if (byteCount > byteLimit) {
                break;
            }
            endIndex = i + 1;
        }

        return str.substring(0, endIndex);
    }
}
