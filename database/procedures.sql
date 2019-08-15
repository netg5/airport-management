CREATE OR REPLACE PROCEDURE flight_processor()
 LANGUAGE plpgsql AS
$$
DECLARE
	res_rec RECORD;
	pilot_rec RECORD;
	aircraft_rec RECORD;
	curr_time DATE = now();
BEGIN
    SET timezone TO 'UTC +3';
	SELECT INTO res_rec
		p.first_name,
		p.last_name,
		p.age,
		p.gender,
		p.phone,
		rt.departure_time,
		rt.arrival_time,
		rt.distance,
		rt.place,
		rt.price,
		r.date_of_flying,
		r.departure_time,
		r.arrival_time,
		r.hours_flying
	FROM reservation r
	JOIN passenger p 
		ON p.id = r.passenger_id
	JOIN route rt
		ON rt.id = r.route_id;
	
	IF res_rec.departure_time = curr_time THEN
		SELECT INTO aircraft_rec * FROM aircraft a WHERE a.available = 1;
		IF aircraft_rec != NULL THEN
			UPDATE aircraft a SET a.available = 0 WHERE a.id = aircraft_rec.id;
		END IF;
		SELECT INTO pilot_rec * FROM pilot p WHERE p.available = 1;
		IF pilot_rec != NULL THEN
			UPDATE pilot p SET p.available = 0 WHERE p.id = pilot_rec.id;
		END IF;
		
		INSERT INTO 
			actual_flight 
		VALUES (
				res_rec.date_of_flying, res_rec.departure_time, 
				res_rec.arrival_time, res_rec.hours_flying, res_rec.first_name, 
				res_rec.last_name, res_rec.gender, res_rec.address, res_rec.country, 
				res_rec.email, res_rec.phone
	   );
	END IF;
END;
$$;