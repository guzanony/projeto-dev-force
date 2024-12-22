# Usa a imagem do Maven para construir o projeto
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Usa a imagem do Java 21 para rodar o JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Kilo-byte-0.0.1-SNAPSHOT.jar app.jar
RUN mkdir -p /app/data
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

