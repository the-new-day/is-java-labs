FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

RUN apk add --no-cache gradle

COPY build.gradle .
COPY settings.gradle .
COPY order-service/build.gradle order-service/build.gradle
COPY order-service/src order-service/src

RUN gradle :order-service:generateProto --no-daemon || true
RUN find /root/.gradle -name "*.exe" -exec chmod +x {} \;
RUN gradle :order-service:bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/order-service/build/libs/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
