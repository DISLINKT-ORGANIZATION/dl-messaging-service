FROM adoptopenjdk:11-jre-hotspot
COPY "target/messaging-service.jar" messaging-service.jar
ENTRYPOINT ["java", "-jar", "messaging-service.jar"]