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
SELECT DISTINCT
    p.id AS passenger_id,
	p.first_name,
	p.last_name,
    r.id AS reservation_id,
	r.date_of_flying,
	r.departure_time,
	r.arrival_time,
	rt.distance,
	rt.place,
	fm.title,
	pr.amount,
	pr.currency
FROM passenger p
LEFT JOIN reservation r 
	ON p.id = r.passenger_id
LEFT JOIN route rt
	ON rt.id = r.route_id
LEFT JOIN fly_modes fm
	ON fm.code = r.fly_mode_code
LEFT JOIN fly_modes_prices_relation fmpr
	ON fm.code = fmpr.fly_modes_code
LEFT JOIN prices pr
	ON pr.code = fmpr.prices_code;