/*
* NOTE: MySQL is preferable choice due to its SQL dialect is used in this script
*/
CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`%`
  SQL SECURITY DEFINER
  VIEW `flight_reservation`.`ticket_view` AS
SELECT `c`.`customer_id`   AS `customer_id`,
       `c`.`first_name`    AS `first_name`,
       `c`.`last_name`     AS `last_name`,
       `r`.`route_id`      AS `route_id`,
       `rt`.`place`        AS `place`,
       `rt`.`distance`     AS `distance`,
       `rt`.`price`        AS `price`,
       `a`.`aircraft_name` AS `aircraft_name`
FROM (((`flight_reservation`.`customer` `c`
  JOIN `flight_reservation`.`reservation` `r`)
  JOIN `flight_reservation`.`route` `rt`)
       JOIN `flight_reservation`.`aircraft` `a`)
WHERE ((`c`.`customer_id` = `r`.`customer_id`)
  AND (`r`.`route_id` = `rt`.`route_id`)
  AND (`rt`.`aircraft_id` = `a`.`aircraft_id`))