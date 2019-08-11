CREATE OR REPLACE FUNCTION activate_flight()
RETURNS TRIGGER AS $activation_trigger$
BEGIN
	IF((SELECT r.departure_time FROM reservation r) = current_timestamp) THEN
		INSERT INTO actual_flight(aircraft_id, pilot_id, route_id, date_of_flying, 
								  departure_time, arrival_time, hours_flying,
								  first_name, last_name, gender, address,
								  country, email, phone)
				  SELECT DISTINCT res.route_id,
						 res.date_of_flying,
						 res.departure_time,
						 res.arrival_time,
						 res.hours_flying,
						 p.first_name,
						 p.last_name,
						 p.gender,
						 p.phone
				FROM reservation res
				JOIN route r 
					ON res.route_id = r.id
				JOIN passenger p
					ON res.passenger_id = p.id;
	END IF;
END;
$activation_trigger$
LANGUAGE 'plpgsql'

CREATE TRIGGER flight_activation_trigger
	AFTER INSERT ON reservation
	EXECUTE PROCEDURE activate_flight();