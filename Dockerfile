# 빌드 단계
FROM gradle:8.0-jdk17 AS builder
WORKDIR /home/app
COPY . .
RUN gradle build --no-daemon

# 실행 단계
FROM openjdk:17-jdk-slim
WORKDIR /app

# 한국 시간대 설정
RUN apt-get update && apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata && \
    apt-get clean

# 빌드된 jar 복사
COPY --from=builder /home/app/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
