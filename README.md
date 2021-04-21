# Club-8 Demo Presentation

## Build & Run


* `./mvnw clean compile install`
* `docker build . -t demo-club8`

If MySQL image not exist in your repository:
* `docker pull mysql:5.6`

If mysql container is already running, skip this step:
* `docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo_club8 -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:5.6`

* `docker run -p 8080:8080 --name demo-club8 --link mysql-standalone:mysql -d demo-club8`

