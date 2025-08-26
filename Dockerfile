# Multi-stage build để tối ưu kích thước image
FROM gradle:8.5-jdk17 AS build

# Thiết lập working directory
WORKDIR /app

# Copy toàn bộ source code
COPY . .

# Build ứng dụng (Gradle sẽ tự động download dependencies)
RUN gradle build --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

# Cài đặt các package cần thiết
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Tạo user không phải root để chạy ứng dụng
RUN groupadd -r spring && useradd -r -g spring spring

# Thiết lập working directory
WORKDIR /app

# Copy JAR file từ build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Thay đổi ownership cho user spring
RUN chown spring:spring app.jar

# Chuyển sang user spring
USER spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM options để tối ưu performance
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"

# Command để chạy ứng dụng
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
