FROM openjdk:17-jdk-slim

ENV TZ=Asia/Seoul

ADD /build/libs/why-trip-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]