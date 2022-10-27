FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY target/*.jar app.jar

ENV HTTP_PORT=4101
ENV DB_HOST=localhost
ENV DB_PORT=27017
ENV DB_NAME=losheroes

ENV TZ=America/Santiago


ENTRYPOINT ["java", "-jar", "app.jar"]