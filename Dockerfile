FROM openjdk:15-jdk-alpine
COPY target/DemoApp.jar DemoApp.jar
ENTRYPOINT ["java", "-jar", "/DemoApp.jar"]
