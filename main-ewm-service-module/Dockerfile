FROM amazoncorretto:11-alpine-jdk
LABEL authors="Евгений"
#ENV TZ=Europe/Moscow
COPY target/*.jar ewm-module.jar
ENTRYPOINT ["java", "-jar", "/ewm-module.jar"]
