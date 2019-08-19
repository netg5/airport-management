CREATE SEQUENCE IF NOT EXISTS processor_insert_id_seq;
CREATE OR REPLACE PROCEDURE flight_processor()
 LANGUAGE plpgsql AS
$$
DECLARE
	booking_rec RECORD;
	pilot_rec RECORD;
	aircraft_rec RECORD;
	curr_time DATE = now();
BEGIN
    SET timezone TO 'UTC +3';
	SELECT INTO booking_rec
        f.id               AS flight_id,
		f.departure_time,
		f.arrival_time,
		f.distance,
		f.place,
		f.price,
        b.id                AS booking_id,
		b.date_of_flying,
		b.departure_time,
		b.arrival_time,
		b.hours_flying,
		b.passenger_id
	FROM booking b
	JOIN flight f
		ON f.id = b.flight_id;
	
	IF booking_rec.departure_time <= curr_time THEN
		SELECT INTO aircraft_rec * FROM aircraft a WHERE a.available = 1;
		INSERT INTO 
			actual_flight 
		VALUES (
                nextval('processor_insert_id_seq'), 
                booking_rec.route_id,
				booking_rec.passenger_id, 
                booking_rec.date_of_flying, 
                booking_rec.departure_time, 
                booking_rec.arrival_time, 
                booking_rec.hours_flying
	   );
	   DELETE FROM booking b WHERE b.id = booking_rec.booking_id;
	END IF;
END;
$$;