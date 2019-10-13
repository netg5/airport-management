CREATE USER flight WITH ENCRYPTED PASSWORD 'flight';

-- Create authentication schema
CREATE DATABASE am_auth
    WITH
    OWNER = flight
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL PRIVILEGES ON DATABASE am_auth TO flight;

-- Create manager service schema
CREATE DATABASE am_manager
    WITH
    OWNER = flight
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL PRIVILEGES ON DATABASE am_manager TO flight;

-- Create flight booking service schema
CREATE DATABASE am_booking
    WITH
    OWNER = flight
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL PRIVILEGES ON DATABASE am_booking TO flight;
