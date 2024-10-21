package com.example.drugtrack.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
/**
 * TimezoneFilter는 HTTP 응답 헤더에 현재 한국 시간(Asia/Seoul)을 설정하는 필터입니다.
 * 클라이언트로 전송되는 응답에 "Date" 헤더를 한국 시간대로 설정하여 응답 시간을 로컬 시간에 맞추어 표시합니다.
 */
@Component
public class TimezoneFilter implements Filter {

    /**
     * 필터 초기화 메서드로, 필터가 생성될 때 호출됩니다.
     * 필터 초기화가 필요한 경우 이 메서드에서 처리합니다. 현재는 특별한 초기화 작업이 없습니다.
     *
     * @param filterConfig 필터 설정 정보
     * @throws ServletException 서블릿 예외가 발생할 경우
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 작업이 필요한 경우 여기에 추가
    }

    /**
     * doFilter 메서드는 필터 체인의 실제 필터링 작업을 수행합니다.
     * 이 메서드는 요청을 처리하고 응답에 한국 시간대('Asia/Seoul')로 "Date" 헤더를 추가한 후, 필터 체인의 다음 필터로 요청을 전달합니다.
     *
     * @param request  클라이언트 요청
     * @param response 서버 응답
     * @param chain    필터 체인
     * @throws IOException      입출력 예외가 발생할 경우
     * @throws ServletException 서블릿 예외가 발생할 경우
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 현재 한국 시간(Asia/Seoul)을 RFC 1123 형식으로 변환하여 응답 헤더에 추가
        String koreaTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.RFC_1123_DATE_TIME);
        httpServletResponse.setHeader("Date", koreaTime);

        // 필터 체인의 다음 필터로 요청을 전달
        chain.doFilter(request, response);
    }


    /**
     * 필터 종료 메서드로, 필터가 제거될 때 호출됩니다.
     * 리소스 해제가 필요한 경우 이 메서드에서 처리할 수 있습니다. 현재는 특별한 리소스 해제 작업이 없습니다.
     */
    @Override
    public void destroy() {
        // 리소스 해제 작업이 필요한 경우 여기에 추가
    }
}
