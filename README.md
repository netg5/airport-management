# flight-reservation
Flight reservation application based on the microservice architecture which allows to add customer, aircraft and route, reserve flight and see all tickets for a particular customer.

## Technologies
* Java 8
* Spring Boot 2
* JPA
* Apache Maven
* MySQL
* ModelMapper
* Jackson
* Swagger

## Services
* config-service - microserivce which makes calls to tht repository where all configurations are stored - [https://github.com/sergeivisotsky/flight-reservation-config](https://github.com/sergeivisotsky/flight-reservation-config)
* auth-service - microservice responsible for authentication and authorization
* eureka-service - microservice registry where of all microservices can be observed
* doc-service - Swagger documentation service
* zuul-gateway - entry point of the all microservices
* reservation-service - main microservice which allows to reserve flight
* ticket-service - service where all customer tickets can be seen
* flight-service-ui - user interface made using angular

## TLS / SSL
Each microservice is using self-signed TLS/SSL PKCS12 certificate.

To generate keystore and public certificate the following commands should be performed.

Command to generate root key:
```text
openssl genrsa -out ca.key 4096
```

Command to generate root key with password:
```text
openssl genrsa –des3 -out ca.key 4096
```

In case if program does not accept certificate generated be the previous command this command should be performed:
```text
openssl rsa -in ca.key -out ca.key
```

Command to generate root certificate:
```text
openssl req -new -x509 -days 3650 -key ca.key -out ca.crt
```

Command to generate PKCS12 certificate:
```text
openssl pkcs12 -export -in ca.crt -inkey ca.key -out keystore.p12 -name localhost -CAfile ca.crt -caname localhost -chain
```

Command to convert PKCS12 into the _.pem_ certificate:
```text
openssl pkcs12 -in keystore.p12 -out keystore.pem -nodes
```

In case of self-signed certificate it should be added to the JVM cacerts so that client were able to communicate with services.

Command for operation described below:
```text
keytool -import -trustcacerts -keystore "%JAVA_HOME%/jre/lib/security/cacerts" -storepass changeit -alias KEYSTORE_ENTRY -import -file public-certificate.pem
```

* `changeit` default password for cacerts

NOTE: Self-signed certificates are not verified by any certification agency and due to this every browser shows warning that they are not secured and consequently are not applicable for production and can be used for dev purposes only.

## Authentication
To access any resource authentication should be performed. By performing this request with such a parameters access_token is retrieved.

Client ID and client secret should be sent as a basic auth header.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `username=USERNAME&password=PASSWORD&grant_type=GRANT_TYPE`

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

After authentication it is able to access any resource with an access token provided. As there is used JWT access and refresh tokens are pretty long.

The next step is get an access to the resources. Example for the flight-api: `http://localhost:8080/flight-api/v1/customers?access_token=ACCESS_TOKEN`

In case if access token is expired refresh token should be used to renew access token.

* URL: `http://localhost:8080/auth-api/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `grant_type=refresh_token&refresh_token=REFRESH_TOKEN`

## Setup
* Setup your database driver dependency in main pom.xml.

_Example for MySQL:_
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

1. Checkout config service [https://github.com/sergeivisotsky/flight-reservation-config](https://github.com/sergeivisotsky/flight-reservation-config) to clone all the necessary config files
2. Copy all the property files in each microservice or create another repository and change the path to it in `bootstrap.yml` config file in `config-service` by changing property `spring.cloud.config.server.git.uri`
3. Change `server.port` for each service if needed which configs are located in the repository above and other configs that are not locates in config repository in `1.` paragraph
4. Change `server.http.port` so that it was able to organize redirect from _HTTP_ to _HTTPS_
5. Add pem certificate to the JVM cacerts due to it is self-signed executing the following command:
```text
keytool -import -trustcacerts -keystore "%JAVA_HOME%/jre/lib/security/cacerts" -storepass changeit -alias KEYSTORE_ENTRY -import -file keystore.pem
```
6. Open `SERVICE_NAME.yml` config file and setup your database url and credentials for services `flight-service` , `ticket service` and `auth-service`
7. Open SQL file `oauth_schema.sql` script located in auth-service under `resources/sql` and change database name to yours
8. Open SQL file `ticket_view.sql` in service `ticket-service` under `resources/sql` and execute this script for your database (NOTE: MySQL dialect was used in this case) 
9. Create view for customer report by opening SQL file `customer_report_view.sql` and execute SQL code in your database (NOTE: MySQL dialect was used in this case)
10. Keep in mind that application port and port in `security.oauth2.resource.accessTokenUri` property might be changed in your case
11. Open `logback-spring.xml` for each microservice and setup directory where all your logging files are going to saved
12. Setup .jar names and ports in `Dockerfile` for each module

NOTE: if you change any port it should be changed in all places where it is used depending on the micrservice.

## Run
##### 1 way - using maven or java command
* Perform command `mvn spring-boot:run` or compile each microservice into the .jar and perform command `java -jar target/SERVICE-NAME-VERSION.jar`

##### 2 way - run into the Docker container
As was mentioned earlier in Setup section `9.` paragraph each microservice containd Dockerfile that allows to run it into the Docker container.
1. Change .jar file name as it is called in your case.
2. Change port for each microservice
3. Build Docker image performing command `docker build -t SERVICE_NAME .` (e.g. `docker build -t flight-service .`)
4. Run Docker container performing command `docker run -it --rm -p MACHINE_PORT:CONTAINER_PORT SERVICE_NAME` (e.g. `docker run -it --rm -p 8085:8085 flight-service`)

NOTE: `config-service` and `eureka-service` should be run first due to all the configs are stored in the separate repository

## TODO
1. End up experiment (in case of success implement in any service)
2. Configure authentication with authorization code grant_type
3. SSL for the Eureka and Config servers
4. Adopt liquibase for unit tests
5. Ignore services without swagger json in doc-service
6. Implement Hystrix fallback methods
7. Develop Front-end with Angular