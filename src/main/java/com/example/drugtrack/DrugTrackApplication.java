package com.example.drugtrack;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**
 * DrugTrackApplication은 Spring Boot 애플리케이션의 메인 클래스입니다.
 * 애플리케이션을 실행하는 진입점이며, 스케줄링 및 기본 시간대를 설정하는 기능을 포함합니다.
 */
@SpringBootApplication // Spring Boot의 자동 설정, Bean 스캔 등을 활성화하는 애너테이션
@EnableScheduling // 스케줄링 기능을 활성화하는 애너테이션
public class DrugTrackApplication {

	/**
	 * 애플리케이션의 메인 메서드로, Java 프로그램의 시작점입니다.
	 * SpringApplication.run 메서드를 호출하여 애플리케이션을 실행합니다.
	 * 또한, 애플리케이션의 기본 시간대를 'Asia/Seoul'로 설정합니다.
	 *
	 * @param args 명령줄 인자를 받는 배열
	 */
	public static void main(String[] args) {
		// 기본 시간대를 'Asia/Seoul'로 설정
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

		// Spring Boot 애플리케이션 실행
		SpringApplication.run(DrugTrackApplication.class, args);
	}


}
