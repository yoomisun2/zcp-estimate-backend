FROM openjdk:8-jre-alpine

VOLUME /tmp
VOLUME /target

ADD target/zcp-estimate-backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]