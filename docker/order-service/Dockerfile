FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY order-service/build.gradle order-service/build.gradle
COPY storage-service/build.gradle storage-service/build.gradle
COPY order-service/src order-service/src

RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
RUN ./gradlew :order-service:bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/order-service/build/libs/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
