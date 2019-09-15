CREATE USER flight WITH ENCRYPTED PASSWORD 'flight';

CREATE DATABASE flight_reservation
    WITH 
    OWNER = flight
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
GRANT ALL PRIVILEGES ON DATABASE flight_reservation TO flight;