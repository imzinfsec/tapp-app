# 1. 어떤 환경에서 돌릴까? (가볍고 빠른 자바 17 알파인 리눅스 환경 사용)
FROM eclipse-temurin:17-jdk-alpine

# 2. 컨테이너 안에서 작업할 기본 폴더 위치 지정
WORKDIR /app

# 3. 아까 1단계에서 만든 .jar 압축 파일을 컨테이너 안의 app.jar 라는 이름으로 복사해 옴
COPY build/libs/*-SNAPSHOT.jar app.jar

# 4. 도커 컨테이너가 켜질 때 실행할 마법의 명령어 (스프링 부트 켜기)
ENTRYPOINT ["java", "-jar", "app.jar"]