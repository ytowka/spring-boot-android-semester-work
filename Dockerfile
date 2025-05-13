FROM openjdk:17-oracle
WORKDIR /app
COPY /release/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]

