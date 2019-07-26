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