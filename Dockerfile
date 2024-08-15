FROM openjdk:17-jdk
LABEL authors="extra"
COPY build/libs/*SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]