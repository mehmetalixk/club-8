FROM openjdk:11

ADD target/demo-384-test-0.0.1-SNAPSHOT.jar app.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.8.0/wait /wait
RUN chmod +x /wait



