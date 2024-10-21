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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
/**
 * DrugDetailService는 외부 API에서 의약품 데이터를 가져오고 이를 데이터베이스에 저장하는 서비스 클래스입니다.
 * API 응답을 페이지 단위로 가져와 비동기적으로 처리하고, 데이터를 저장 또는 업데이트합니다.
 */
@Service
public class DrugDetailService {

    private static final Logger logger = LoggerFactory.getLogger(DrugDetailService.class);

    private final DrugDetailResponseRepository drugDetailResponseRepository;

    @Value("${api.url}")
    private String apiUrl;  // 의약품공공API 식품의약품안전처_의약품 제품 허가정보 URL

    @Value("${api.serviceKey}")
    private String serviceKey;  // API 인증 키

    @Value("${api.type}")
    private String responseType; // API 응답 타입

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 인스턴스 생성

    private int cachedTotalCount = -1;  // API에서 가져온 총 레코드 수를 캐시

    /**
    * 레포지토리 인터페이스 주입 생성자
    *
     */
    public DrugDetailService(DrugDetailResponseRepository drugDetailResponseRepository) {
        this.drugDetailResponseRepository = drugDetailResponseRepository;
    }

    /**
     * API에서 전체 의약품 데이터의 총 개수를 가져옵니다.
     * 캐시된 값이 있으면 캐시된 값을 반환하고, 없을 경우 API 호출을 통해 값을 가져옵니다.
     *
     * @return 총 의약품 데이터 개수
     */
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

    /**
     * API 응답 필드 값을 새로운 엔티티에 설정합니다.
     *
     * @param apiResponse 새로운 엔티티
     * @param item 기존 데이터에서 가져온 엔티티
     */
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

    /**
     * 비동기로 특정 페이지에 해당하는 의약품 데이터를 API로부터 가져옵니다.
     *
     * @param pageNo 요청할 페이지 번호
     * @param pageSize 페이지 당 레코드 수
     * @return 의약품 데이터 목록을 포함한 CompletableFuture 객체
     */
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

    /**
     * XML 태그를 제거하여 텍스트만 추출하는 메서드입니다.
     *
     * @param xml XML 형식의 문자열
     * @return 태그가 제거된 텍스트
     */
    private String removeXmlTags(String xml) {
        if (xml == null || xml.isEmpty()) {
            return xml;
        }

        Document doc = Jsoup.parse(xml);  // Jsoup을 사용하여 XML을 파싱
        Element body = doc.body();
        return body.text(); // 텍스트만 추출하여 반환
    }

    /**
     * 문자열이 지정된 바이트 수를 초과할 경우 자르는 메서드입니다.
     *
     * @param str 자를 문자열
     * @param byteLimit 제한할 바이트 수
     * @return 잘린 문자열
     */
    private String truncateString(String str, int byteLimit) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        byte[] strBytes = str.getBytes();
        if (strBytes.length <= byteLimit) {
            return str;  // 문자열의 길이가 제한보다 짧으면 그대로 반환
        }

        int endIndex = 0;
        int byteCount = 0;

        for (int i = 0; i < str.length(); i++) {
            byteCount += String.valueOf(str.charAt(i)).getBytes().length; // 각 문자의 바이트 길이를 계산
            if (byteCount > byteLimit) {
                break; // 제한을 초과하면 루프 종료
            }
            endIndex = i + 1;
        }

        return str.substring(0, endIndex); // 제한된 바이트 수 내에서 잘라서 반환
    }
}
