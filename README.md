# Club-8 Demo Presentation

## Build & Run


* `./mvnw clean compile install`
* `docker build . -t demo-club8`
* `docker pull mysql:5.6`
* `docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo_club8 -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:5.6`
* `docker run -p 8080:8080 --name demo-club8 --link mysql-standalone:mysql -d demo-club8`
* `curl localhost:8080/demo/add -d name=test -d surname=test -d username=testusername -d password=12345 -d emailAddress=asd@asd.com -d gender=male`
* `curl localhost:8080/demo/all`

