# Этап сборки
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Этап запуска
FROM eclipse-temurin:17-jre
COPY --from=build /target/alwoosh-fitness-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "app.jar", "edu.aitu.oop3.Main"]