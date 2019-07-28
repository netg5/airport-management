-- Aircraft report view
CREATE VIEW aircraft_report_view AS
SELECT a.id            AS aircraft_id,
       a.aircraft_name AS aircraft_name,
       a.model         AS model,
       rt.id           AS route_id,
       rt.distance     AS distance,
       rt.place        AS place,
       rt.price        AS price
FROM aircraft a
         LEFT OUTER JOIN
     route rt ON a.id = rt.aircraft_id;

-- Customer report view
CREATE VIEW customer_report_view AS
SELECT c.id         AS customer_id,
       c.first_name AS first_name,
       c.last_name  AS last_name
FROM customer c;

-- Ticket view
CREATE VIEW ticket_view AS
SELECT c.id            AS customer_id,
       c.first_name    AS first_name,
       c.last_name     AS last_name,
       r.id            AS route_id,
       rt.place        AS place,
       rt.distance     AS distance,
       rt.price        AS price,
       a.aircraft_name AS aircraft_name
FROM customer c
       JOIN
     reservation r ON c.id = r.customer_id
       JOIN
     route rt ON r.id = rt.id
       JOIN
     aircraft a ON rt.id = a.id;