-- Sequences
CREATE SEQUENCE IF NOT EXISTS pilot_id_seq;
CREATE SEQUENCE IF NOT EXISTS owner_id_seq;
CREATE SEQUENCE IF NOT EXISTS aircraft_id_seq;
CREATE SEQUENCE IF NOT EXISTS flight_id_seq;
CREATE SEQUENCE IF NOT EXISTS manufacturer_id_seq;
CREATE SEQUENCE IF NOT EXISTS airport_id_seq;
CREATE SEQUENCE IF NOT EXISTS hangar_id_seq;
CREATE SEQUENCE IF NOT EXISTS passenger_id_seq;
CREATE SEQUENCE IF NOT EXISTS booking_id_seq;
CREATE SEQUENCE IF NOT EXISTS manager_id_seq;
CREATE SEQUENCE IF NOT EXISTS response_messages_id_seq;
CREATE SEQUENCE IF NOT EXISTS actual_flight_id_seq;
CREATE SEQUENCE IF NOT EXISTS calendar_entry_id_seq;
CREATE SEQUENCE IF NOT EXISTS airport_management_facts_id_seq;
CREATE SEQUENCE IF NOT EXISTS auth_user_id_seq;
CREATE SEQUENCE IF NOT EXISTS auth_user_auth_user_roles_id_seq;
CREATE SEQUENCE IF NOT EXISTS prices_id_seq;
CREATE SEQUENCE IF NOT EXISTS fly_modes_id_seq;
CREATE SEQUENCE IF NOT EXISTS sales_agents_and_reservations_id_seq;
CREATE SEQUENCE IF NOT EXISTS handling_agents_id_seq;

