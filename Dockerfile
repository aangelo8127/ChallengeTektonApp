# --- Etapa 1: Construcción del JAR ---
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen final liviana ---
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY --from=builder /app/target/ChallengeTektonApp-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]