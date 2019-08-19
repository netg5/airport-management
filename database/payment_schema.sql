CREATE TABLE payment (
    id BIGINT NOT NULL,
    payer_first_name VARCHAR(45) NOT NULL,
    payer_last_name VARCHAR(45) NOT NULL,
    price_code VARCHAR(7) NOT NULL,
    CONSTRAINT payment_pk PRIMARY KEY (id),
    CONSTRAINT price_code_fk FOREIGN KEY (price_code) REFERENCES prices(code)
);