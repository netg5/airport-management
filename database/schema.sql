CREATE TABLE pilot
(
    id             BIGINT           NOT NULL,
    license_number VARCHAR(150)     NOT NULL,
    ssn            VARCHAR(15)      NOT NULL,
    first_name     VARCHAR(100)     NOT NULL,
    last_name      VARCHAR(100)     NOT NULL,
    gender         VARCHAR(6)       NOT NULL,
    weight         DOUBLE PRECISION NOT NULL,
    date_of_birth  DATE             NOT NULL,
    address        VARCHAR(100)     NOT NULL,
    country        VARCHAR(45)      NOT NULL,
    email          VARCHAR(45)      NOT NULL,
    phone          NUMERIC(45)      NOT NULL,
    CONSTRAINT pilot_pk PRIMARY KEY (id)
);

CREATE TABLE owner
(
    id         BIGINT       NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    gender     VARCHAR(6)   NOT NULL,
    address    VARCHAR(100) NOT NULL,
    country    VARCHAR(45)  NOT NULL,
    email      VARCHAR(45)  NOT NULL,
    phone      NUMERIC(25)  NOT NULL,
    CONSTRAINT owner_pk PRIMARY KEY (id)
);

CREATE TABLE aircraft
(
    id                  BIGINT           NOT NULL,
    manufacturer_code   VARCHAR(45)      NOT NULL,
    owner_id            BIGINT           NOT NULL,
    registration_number VARCHAR(45)      NOT NULL,
    model_number        VARCHAR(10)      NOT NULL,
    aircraft_name       VARCHAR(45)      NOT NULL,
    capacity            INTEGER          NOT NULL,
    weight              DOUBLE PRECISION NOT NULL,
    exploitation_period INTEGER          NOT NULL,
    CONSTRAINT aircraft_pk PRIMARY KEY (id),
    CONSTRAINT owner_fk FOREIGN KEY (owner_id) REFERENCES owner (id)
);

CREATE TABLE route
(
    id             BIGINT                      NOT NULL,
    arrival_time   TIMESTAMP                   NOT NULL,
    departure_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    distance       DOUBLE PRECISION            NOT NULL,
    place          VARCHAR(45)                 NOT NULL,
    price          NUMERIC(19, 2)              NOT NULL,
    aircraft_id    BIGINT,
    CONSTRAINT route_pk PRIMARY KEY (id)
);

CREATE TABLE manufacturer
(
    manufacturer_code VARCHAR(60) NOT NULL,
    manufacturer_name VARCHAR(45) NOT NULL,
    location          VARCHAR(45) NOT NULL,
    CONSTRAINT manufacturer_pk PRIMARY KEY (manufacturer_code)
);

CREATE TABLE airport
(
    id           BIGINT      NOT NULL,
    airport_name VARCHAR(45) NOT NULL,
    address      VARCHAR(45) NOT NULL,
    country      VARCHAR(45) NOT NULL,
    contact_name VARCHAR(45) NOT NULL,
    contact_job  VARCHAR(45) NOT NULL,
    email        VARCHAR(45) NOT NULL,
    phone        NUMERIC(25) NOT NULL,
    CONSTRAINT airport_pk PRIMARY KEY (id)
);

CREATE TABLE hangar
(
    hangar_number   VARCHAR(45)      NOT NULL,
    airport_id      BIGINT           NOT NULL,
    hanger_capacity DOUBLE PRECISION NOT NULL,
    hanger_location VARCHAR(45)      NOT NULL,
    CONSTRAINT hangar_pk PRIMARY KEY (hangar_number),
    CONSTRAINT airport_id_fk FOREIGN KEY (airport_id) REFERENCES airport (id)
);

CREATE TABLE passenger
(
    id         BIGINT      NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    last_name  VARCHAR(45) NOT NULL,
    age        INTEGER     NOT NULL,
    gender     VARCHAR(10) NOT NULL,
    phone      NUMERIC(25) NOT NULL,
    CONSTRAINT passenger_pk PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id             BIGINT    NOT NULL,
    passenger_id   BIGINT    NOT NULL,
    aircraft_id    BIGINT    NOT NULL,
    date_of_flying DATE      NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time   TIMESTAMP NOT NULL,
    hours_flying   INTEGER   NOT NULL,
    CONSTRAINT reservation_pk PRIMARY KEY (id),
    CONSTRAINT customer_id_fk FOREIGN KEY (passenger_id) REFERENCES passenger (id),
    CONSTRAINT aircraft_id_fk FOREIGN KEY (aircraft_id) REFERENCES aircraft (id)
);

