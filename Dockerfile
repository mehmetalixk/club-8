FROM openjdk:11

COPY . /app

EXPOSE 8080:8080

ENTRYPOINT ["java", "-jar","/app/target/demo-384-test-0.0.1-SNAPSHOT.jar"]