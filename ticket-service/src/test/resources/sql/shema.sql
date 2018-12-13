CREATE TABLE `aircraft`
(
  `aircraft_id`    bigint(20)   NOT NULL,
  `aircraft_name`  varchar(255) NOT NULL,
  `weight`         double       NOT NULL,
  `max_passengers` int(11)      NOT NULL,
  `model`          varchar(255) NOT NULL,
  PRIMARY KEY (`aircraft_id`)
);

CREATE TABLE `aircraft_seq`
(
  `next_val` bigint(20) DEFAULT NULL
);

CREATE TABLE `customer`
(
  `customer_id` bigint(20)   NOT NULL,
  `age`         int(11)      NOT NULL,
  `first_name`  varchar(255) NOT NULL,
  `last_name`   varchar(255) NOT NULL,
  PRIMARY KEY (`customer_id`)
);

CREATE TABLE `customer_seq`
(
  `next_val` bigint(20) DEFAULT NULL
);

CREATE TABLE `reservation`
(
  `reservation_id`   bigint(20)  NOT NULL,
  `reservation_date` datetime(6) NOT NULL,
  `customer_id`      bigint(20)  NOT NULL,
  `route_id`         bigint(20)  NOT NULL,
  PRIMARY KEY (`reservation_id`),
  KEY `FKcamidltflqvw3kkdlbuslr2db` (`customer_id`),
  KEY `FKcbe3ghgonsviponovpalraxnp` (`route_id`),
  CONSTRAINT `FKcamidltflqvw3kkdlbuslr2db` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE,
  CONSTRAINT `FKcbe3ghgonsviponovpalraxnp` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`) ON DELETE CASCADE
);

CREATE TABLE `reservation_seq`
(
  `next_val` bigint(20) DEFAULT NULL
);

CREATE TABLE `route`
(
  `route_id`       bigint(20)     NOT NULL,
  `arrival_time`   datetime       NOT NULL,
  `departure_time` datetime       NOT NULL,
  `distance`       double         NOT NULL,
  `place`          varchar(255)   NOT NULL,
  `price`          decimal(19, 2) NOT NULL,
  `aircraft_id`    bigint(20)     NOT NULL,
  PRIMARY KEY (`route_id`),
  KEY `FKhmxsignt10g676bwrwd04kyb0` (`aircraft_id`),
  CONSTRAINT `FKhmxsignt10g676bwrwd04kyb0` FOREIGN KEY (`aircraft_id`) REFERENCES `aircraft` (`aircraft_id`)
);

CREATE TABLE `route_seq`
(
  `next_val` bigint(20) DEFAULT NULL
);