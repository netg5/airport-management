INSERT INTO pilot VALUES (1, 'DAS124', '12-34-56', 'John', 'Smith', 'male', 50.0, '1970-08-29', '3rd Kennedy str, New-York', 'USA','someowner@somewhere.com', '1234567');
INSERT INTO pilot VALUES (2, 'BD9051', '90-A1-9K', 'Janis', 'Priede', 'male', 60.0, '1985-08-20', '5th Barona str, Riga', 'Latvia','someowner@somewhere.com', '34534625');
INSERT INTO pilot VALUES (3, 'LK4562', '1Q-3B-23', 'Fiona', 'Pridaine', 'female', 85.0, '1995-05-29', '1st Amerino str, Berlin', 'Germany','someowner@somewhere.com', '437372342');

INSERT INTO owner VALUES (1, 'Alexey', 'Vishnyakov', 'male', '3rd Kennedy str, New-York', 'USA', 'someowner@somewhere.com', '1234567');
INSERT INTO owner VALUES (2, 'John', 'Denny', 'male', '5rd Avenue str, New-York', 'USA', 'someowner@somewhere.com', '1234567');
INSERT INTO owner VALUES (3, 'Jane', 'Smith', 'female', '12th Elizabeth str, London', 'England', 'someowner@somewhere.com', '1234567');

INSERT INTO manufacturer values(1, '213213d', 'Airbus Corporate Jets', 'New-zeland');
INSERT INTO manufacturer values(2, 'fsdf32', 'Boeing Business Jets', 'USA');
INSERT INTO manufacturer values(3, 'd3r35v', 'Embraer-Empresa Brasileira DR AeronÁutica', 'Arab-Emirants');

INSERT INTO hangar VALUES(1, '2131234', 3, 'New-York');
INSERT INTO hangar VALUES(2, 'fsaf33522', 1, 'Riga');
INSERT INTO hangar VALUES(3, 'f341tg', 3, 'London');

INSERT INTO aircraft VALUES (1, 1, 1, '123D', '777-300ER', 'Boeing', 3450, 1000, 3);
INSERT INTO aircraft VALUES (2, 2, 2, '12dc3', 'A220', 'Airbus', 43000, 5400, 6);
INSERT INTO aircraft VALUES (3, 3, 3, '82497Df', 'B30', 'Airbus', 42007, 3200, 7);

INSERT INTO route VALUES (2, '2019-07-02', '2019-07-01', 1000, 'Riga', 123, 1);
INSERT INTO route VALUES (3, '2019-08-23', '2019-08-22', 3600, 'Singapoore', 3400, 2);
INSERT INTO route VALUES (1, '2019-08-23', '2019-08-22', 3600, 'New-York', 3400, 3);

INSERT INTO passenger VALUES (1, 'Arena', 'Kronfold', 45, 'female', '8923847');
INSERT INTO passenger VALUES (2, 'Sergei', 'Visotsky', 21, 'male', '1234567');
INSERT INTO passenger VALUES (3, 'Krist', 'Kronfold', 29, 'male', '9204');

INSERT INTO manager VALUES (1, '12-34-56', 'John', 'Smith', 'male', '3rd Kennedy str, New-York', 'USA','someowner@somewhere.com', '1234567');
INSERT INTO manager VALUES (2, '90-A1-9K', 'Janis', 'Priede', 'male', '5th Barona str, Riga', 'Latvia','someowner@somewhere.com', '34534625');
INSERT INTO manager VALUES (3, '1Q-3B-23', 'Fiona', 'Pridaine', 'female', '1st Amerino str, Berlin', 'Germany','someowner@somewhere.com', '437372342');

INSERT INTO reservation VALUES(1, 1, '2019-08-09', '2019-08-09 17:28:30.183', '2019-08-09 17:28:30.183', 1, 1);
INSERT INTO reservation VALUES(2, 2, '2019-08-09', '2019-08-09 17:28:30.183', '2019-08-09 17:28:30.183', 1, 2);
INSERT INTO reservation VALUES(3, 3, '2019-08-09', '2019-08-09 17:28:30.183', '2019-08-09 17:28:30.183', 1, 3);

INSERT INTO airport VALUES(1, 'Los Angeles International Airport', '1st Avenue, Los Angeles', 'USA', 'John Smith', 'Supervisor', 'jahn@example.com', '+124579');

-- User service data - 1st user password: 123456
INSERT INTO auth_user VALUES (1, 'admin', '$2a$10$sHzvSELmDqDxAI8GN.yyb.bRLDhWNB03zJfGWc9M5dQelX05GMACm');
INSERT INTO auth_user_roles VALUES (1, 'USER');
INSERT INTO auth_user_roles VALUES (2, 'ADMIN');
INSERT INTO auth_user_auth_user_roles VALUES (1, 1);
INSERT INTO auth_user_auth_user_roles VALUES (1, 2) ;
INSERT INTO oauth_client_details VALUES('trusted-client', '', '$2a$10$K9.dtdouoTm1tlhQTTQu4OEV1HPlV0IpNjUsNu/8ZBiesIkSXafmK', 'read,write,trust', 'refresh_token,password', 'http://www.google.com', 'ROLE_CLIENT,ROLE_TRUSTED_CLIENT,ROLE_ADMIN', 3600, 86400, '', '');

-- Errors
INSERT INTO response_messages VALUES(1, 'AIR-001', 'Aircraft with this ID not found');
INSERT INTO response_messages VALUES(2, 'AIR-002', 'Aircraft exploitation period is greater than 10 required years');
INSERT INTO response_messages VALUES(3, 'AIR-003', 'Aircraft with this model number not found');
INSERT INTO response_messages VALUES(4, 'PAS-001', 'Passenger with this ID not found');
INSERT INTO response_messages VALUES(5, 'PAS-002', 'This passenger has no reservations made');
INSERT INTO response_messages VALUES(6, 'RT-001', 'Route with this ID not found');
INSERT INTO response_messages VALUES(7, 'RES-001', 'Reservation with this ID not found');
INSERT INTO response_messages VALUES(8, 'PT-001', 'Passenger has no tickets');
INSERT INTO response_messages VALUES(9, 'PIL-001', 'Pilot with this ID not found');
INSERT INTO response_messages VALUES(10, 'PIL-002', 'Pilot table is empty');
INSERT INTO response_messages VALUES(11, 'RP-001', 'Required parameter is not present');
INSERT INTO response_messages VALUES(12, 'PIL-003', 'Pilot weight is greater than expected');
INSERT INTO response_messages VALUES(13, 'OW-001', 'Owner with this ID not found');
INSERT INTO response_messages VALUES(14, 'MAN-001', 'Manufacturer with this ID not found');
INSERT INTO response_messages VALUES(15, 'HAN-001', 'Hangar not found');
INSERT INTO response_messages VALUES(16, 'APT-001', 'Airport with this name not found');
INSERT INTO response_messages VALUES(17, 'APT-002', 'No airport contact found by this parameter');