FROM openjdk:17-jdk-slim AS build
ARG JAR_FILE=build/libs/hhplus-framework-study-board.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]