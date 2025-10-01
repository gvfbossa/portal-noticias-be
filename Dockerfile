# ===========================
# Build
# ===========================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -DfinalName=app

# ===========================
# Run
# ===========================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Criar usuário não-root (opcional mas recomendado)
RUN addgroup --system spring && adduser --system --ingroup spring spring

COPY --from=build /app/target/portal-noticias-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9443

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

USER spring:spring

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
