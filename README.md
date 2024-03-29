# Airport management
Airport management application based on the microservice architecture which allows to prform airport administration, flight booking and all the data processing.

## Technologies
* Java 11
* Spring Boot
* Spring Cloud
* JPA
* Maven
* MySQL
* Jackson
* Swagger
* Angular
* Jaeger

## Services
* config - serivce which makes calls to the repository where all configurations are stored - 
[https://github.com/sergeivisotsky/airport-management-config](https://github.com/sergeivisotsky/airport-management-config)
* auth - service responsible for authentication and authorization
* registry - service registry where of all microservices can be observed
* gateway - entry point of the all services
* booking - service which allows to reserve flight
* management - airport management service
* tickets - service where all passenger tickets can be seen

To be continued after implementation...

## Authentication
To access any resource authentication should be performed. By performing this request with such a parameters access_token is retrieved.

#### 1 way - using client credentials
Client ID and client secret should be sent as a basic auth header.

* URL: `http://localhost:8080/auth/oauth/token`
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

`http://localhost:8080/auth/oauth/authorize?
&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&response_type=code&redirect_uri=REDIRECT_URI&scope=SCOPES`

2. Authorization with code:
Client ID and client secret should be sent as a basic auth header.

* URL: `http://localhost:8080/auth/oauth/token`
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

In case if access token is expired refresh token should be used to renew access token.

* URL: `http://localhost:8080/auth/oauth/token`
* Method: `POST`
* Content-Type: `application/x-www-form-urlencoded`
* Content-Options: `grant_type=refresh_token&refresh_token=REFRESH_TOKEN`

## Run
#### 1 way - using maven or java command
* Perform command `$ ./mvnw spring-boot:run` or compile each microservice into the .jar and perform command `java -jar target/SERVICE-NAME-VERSION.jar`

**__NOTE: `config` and `eureka` services should be run first due to all the configs are stored in the separate repository.__**

#### 2 way - run into the Docker container
As was mentioned earlier in Setup section `9.` paragraph each service contains _Dockerfile_ that allows to run it into the Docker container.

_Follow this steps:_

1. Create image from _Dockerfile_:
```text
docker build --file=Dockerfile --tag=IMAGE_NAME:latest --rm=true .
```

. Run Docker image:
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
docker stop CONTAINER_NAME
```

6. Remove container:
```text
docker container rm CONTAINER_NAME
```

7. Observe Docker images:
```text
docker image ls
```

8. Delete Dcoker image:
```text
docker image rm IMAGE_ID
```

**__NOTE: `config` and `registry` should be run first due to all the configs are stored in the separate repository.__**

## Jaeger trace

For tracing application uses Jaeger.

Jaeger is an implementation of opentrace specification which gives an ability to perform tracing of application, 
see all the requests, see its quantity, how successful they are (status code) and more other useful features.

Run Jaeger in Docker container:

```
docker run -d --name jaeger -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:1.13
```