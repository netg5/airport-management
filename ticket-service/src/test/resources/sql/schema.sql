create schema flight_reservation;
CREATE TABLE flight_reservation.customer
(
  customer_id bigint(20)   NOT NULL,
  first_name  varchar(255) NOT NULL,
  last_name   varchar(255) NOT NULL,
  age         int(11)      NOT NULL,
  PRIMARY KEY (customer_id)
);

CREATE TABLE flight_reservation.customer_seq
(
  next_val bigint(20) DEFAULT NULL
);

CREATE TABLE flight_reservation.aircraft
(
  aircraft_id    bigint(20)   NOT NULL,
  aircraft_name  varchar(255) NOT NULL,
  weight         double       NOT NULL,
  max_passengers int(11)      NOT NULL,
  model          varchar(255) NOT NULL,
  PRIMARY KEY (aircraft_id)
);

CREATE TABLE flight_reservation.aircraft_seq
(
  `next_val` bigint(20) DEFAULT NULL
);

CREATE TABLE flight_reservation.route
(
  route_id       bigint(20)     NOT NULL,
  arrival_time   timestamp      NOT NULL,
  departure_time timestamp      NOT NULL,
  distance       double         NOT NULL,
  place          varchar(255)   NOT NULL,
  price          decimal(19, 2) NOT NULL,
  aircraft_id    bigint(20)     NOT NULL,
  PRIMARY KEY (route_id),
  FOREIGN KEY (aircraft_id) REFERENCES aircraft (aircraft_id)
);

CREATE TABLE flight_reservation.route_seq
(
  next_val bigint(20) DEFAULT NULL
);

CREATE TABLE flight_reservation.reservation
(
  reservation_id   bigint(20) NOT NULL,
  reservation_date timestamp  NOT NULL,
  customer_id      bigint(20) NOT NULL,
  route_id         bigint(20) NOT NULL,
  PRIMARY KEY (reservation_id),
  FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE,
  FOREIGN KEY (route_id) REFERENCES route (route_id) ON DELETE CASCADE
);

CREATE TABLE flight_reservation.reservation_seq
(
  next_val bigint(20) DEFAULT NULL
);

CREATE VIEW flight_reservation.ticket_view AS
SELECT c.customer_id   AS customer_id,
       c.first_name    AS first_name,
       c.last_name     AS last_name,
       r.route_id      AS route_id,
       rt.place        AS place,
       rt.distance     AS distance,
       rt.price        AS price,
       a.aircraft_name AS aircraft_name
FROM (((flight_reservation.customer c
  JOIN flight_reservation.reservation r)
  JOIN flight_reservation.route rt)
       JOIN flight_reservation.aircraft a)
WHERE ((c.customer_id = r.customer_id)
  AND (r.route_id = rt.route_id)
  AND (rt.aircraft_id = a.aircraft_id))