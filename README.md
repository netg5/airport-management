# Flight reservation
Flight reservation application based on the microservice architecture which allows to add customer, aircraft and route, 
reserve flight and see all tickets for a particular customer.

## Technologies
* Java 11
* Spring Boot
* Spring Cloud
* JPA
* Apache Maven
* MySQL
* ModelMapper
* Jackson
* Swagger
* Thymeleaf
* Twitter Bootstrap

## Services
* config - serivce which makes calls to the repository where all configurations are stored - 
[https://github.com/sergeivisotsky/flight-reservation-config](https://github.com/sergeivisotsky/flight-reservation-config)
* auth - service responsible for authentication and authorization
* registry - service registry where of all microservices can be observed
* service-resource - service to experiment with the new features
* gateway - entry point of the all services
* reservation - service which allows to reserve flight
* tickets - service where all customer tickets can be seen
* reports - service to get all reports for a specific purpose (e.g. route report)
* main-ui - user interface service (Thymeleaf + Twitter Bootstrap)

## Authentication
To access any resource authentication should be performed. By performing this request with such a parameters access_token is retrieved.

#### 1 way - using client credentials
Client ID and client secret should be sent as a basic auth header.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `username=USERNAME&password=PASSWORD&grant_type=password`

_Response:_
```json
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDIzODg2MzMsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiIzZTEzM2Q5MS0wYmVmLTQ0MzQtOTAyNS1kZmQwYTk2Njg1YzgiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.GHfKu8p7sEIBKPOoH7iknWj5eBffaoPEa2e3YZ3EAS-RFWpLi2-BK0rQd6FOtSdpNd9GOf6yvfcdmBWN7wrq9mS4RWVuhdSm8AaP-SOvntVIydvW-5m_32OGTS7r2Vlxjal0EkPisgHmFKMNYnPQw3D8R0St3NV32ycwOlpqCAaJFjuAO5iqhq0IuxUOJjGplqqNI9ubdd9qvZ7giHMbXhBbaBzsVBgzlQLkZAxN11VytqzVaC0ZL-BjKoNVgxPmTWtmY5rNgk9aqwvFw0hefzvlKkDmYuDDGK8g9C1J56MiY-HyVwKkbki3D08LC8hU4idzVxG7g9G6TVuUbKNjiA",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6IjNlMTMzZDkxLTBiZWYtNDQzNC05MDI1LWRmZDBhOTY2ODVjOCIsImV4cCI6MTU0MjU2MTQzMywiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI4OWIzMjA3OC1jMDczLTQ4MDctOWVmZS04MjYzNDkxMjYyMGIiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCJ9.APVVEQjLxnPiCS_Ri4AVhjgcRG_UrDZ-lIdEGNxsnyh0S23lt2A2xr3UrWfH9HjeQKsLsJs4Xgz00YlZfk3Ls_ttr1CUEIybHWu58Cq4JrypLIplUnhcGi6ZewAJWlolwrpLQTQEisemg7WbgKxohTn-RdMsXruhP8N389F5cdYWnh6RvuN2bXwS3Ga4rO2dZOIECDntGt3mefhbREgH5e1i-EvHt2l6ertqrbVqAiudbpwVGaDyYuZAh8xTj6ZXpkOqhm6XODmUpCT9tOXyEdSnErvmZJTOEswuloWnsWdlpmQbrirUub_nr7Yx5Z5ilDUwiYL9wbcoS6XL4yrfJQ",
    "expires_in": 86399,
    "scope": "read write trust",
    "jti": "3e133d91-0bef-4434-9025-dfd0a96685c8"
}
```

#### 2 way - using _authorization_code_ (more secured)
1. Get authorization code sending credentials using method _GET_ in browser

`http://localhost:8080/auth-api/oauth/authorize?
&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&response_type=code&redirect_uri=REDIRECT_URI&scope=SCOPES`

2. Authorization with code:
Client ID and client secret should be sent as a basic auth header.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `redirect_uri=REDIRECT_URI&grant_type=authorization_code&code=CODE&scope=SCOPES`

_Response:_
```json
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDY5NDYyMDEsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI1MDA2Y2VmYi0xYjRlLTQxYzYtOGVlZi0xZDEzZDBhNGM2YjkiLCJjbGllbnRfaWQiOiJ0cnVzdC1tZSIsInNjb3BlIjpbInJlYWQiXX0.fpcwy8K9EUK07YAzX4QvGKM65jL7aV64lnJA3HiA2Y-EJphN-2L5HOM2MfOKtpROjtB0he0ZbUM75RWQGhpiODcf2mpvWRa1L466cnCPtoj6BN2Rdyi_ZcHG0HAtRRZHRkDfRfeVtBxl7N_AxceK3esuV-y-hD-sWq-XYi5vdt-yjdJdoGG8sJ4S9Ee_qz8qUt2baRKrpwn2dAFyD5peLRhIxKvjaVUWK6lOyVg2aoaWHADV1F-ALvxF63l40JLccu4Yqmoq7rDeFzfUn66KrHneTBwAFQPsB3cxBaFSsXUWblz-mKZbNG55V1y6huqGv-6ip3M9UvOrmigSEejF6Q",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiXSwiYXRpIjoiNTAwNmNlZmItMWI0ZS00MWM2LThlZWYtMWQxM2QwYTRjNmI5IiwiZXhwIjoxNTQ3MTE5MDAxLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIl0sImp0aSI6ImE2NzA5NjFmLTY3NGUtNGM3Ny1iOTY2LTg3YzM2ZDYyOTk0OSIsImNsaWVudF9pZCI6InRydXN0LW1lIn0.Cmda6hHop3WgCmdLhpKwbRhDVPxGu6huTHpWl-xicZ5WYSe4uDLC5bGH5h7ZDPGPk4VhDYgXYG-rbCk0bcPymbbzZa511idCP2BbAplFhYCRcaOw76pksc_4os-sF8nCFDXUU2x1eMkJeGYukSO_VxuU7KvYFiOQXGxSOvQgN_7P0fp-1wUwyx16DOqQGZWByr1mL0s0nJhEl-w7jhKgHIsKVa8STXj-bkAOi3dYkB9kkRV9110ZDRdqxI0e5CaZEgcpEd6jEdTAJgTiLH4K68eEiAOA-jNIbXr174J0PT__Pnwm16wM_XaV0diDqkkZWxwuw3dbKp7Gqg8El_xFjA",
    "expires_in": 86399,
    "scope": "read",
    "jti": "5006cefb-1b4e-41c6-8eef-1d13d0a4c6b9"
}
```

After authentication it is able to access any resource with an access token provided. As there is used JWT access and refresh tokens are pretty long.

The next step is get an access to the resources. Example for the flight-api: `http://localhost:8080/flight-api/v1/customers?access_token=ACCESS_TOKEN`

In case if access token is expired refresh token should be used to renew access token.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `grant_type=refresh_token&refresh_token=REFRESH_TOKEN`

## API documentation
For endpoint documentation is used Swagger which is accessible by the url - `http://localhost:8080/docs`.
 
 * _where `http://localhost:8080` is a gateway url_

## Setup
1. Checkout config service [https://github.com/sergeivisotsky/flight-reservation-config](https://github.com/sergeivisotsky/flight-reservation-config) 
to clone all the necessary config files
2. Copy all the property files into the config folder locally and replace property `spring.cloud.config.server.git.uri` 
with `spring.cloud.config.server.native.searchLocations: file:///${user.home}/config-repo` in `bootstrap.yml` config 
file in `config-service` or create another repository and change the path to it by changing 
property `spring.cloud.config.server.git.uri`
3. Change `server.port` for each service located in config repository mentioned in `1.` paragraph _(optional)_
4. Change database driver for your database.

_Example for MySQL:_
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```
5. Change the value of database driver property `spring.datasource.driver-class-name` in config file located in repo 
called `flight-reservation-config` for each service which communicates with database
6. Change database url property: `spring.datasource.url`
7. Change database url property: `spring.datasource.username`
8. Change database url property: `spring.datasource.password`
9. Change SQL dialect modifying this property: `spring.jpa.properties.hibernate.dialect`
10. Open SQL file `oauth_schema.sql` script located in auth-service under `resources/sql` and change database name to yours
11. Open directory `database` and execute all SQL located there
12. Open `application-prod.yml` config file located in config repository for this services `flight-service`, `ticket service` 
and `auth-service` and setup your database url and credentials or in each service `application-dev.yml` in case of _dev_ profile
13. Application port and port in `security.oauth2.resource.accessTokenUri` property might be changed in your case
14. Open `logback-spring.xml` for each microservice and setup directory where all your logging files are going to saved
15. Each service has additional dev profile config file and in case you want to use it you should 
change property `spring.profiles.active` value from _prod_ to _dev_ and you can use development profile
16. Change port for each microservice in _docker-compose.yml_ for yours

**__NOTE: If you change any port it should be changed in all places where it is used e.g. all depending microservices.__**

## Run
#### 1 way - using maven or java command
* Perform command `$ ./mvnw spring-boot:run` or compile each microservice into the .jar and perform command `java -jar target/SERVICE-NAME-VERSION.jar`

**__NOTE: `config-service` and `eureka-service` should be run first due to all the configs are stored in the separate repository.__**

#### 2 way - run into the Docker container
As was mentioned earlier in Setup section `9.` paragraph each microservice contains _Dockerfile_ that allows to run it into the Docker container.

More than that every container is described in _docker-compose.yml_ _(THE BEST WAY TO RUN 10+ CONTAINERS)_

_Steps to get ready with docker-compose:_

1. Build each microservice executing the following command foe each service:
```text
./mvnw clean package spring-boot:repackage
```

2. Next step: check docker-compose for syntax errors:
```text
docker-compose config
```

3. Let docker compose build each image:
```text
docker-compose up --build
```

4. When you are about to stop all containers remove from Docker and remove the connected networks and volumes from it perform the following command:
```text
docker-compose down
``` 

Another approach to run each docker container without _docker-compose.yml_. _(UGLIEST WAY EVER IN CASE YOU HAVE 10+ CONTAINERS, prefer docker-compose instead)_

_Follow this steps:_

1. Create image from _Dockerfile_:
```text
docker build --file=Dockerfile --tag=IMAGE_NAME:latest --rm=true .
```

2. Create volume for mounting:
```text
docker volume create --name=VOLUME_NAME
```

3. Run Docker image:
```text
docker run --name=CONTAINER_NAME --publish=8888:8888 --volume=VOLUME_NAME:/var/lib/project-root/service-dir IMAGE_NAME:latest
```

_When you need to stop any container perform the following commands:_

4. Inspect container details:
```text
docker inspect CONTAINER_NAME
```

5. Stop container
```text
docker inspect CONTAINER_NAME
```

6. Remove container:
```text
docker inspect CONTAINER_NAME
```

**__NOTE: `config-service` and `eureka-service` should be run first due to all the configs are stored in the separate repository.__**

## Architecture diagram
<img src="architecture_design.svg" alt="architecture"/>