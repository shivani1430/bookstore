FROM openjdk:8-jre-alpine

COPY target/bookstore-0.0.1-SNAPSHOT.jar ./bookstore-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "-Dserver.port=8080", "bookstore-0.0.1-SNAPSHOT.jar"]
