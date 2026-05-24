FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

RUN apk add --no-cache gradle

COPY build.gradle .
COPY settings.gradle .
COPY storage-service/build.gradle storage-service/build.gradle
COPY storage-service/src storage-service/src

RUN gradle :storage-service:generateProto --no-daemon || true
RUN find /root/.gradle -name "*.exe" -exec chmod +x {} \;
RUN gradle :storage-service:bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/storage-service/build/libs/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
