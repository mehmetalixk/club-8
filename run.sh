#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

dock=$(docker container ls -a | grep "java" | awk -F' ' '{print $1}')
# shellcheck disable=SC2046
docker container stop "$dock"
docker container rm -v "$dock"

./mvnw clean compile install
docker build . -t demo-club8
docker run -p 8080:8080 --name demo-club8 --link mysql-standalone:mysql -d demo-club8
