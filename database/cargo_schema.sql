-- Cargo specific domain
CREATE TABLE sales_agents_and_reservations (
    id             BIGINT        NOT NULL DEFAULT nextval('sales_agents_and_reservations_id_seq'),
    country        VARCHAR(45)   NOT NULL,
    city           VARCHAR(45)   NOT NULL,
    representative VARCHAR (100) NOT NULL,
    email          VARCHAR(45)   NOT NULL,
    phone          VARCHAR(45)   NOT NULL,  
    CONSTRAINT agent_pk PRIMARY KEY(id)
);

CREATE TABLE warehouses (
    id                 BIGINT NOT NULL DEFAULT nextval('warehouses_id_seq'),
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

CREATE TABLE cargo_transfer_flights (
    id             BIGINT           NOT NULL DEFAULT nextval('cargo_transfer_flight_id_seq'),
    departure_time TIMESTAMP        NOT NULL,
    arrival_time   TIMESTAMP        NOT NULL,
    distance       DOUBLE PRECISION NOT NULL,
    place          VARCHAR(45)      NOT NULL,
    price          NUMERIC(19, 2)   NOT NULL,
    aircraft_id    BIGINT           NOT NULL,
    CONSTRAINT cargo_transfer_flight_pk PRIMARY KEY(id),
    CONSTRAINT aircraft_id_fk FOREIGN KEY(aircraft_id) REFERENCES aircraft(id)
);

CREATE TABLE cargo (
    id               BIGINT           NOT NULL DEFAULT nextval('cargo_id_seq'),
    warehouse_id     BIGINT           NOT NULL,
    sales_agent_id   BIGINT           NOT NULL,
    unit_type        VARCHAR(10)      NOT NULL,
    internal_volume  DOUBLE PRECISION NOT NULL,
    length           DOUBLE PRECISION NOT NULL,
    weight           DOUBLE PRECISION NOT NULL,
    height           DOUBLE PRECISION NOT NULL,
    max_gross_weight INTEGER          NOT NULL,
    tare_weight      INTEGER          NOT NULL,
    CONSTRAINT cargo_pk PRIMARY KEY(id),
    CONSTRAINT warehouses_id_fk FOREIGN KEY(warehouse_id) REFERENCES warehouses(id),
    CONSTRAINT sales_agents_and_reservations_id_fk FOREIGN KEY(sales_agent_id) REFERENCES sales_agents_and_reservations(id)
);

CREATE TABLE cargo_transfer_bookings (
    id                       BIGINT    NOT NULL DEFAULT nextval('cargo_transfer_booking_id_seq'),
    cargo_id                 BIGINT    NOT NULL, 
    cargo_transfer_flight_id BIGINT    NOT NULL,
    date_of_flying           DATE      NOT NULL,
    departure_time           TIMESTAMP NOT NULL,
    arrival_time             TIMESTAMP NOT NULL,
    hours_flying             INTEGER   NOT NULL,
    CONSTRAINT cargo_transfer_booking_pk PRIMARY KEY(id),
    CONSTRAINT cargo_id_fk FOREIGN KEY(cargo_id) REFERENCES cargo(id),
    CONSTRAINT cargo_transfer_flights_id_fk FOREIGN KEY(cargo_transfer_flight_id) REFERENCES cargo_transfer_flights(id)
);

CREATE TABLE cargo_transfer_actual_flights (
    id                 BIGINT NOT NULL DEFAULT nextval('cargo_transfer_actual_flights_id_seq'),
    aircraft_id        BIGINT    NOT NULL,
    pilot_id           BIGINT    NOT NULL,
    cargo_id           BIGINT    NOT NULL,
    transfer_flight_id BIGINT    NOT NULL,
    date_of_flying     DATE      NOT NULL,
    departure_time     TIMESTAMP NOT NULL,
    arrival_time       TIMESTAMP NOT NULL,
    hours_flying       INTEGER   NOT NULL,
    CONSTRAINT cargo_transfer_actual_flight_pk PRIMARY KEY(id),
    CONSTRAINT aircraft_fk FOREIGN KEY (aircraft_id) REFERENCES aircraft (id),
    CONSTRAINT pilot_id_fk FOREIGN KEY (pilot_id) REFERENCES pilot (id),
    CONSTRAINT cargo_id_fk FOREIGN KEY(cargo_id) REFERENCES cargo(id),
    CONSTRAINT cargo_transfer_flights_id_fk FOREIGN KEY(transfer_flight_id) REFERENCES cargo_transfer_flights(id)
);