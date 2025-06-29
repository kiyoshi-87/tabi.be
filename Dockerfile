# ------------ Stage 1: Build with Maven ------------
FROM maven:3.9.5-eclipse-temurin-21 AS builder
# Set working directory
WORKDIR /app
# Copy dependency descriptors
COPY pom.xml .
# Download dependencies early (cache optimization)
RUN mvn dependency:go-offline -B
# Copy all source code
COPY src ./src
# Build the application (skip tests for speed)
RUN mvn clean package -DskipTests

# ------------ Stage 2: Run with lightweight JDK ------------
FROM eclipse-temurin:21-jdk-alpine
# Set working directory
WORKDIR /app
# Copy the jar from the previous stage
COPY --from=builder /app/target/*.jar app.jar
# Application port (optional)
EXPOSE 8085
# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]