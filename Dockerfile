FROM openjdk:11
ADD /target/REST-0.0.1-SNAPSHOT.jar backend.jar

LABEL authors="79819"

ENTRYPOINT ["java", "-jar", "backend.jar"]