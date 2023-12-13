FROM openjdk:17-jdk-slim

WORKDIR /app/

COPY ./build/libs/together-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]