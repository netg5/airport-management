# flight-reservation
[![Build Status](https://travis-ci.com/sergeivisotsky/flight-reservation.svg?branch=master)](https://travis-ci.com/sergeivisotsky/flight-reservation)

Application to reserve flights.
Information about aircrafts, customers and routes is stored into the database.
Customer makes a reservation of the specific route as well as aircraft due to the route and aircraft are connected with each other by `One-to-Many` relationship.

## Technologies
* Java 8
* Apache Tomcat 9+
* Apache Maven
* Spring Boot 2
* ModelMapper

##### TODO: Functionality to reserve flight, Front-end (REST client)