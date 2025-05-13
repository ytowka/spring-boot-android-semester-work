FROM openjdk:17-oracle
WORKDIR /app
COPY /release/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]

