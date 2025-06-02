FROM gradle:8.9-jdk17 AS build
WORKDIR /app
COPY .. /app/.
RUN gradle :ContentFriendsBackend:build -x test --no-daemon

FROM openjdk:17-oracle
COPY --from=build /app/ContentFriendsBackend/build/libs/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]

