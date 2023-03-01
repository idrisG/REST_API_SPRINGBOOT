FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY target/add-user-app-0.0.1-SNAPSHOT.jar add-user-app.jar

ENTRYPOINT ["java","-jar","add-user-app.jar"]