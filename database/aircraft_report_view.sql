CREATE VIEW flight_reservation.aircraft_report_view AS
SELECT a.aircraft_id   AS aircraft_id,
       a.aircraft_name AS aircraft_name,
       a.model         AS model,
       rt.route_id     AS route_id,
       rt.distance     AS distance,
       rt.place        AS place,
       rt.price        AS price
FROM flight_reservation.aircraft a
       LEFT OUTER JOIN
     flight_reservation.route rt ON a.aircraft_id = rt.aircraft_id;