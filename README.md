# flight-reservation
[![Build Status](https://travis-ci.com/sergeivisotsky/flight-reservation.svg?branch=master)](https://travis-ci.com/sergeivisotsky/flight-reservation)

## Services
* eureka-service - microservice registry where the list of all microservices can be seen
* zuul-gateway - entry point of the all microservices (Not configured properly now)
* flight-service - main microservice which allows to reserve flight

## Docker commands
* To build docker image command `docker build -t flight-service .` should be performed
* To run docker container command `docker run -it --rm -p 8085:8085 flight-service` should be performed

## TODO
* Configure Eureka Server
* Configure Zuul Gateway
* Front-end (REST client)