CREATE TABLE manager
(
    id         BIGINT       NOT NULL,
    ssn        VARCHAR(15)  NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    gender     VARCHAR(6)   NOT NULL,
    address    VARCHAR(100) NOT NULL,
    country    VARCHAR(45)  NOT NULL,
    email      VARCHAR(45)  NOT NULL,
    phone      NUMERIC(25)  NOT NULL,
    CONSTRAINT manager_pk PRIMARY KEY (id)
);

CREATE TABLE actual_flight
(
    id             BIGINT       NOT NULL,
    aircraft_id    BIGINT       NOT NULL,
    pilot_id       BIGINT       NOT NULL,
    route_id       BIGINT       NOT NULL,
    date_of_flying DATE         NOT NULL,
    departure_time TIMESTAMP    NOT NULL,
    arrival_time   TIMESTAMP    NOT NULL,
    hours_flying   INTEGER      NOT NULL,
    last_name      VARCHAR(100) NOT NULL,
    gender         VARCHAR(6)   NOT NULL,
    address        VARCHAR(100) NOT NULL,
    country        VARCHAR(45)  NOT NULL,
    email          VARCHAR(45)  NOT NULL,
    phone          NUMERIC(25)  NOT NULL,
    CONSTRAINT actual_flight_pk PRIMARY KEY (id),
    CONSTRAINT aircraft_ft FOREIGN KEY (aircraft_id) REFERENCES aircraft (id),
    CONSTRAINT pilot_id_fk FOREIGN KEY (pilot_id) REFERENCES pilot (id),
    CONSTRAINT route_id_fk FOREIGN KEY (route_id) REFERENCES route (id)
);

CREATE TABLE calendar_entry
(
    id            BIGINT    NOT NULL,
    day_number    INTEGER   NOT NULL,
    day_date      INTEGER   NOT NULL,
    time_in_hours TIMESTAMP NOT NULL,
    CONSTRAINT calendar_entry_pk PRIMARY KEY (id)
);

CREATE TABLE airport_management_facts
(
    id                BIGINT      NOT NULL,
    actual_flight_id  BIGINT      NOT NULL,
    aircraft_id       BIGINT      NOT NULL,
    airport_id        BIGINT      NOT NULL,
    calendar_entry_id BIGINT      NOT NULL,
    hanger_number     VARCHAR(45) NOT NULL,
    manager_id        BIGINT      NOT NULL,
    manufacturer_code VARCHAR(45) NOT NULL,
    owner_id          BIGINT      NOT NULL,
    pilot_id          BIGINT      NOT NULL,
    date_of_flying    DATE        NOT NULL,
    departure_time    TIMESTAMP   NOT NULL,
    arrival_time      TIMESTAMP   NOT NULL,
    hours_flying      INTEGER     NOT NULL,
    average           INTEGER     NOT NULL,
    count             INTEGER     NOT NULL,
    kpi               VARCHAR(45) NOT NULL,
    CONSTRAINT calendar_pk PRIMARY KEY (id),
    CONSTRAINT actual_flight_fk FOREIGN KEY (actual_flight_id) REFERENCES actual_flight (id),
    CONSTRAINT aircraft_fk FOREIGN KEY (aircraft_id) REFERENCES aircraft (id),
    CONSTRAINT airport_fk FOREIGN KEY (airport_id) REFERENCES airport (id),
    CONSTRAINT calendar_entry_fk FOREIGN KEY (calendar_entry_id) REFERENCES calendar_entry (id),
    CONSTRAINT hanger_fk FOREIGN KEY (hanger_number) REFERENCES hangar (hangar_number),
    CONSTRAINT manager_fk FOREIGN KEY (manager_id) REFERENCES MANAGER (id),
    CONSTRAINT manufacturer_fd FOREIGN KEY (manufacturer_code) REFERENCES manufacturer (manufacturer_code),
    CONSTRAINT owner_fk FOREIGN KEY (owner_id) REFERENCES OWNER (id),
    CONSTRAINT pilot_fk FOREIGN KEY (pilot_id) REFERENCES pilot (id)
);