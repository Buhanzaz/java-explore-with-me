FROM amazoncorretto:11-alpine-jdk
LABEL authors="Евгений"
#ENV TZ=Europe/Moscow
COPY ./target/*.jar statistics-module.jar
ENTRYPOINT ["java", "-jar", "/statistics-module.jar"]