-- Tables
CREATE TABLE IF NOT EXISTS pilot
(
    id             BIGINT           NOT NULL DEFAULT nextval('pilot_id_seq'),
    license_number VARCHAR(150)     NOT NULL,
    ssn            VARCHAR(15)      NOT NULL,
    first_name     VARCHAR(45)      NOT NULL,
    last_name      VARCHAR(45)      NOT NULL,
    gender         VARCHAR(6)       NOT NULL,
    weight         DOUBLE PRECISION NOT NULL,
    date_of_birth  DATE             NOT NULL,
    address        VARCHAR(100)     NOT NULL,
    country        VARCHAR(45)      NOT NULL,
    email          VARCHAR(45)      NOT NULL,
    phone          VARCHAR(45)      NOT NULL,
    CONSTRAINT pilot_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS owner
(
    id         BIGINT       NOT NULL DEFAULT nextval('owner_id_seq'),
    first_name VARCHAR(45)  NOT NULL,
    last_name  VARCHAR(45)  NOT NULL,
    gender     VARCHAR(6)   NOT NULL,
    address    VARCHAR(100) NOT NULL,
    country    VARCHAR(45)  NOT NULL,
    email      VARCHAR(45)  NOT NULL,
    phone      VARCHAR(45)  NOT NULL,
    CONSTRAINT owner_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS manufacturer
(
    id                BIGINT      NOT NULL DEFAULT nextval('manufacturer_id_seq'),
    manufacturer_code VARCHAR(60) NOT NULL,
    manufacturer_name VARCHAR(45) NOT NULL,
    location          VARCHAR(45) NOT NULL,
    CONSTRAINT manufacturer_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS hangar
(
    id              BIGINT      NOT NULL DEFAULT nextval('hangar_id_seq'),
    hangar_number   VARCHAR(45) NOT NULL,
    hangar_capacity INTEGER     NOT NULL,
    hangar_location VARCHAR(45) NOT NULL,
    CONSTRAINT hangar_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS aircraft
(
    id                  BIGINT           NOT NULL DEFAULT nextval('aircraft_id_seq'),
    manufacturer_id     BIGINT           NOT NULL,
    hangar_id           BIGINT           NOT NULL,
    registration_number VARCHAR(45)      NOT NULL,
    model_number        VARCHAR(10)      NOT NULL,
    aircraft_name       VARCHAR(45)      NOT NULL,
    capacity            INTEGER          NOT NULL,
    weight              DOUBLE PRECISION NOT NULL,
    exploitation_period INTEGER          NOT NULL,
    CONSTRAINT aircraft_pk PRIMARY KEY (id),
    CONSTRAINT manufacturer_fk FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (id),
    CONSTRAINT hangar_fk FOREIGN KEY (hangar_id) REFERENCES hangar (id)
);

CREATE TABLE IF NOT EXISTS flight
(
    id             BIGINT                      NOT NULL DEFAULT nextval('flight_id_seq'),
    departure_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arrival_time   TIMESTAMP                   NOT NULL,
    distance       DOUBLE PRECISION            NOT NULL,
    place          VARCHAR(45)                 NOT NULL,
    price          NUMERIC(19, 2)              NOT NULL,
    aircraft_id    BIGINT                      NOT NULL,
    CONSTRAINT flight_pk PRIMARY KEY (id),
    CONSTRAINT aircraft_id_fk FOREIGN KEY(aircraft_id) REFERENCES aircraft(id)
);

CREATE TABLE IF NOT EXISTS airport
(
    id           BIGINT      NOT NULL DEFAULT nextval('airport_id_seq'),
    airport_name VARCHAR(45) NOT NULL,
    address      VARCHAR(45) NOT NULL,
    country      VARCHAR(45) NOT NULL,
    contact_name VARCHAR(45) NOT NULL,
    contact_job  VARCHAR(45) NOT NULL,
    email        VARCHAR(45) NOT NULL,
    phone        VARCHAR(45) NOT NULL,
    CONSTRAINT airport_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS passenger
(
    id         BIGINT      NOT NULL DEFAULT nextval('passenger_id_seq'),
    first_name VARCHAR(45) NOT NULL,
    last_name  VARCHAR(45) NOT NULL,
    age        INTEGER     NOT NULL,
    gender     VARCHAR(10) NOT NULL,
    phone      VARCHAR(45) NOT NULL,
    CONSTRAINT passenger_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS prices (
    code        VARCHAR(7)    NOT NULL,
    amount      NUMERIC(5)    NOT NULL,
    currency    VARCHAR(3)    NOT NULL,
    CONSTRAINT  prices_pk     PRIMARY KEY(code)
);

CREATE TABLE IF NOT EXISTS fly_modes (
    code        VARCHAR(7)    NOT NULL,
    title       VARCHAR(45)   NOT NULL,
    description VARCHAR(200)  NOT NULL,
    CONSTRAINT  fly_modes_pk  PRIMARY KEY(code)
);

CREATE TABLE IF NOT EXISTS fly_modes_prices_relation (
    fly_modes_code VARCHAR(7)    NOT NULL,
    prices_code    VARCHAR(7)    NOT NULL,
    CONSTRAINT fly_modes_code_fk FOREIGN KEY (fly_modes_code) REFERENCES fly_modes(code),
    CONSTRAINT prices_code_fk    FOREIGN KEY (prices_code)    REFERENCES prices(code)
);

CREATE TABLE IF NOT EXISTS booking
(
    id             BIGINT       NOT NULL DEFAULT nextval('booking_id_seq'),
    passenger_id   BIGINT       NOT NULL,
    flight_id      BIGINT       NOT NULL,
    fly_mode_code  VARCHAR(7)   NOT NULL,
    date_of_flying DATE         NOT NULL,
    departure_time TIMESTAMP    NOT NULL,
    arrival_time   TIMESTAMP    NOT NULL,
    hours_flying   INTEGER      NOT NULL,
    CONSTRAINT reservation_pk   PRIMARY KEY (id),
    CONSTRAINT passenger_id_fk  FOREIGN KEY (passenger_id) REFERENCES passenger (id),
    CONSTRAINT flight_id_fk     FOREIGN KEY(flight_id)      REFERENCES flight(id),
    CONSTRAINT fly_mode_code_fk FOREIGN KEY(fly_mode_code) REFERENCES fly_modes(code)
);

CREATE TABLE IF NOT EXISTS manager
(
    id         BIGINT       NOT NULL DEFAULT nextval('manager_id_seq'),
    ssn        VARCHAR(15)  NOT NULL,
    first_name VARCHAR(45)  NOT NULL,
    last_name  VARCHAR(45)  NOT NULL,
    gender     VARCHAR(6)   NOT NULL,
    address    VARCHAR(100) NOT NULL,
    country    VARCHAR(45)  NOT NULL,
    email      VARCHAR(45)  NOT NULL,
    phone      VARCHAR(45)  NOT NULL,
    CONSTRAINT manager_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS actual_flight
(
    id             BIGINT       NOT NULL DEFAULT nextval('actual_flight_id_seq'),
    aircraft_id    BIGINT       NOT NULL,
    pilot_id       BIGINT       NOT NULL,
    flight_id      BIGINT       NOT NULL,
    passenger_id   BIGINT       NOT NULL,
    date_of_flying DATE         NOT NULL,
    departure_time TIMESTAMP    NOT NULL,
    arrival_time   TIMESTAMP    NOT NULL,
    hours_flying   INTEGER      NOT NULL,
    CONSTRAINT actual_flight_pk PRIMARY KEY (id),
    CONSTRAINT aircraft_fk FOREIGN KEY (aircraft_id) REFERENCES aircraft (id),
    CONSTRAINT pilot_id_fk FOREIGN KEY (pilot_id) REFERENCES pilot (id),
    CONSTRAINT flight_id_fk FOREIGN KEY (flight_id) REFERENCES flight (id),
    CONSTRAINT passenger_id_fk FOREIGN KEY (passenger_id) REFERENCES passenger (id)
);

CREATE TABLE IF NOT EXISTS calendar_entry
(
    id            BIGINT    NOT NULL DEFAULT nextval('calendar_entry_id_seq'),
    day_number    INTEGER   NOT NULL,
    day_date      INTEGER   NOT NULL,
    time_in_hours TIMESTAMP NOT NULL,
    CONSTRAINT calendar_entry_pk PRIMARY KEY (id)
);

-- Response message storage
CREATE TABLE IF NOT EXISTS response_messages
(
    id          BIGINT        NOT NULL DEFAULT nextval('response_messages_id_seq'),
    code        VARCHAR(10)   NOT NULL,
    description VARCHAR(1000) NOT NULL,
    CONSTRAINT response_msg_pk PRIMARY KEY (id)
);

-- Cargo specific domain
CREATE TABLE sales_agents_and_reservations (
    id             BIGINT        NOT NULL DEFAULT nextval('sales_agents_and_reservations_id_seq'),
    country        VARCHAR(45)   NOT NULL,
    city           VARCHAR(45)   NOT NULL,
    representative VARCHAR (100) NOT NULL,
    email          VARCHAR(45)   NOT NULL,
    phone          VARCHAR(45)   NOT NULL  
    CONSTRAINT agent_pk PRIMARY KEY(id)
);

CREATE TABLE handling_agents (
    id                 BIGINT NOT NULL DEFAULT nextval('handling_agents_id_seq'),
    country            VARCHAR(45)      NOT NULL,
    city               VARCHAR(45)      NOT NULL,
    warehouse_handling VARCHAR(100)     NOT NULL,
    heavy_cargo        DOUBLE PRECISION NOT NULL,
    dangerous          VARCHAR(12)      NOT NULL,
    keep_cool_service  VARCHAR(12)      NOT NULL,
    live_animals       VARCHAR(12)      NOT NULL,
    valuable_cargo     VARCHAR(12)      NOT NULL,
    CONSTRAINT handling_agents_pk PRIMARY KEY(id)
);