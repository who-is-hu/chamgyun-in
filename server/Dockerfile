FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} chamgyun.jar
ENTRYPOINT ["java", "-jar", "/chamgyun.jar"]