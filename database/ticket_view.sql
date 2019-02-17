CREATE VIEW flight_reservation.ticket_view AS
SELECT c.customer_id   AS customer_id,
       c.first_name    AS first_name,
       c.last_name     AS last_name,
       r.route_id      AS route_id,
       rt.place        AS place,
       rt.distance     AS distance,
       rt.price        AS price,
       a.aircraft_name AS aircraft_name
FROM flight_reservation.customer c
       JOIN
     flight_reservation.reservation r ON c.customer_id = r.customer_id
       JOIN
     flight_reservation.route rt ON r.route_id = rt.route_id
       JOIN
     flight_reservation.aircraft a ON rt.aircraft_id = a.aircraft_id;