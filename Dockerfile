# 베이스 이미지 설정
FROM openjdk:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 컨테이너로 복사
COPY build/libs/app.jar /app/app.jar

# 애플리케이션에서 사용하는 포트 노출
EXPOSE 8080

# 애플리케이션 실행 명령어 설정
ENTRYPOINT ["java", "-Xmx2g", "-jar", "/app.jar"]
