# flight-reservation
[![Build Status](https://travis-ci.com/sergeivisotsky/flight-reservation.svg?branch=master)](https://travis-ci.com/sergeivisotsky/flight-reservation)

## Services
* eureka-service - microservice registry where the list of all microservices can be seen
* zuul-gateway - entry point of the all microservices (Not configured properly now)
* flight-service - main microservice which allows to reserve flight

## TODO
* Configure Eureka Server
* Configure Zuul Gateway
* Run into the Docker container
* Front-end (REST client)