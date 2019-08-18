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
    b.id AS booking_id,
	b.date_of_flying,
	b.departure_time,
	b.arrival_time,
	f.distance,
	f.place,
	fm.title,
	pr.amount,
	pr.currency
FROM passenger p
LEFT JOIN booking b 
	ON p.id = b.passenger_id
LEFT JOIN flight f
	ON f.id = b.flight_id
LEFT JOIN fly_modes fm
	ON fm.code = b.fly_mode_code
LEFT JOIN fly_modes_prices_relation fmpr
	ON fm.code = fmpr.fly_modes_code
LEFT JOIN prices pr
	ON pr.code = fmpr.prices_code;