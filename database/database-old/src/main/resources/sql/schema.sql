-- Tables
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
    code          VARCHAR(7)    NOT NULL,
    title         VARCHAR(45)   NOT NULL,
    "description" VARCHAR(200)  NOT NULL,
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
    CONSTRAINT booking_pk       PRIMARY KEY (id),
    CONSTRAINT passenger_id_fk  FOREIGN KEY (passenger_id) REFERENCES passenger (id),
    CONSTRAINT flight_id_fk     FOREIGN KEY(flight_id)     REFERENCES flight(id),
    CONSTRAINT fly_mode_code_fk FOREIGN KEY(fly_mode_code) REFERENCES fly_modes(code)
);

CREATE TABLE IF NOT EXISTS manager
(
    id         BIGINT       NOT NULL DEFAULT nextval('manager_id_seq'),
    ssn        VARCHAR(15)  NOT NULL,
    first_name VARCHAR(45)  NOT NULL,
    last_name  VARCHAR(45)  NOT NULL,
    gender     VARCHAR(6)   NOT NULL,
    "address"  VARCHAR(100) NOT NULL,
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

-- NB: Decide if this table should be present
CREATE TABLE IF NOT EXISTS calendar_entry
(
    id            BIGINT    NOT NULL DEFAULT nextval('calendar_entry_id_seq'),
    day_number    INTEGER   NOT NULL,
    day_date      INTEGER   NOT NULL,
    time_in_hours TIMESTAMP NOT NULL,
    CONSTRAINT calendar_entry_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS timetable (
    id             BIGINT       NOT NULL DEFAULT nextval('timetable_id_seq'),
    scheduled_time TIME         NOT NULL,
    flight_number  VARCHAR(6)   NOT NULL,
    destination    VARCHAR(100) NOT NULL,
    hours_flying   INTEGER      NOT NULL,
    departure_time TIMESTAMP    NOT NULL,
    arrival_time   TIMESTAMP    NOT NULL,
    CONSTRAINT timetable_pk PRIMARY KEY(id)
);

-- Response message storage
CREATE TABLE IF NOT EXISTS response_messages
(
    id            BIGINT        NOT NULL DEFAULT nextval('response_messages_id_seq'),
    code          VARCHAR(10)   NOT NULL,
    "description" VARCHAR(1000) NOT NULL,
    CONSTRAINT response_msg_pk PRIMARY KEY (id)
);
