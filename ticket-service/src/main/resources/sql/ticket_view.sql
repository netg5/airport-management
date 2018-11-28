CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`%`
  SQL SECURITY DEFINER
VIEW `flight_service`.`ticket_view` AS
  SELECT
         `c`.`first_name` AS `first_name`,
         `c`.`last_name` AS `last_name`,
         `fr`.`route_id` AS `route_id`,
         `rt`.`place` AS `place`,
         `rt`.`distance` AS `distance`,
         `rt`.`price` AS `price`,
         `a`.`aircraft_name` AS `aircraft_name`
  FROM
       (((`flight_service`.`customer` `c`
           JOIN `flight_service`.`flight_reservation` `fr`)
           JOIN `flight_service`.`route` `rt`)
           JOIN `flight_service`.`aircraft` `a`)
  WHERE
      ((`c`.`customer_id` = `fr`.`customer_id`)
         AND (`fr`.`route_id` = `rt`.`route_id`)
         AND (`rt`.`aircraft_id` = `a`.`aircraft_id`))