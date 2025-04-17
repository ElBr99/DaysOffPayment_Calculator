# FROM maven:3.8.4-openjdk-17 as builder
# WORKDIR /app
# COPY . /app/.
# RUN ./gradlew clean build -x test
#
# EXPOSE 8181
# ENTRYPOINT ["java", "-jar", "/app/*.jar"]

# Этап сборки
FROM gradle:7.5.1-jdk17 as builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# Этап выполнения
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/daysOffCalculator-1.0-SNAPSHOT.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


