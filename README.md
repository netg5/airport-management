# flight-reservation
[![Build Status](https://travis-ci.com/sergeivisotsky/flight-reservation.svg?branch=master)](https://travis-ci.com/sergeivisotsky/flight-reservation)

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
* flight-service - main microservice which allows to reserve flight
* ticket-service - service where all customer tickets can be seen

## Docker commands
* To build docker image command `docker build -t flight-service .` should be performed
* To run docker container command `docker run -it --rm -p 8085:8085 flight-service` should be performed

## Authentication
To access any resource authentication should be performed. By performing this request with such a parameters access_token is retrieved.
After authentication we have an access resource by providing access_token
* URL: `http://localhost:8080/auth-api/oauth/token`
* Content-Type: `application/x-www-form-urlencoded`
* ContentOptions: `username=USERNAME&password=PASSWORD&grant_type=GRANT_TYPE`

_response_
```
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDIzODg2MzMsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiIzZTEzM2Q5MS0wYmVmLTQ0MzQtOTAyNS1kZmQwYTk2Njg1YzgiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.GHfKu8p7sEIBKPOoH7iknWj5eBffaoPEa2e3YZ3EAS-RFWpLi2-BK0rQd6FOtSdpNd9GOf6yvfcdmBWN7wrq9mS4RWVuhdSm8AaP-SOvntVIydvW-5m_32OGTS7r2Vlxjal0EkPisgHmFKMNYnPQw3D8R0St3NV32ycwOlpqCAaJFjuAO5iqhq0IuxUOJjGplqqNI9ubdd9qvZ7giHMbXhBbaBzsVBgzlQLkZAxN11VytqzVaC0ZL-BjKoNVgxPmTWtmY5rNgk9aqwvFw0hefzvlKkDmYuDDGK8g9C1J56MiY-HyVwKkbki3D08LC8hU4idzVxG7g9G6TVuUbKNjiA",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImF0aSI6IjNlMTMzZDkxLTBiZWYtNDQzNC05MDI1LWRmZDBhOTY2ODVjOCIsImV4cCI6MTU0MjU2MTQzMywiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI4OWIzMjA3OC1jMDczLTQ4MDctOWVmZS04MjYzNDkxMjYyMGIiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCJ9.APVVEQjLxnPiCS_Ri4AVhjgcRG_UrDZ-lIdEGNxsnyh0S23lt2A2xr3UrWfH9HjeQKsLsJs4Xgz00YlZfk3Ls_ttr1CUEIybHWu58Cq4JrypLIplUnhcGi6ZewAJWlolwrpLQTQEisemg7WbgKxohTn-RdMsXruhP8N389F5cdYWnh6RvuN2bXwS3Ga4rO2dZOIECDntGt3mefhbREgH5e1i-EvHt2l6ertqrbVqAiudbpwVGaDyYuZAh8xTj6ZXpkOqhm6XODmUpCT9tOXyEdSnErvmZJTOEswuloWnsWdlpmQbrirUub_nr7Yx5Z5ilDUwiYL9wbcoS6XL4yrfJQ",
    "expires_in": 86399,
    "scope": "read write trust",
    "jti": "3e133d91-0bef-4434-9025-dfd0a96685c8"
}
```
As there is used JWT access and refresh tokens are pretty long.

The next step is get an access to the resources. Example for the flight-api: `http://localhost:8080/flight-api/v1/customers?access_token=ACCESS_TOKEN`

In case if access token is expired refresh token should be used to renew access token.
* URL: `http://localhost:8080/auth-api/oauth/token`
* Content-Type: `application/x-www-form-urlencoded`
* ContentOptions: `grant_type=refresh_token&refresh_token=REFRESH_TOKEN`

## TODO
1. Adopt liquibase for unit tests
2. Ignore services without swagger json in doc-service
3. Implement Hystrix fallback methods
4. Front-end (REST client)