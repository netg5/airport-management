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

-- Flight report view
CREATE VIEW flight_report AS
SELECT
	f.id AS flight_id,
	f.departure_time,
	f.arrival_time,
	f.distance,
	f.place,
	f.price,
	a.registration_number,
	a.model_number,
	a.aircraft_name,
	a.capacity,
	a.weight,
	a.exploitation_period,
	m.manufacturer_code,
	m.manufacturer_name,
	m.location,
	h.hangar_number,
	h.hangar_capacity,
	h.hangar_location
FROM flight f
LEFT JOIN aircraft a 
	on aircraft_id = a.id
LEFT JOIN manufacturer m
	ON m.id = a.manufacturer_id
LEFT JOIN hangar h
	ON h.id = a.hangar_id;