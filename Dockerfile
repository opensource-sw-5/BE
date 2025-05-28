# 빌드 단계
FROM gradle:8.0-jdk17 AS builder

WORKDIR /home/app

# 소스 코드 복사
COPY .. ${APP_DIR}
WORKDIR ${APP_DIR}

# 빌드 실행
RUN gradle build --no-daemon -x test

# 실행 단계
FROM openjdk:17-jdk-slim

# 앱 경로를 변수로 설정 (두 번째 스테이지에서도 동일하게 유지)
ARG APP_DIR=/home/app

# 빌드 결과물 복사
ARG JAR_FILE=build/libs/*.jar

COPY --from=build ${APP_DIR}/qrust-api/${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
