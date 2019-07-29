-- Aircraft report view
CREATE VIEW aircraft_report_view AS
SELECT a.id            AS aircraft_id,
       a.aircraft_name AS aircraft_name,
       a.model_number  AS model,
       o.first_name    AS first_name,
       o.last_name     AS last_name,
       o.address       AS adress,
       o.email         AS email
FROM aircraft a
         LEFT OUTER JOIN
     owner o ON a.owner_id = o.id;

-- Passenger report view
CREATE VIEW passenger_report_view AS
SELECT p.id         AS passenger_id,
       p.first_name AS first_name,
       p.last_name  AS last_name
FROM passenger p;

-- Ticket view
CREATE VIEW ticket_view AS
SELECT p.id             AS passenger_id,
       p.first_name     AS first_name,
       p.last_name      AS last_name,
       r.id             AS aircraft_id,
       r.date_of_flying AS date_of_flying,
       r.arrival_time   AS arrival_time,
       r.hours_flying   AS hours_flying,
       a.aircraft_name  AS aircraft_name,
       a.model_number   AS model_number
FROM passenger p
         JOIN
     reservation r ON p.id = r.passenger_id
         JOIN
     route rt ON r.id = rt.id
         JOIN
     aircraft a ON r.aircraft_id = a.id;