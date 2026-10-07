# ==========================================
# Multi-stage Dockerfile for IndiraHub
# Optimized for Render Free Tier (512MB RAM)
# ==========================================

# 1. Build Stage
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

# Copy dependency definition
COPY pom.xml .
# Pre-fetch dependencies to leverage Docker layer caching
RUN mvn dependency:go-offline -B

# Copy source code and static assets
COPY src ./src

# Build production executable JAR without running tests
RUN mvn clean package -DskipTests

# 2. Production Runtime Stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Non-root user for security best practices
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy the built JAR from builder stage
COPY --from=builder /build/target/*.jar app.jar

# Render injects PORT dynamically; default fallback to 8080
ENV PORT=8080
EXPOSE ${PORT}

# JVM tuning: limit heap memory to 384MB so it never exceeds Render's 512MB RAM ceiling
ENTRYPOINT ["java", "-Xmx384m", "-Dserver.port=${PORT}", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]