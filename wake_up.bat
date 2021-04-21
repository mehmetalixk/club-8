CALL ./mvnw clean compile install 
CALL docker build . -t demo-club8
CALL docker run -p 8080:8080 --name demo-club8 --link mysql-standalone:mysql -d demo-club8