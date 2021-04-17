CALL ./mvnw clean compile install 
CALL docker build . -t demo-club8
CALL docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo_club8 -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:5.6
CALL timeout 10
CALL docker run -p 8080:8080 --name demo-club8 --link mysql-standalone:mysql -d demo-club8
ECHO Finished, running