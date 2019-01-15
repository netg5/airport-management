/*
* NOTE: MySQL is preferable choice due to its SQL dialect is used in this script
*/
CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`%`
  SQL SECURITY DEFINER
  VIEW `flight_reservation`.`aircraft_report_view` AS
SELECT `a`.`aircraft_id`   AS `aircraft_id`,
       `a`.`aircraft_name` AS `aircraft_name`,
       `a`.`model`         AS `model`,
       `rt`.`route_id`     AS `route_id`,
       `rt`.`distance`     AS `distance`,
       `rt`.`place`        AS `place`,
       `rt`.`price`        AS `price`
FROM (`flight_reservation`.`aircraft` `a`
       JOIN `flight_reservation`.`route` `rt`)
WHERE (`a`.`aircraft_id` = `rt`.`aircraft_id`)