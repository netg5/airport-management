INSERT INTO pilot VALUES (1, 'DAS124', '12-34-56', 'John', 'Smith', 'male', 50.0, '1970-08-29', '3rd Kennedy str, New-York', 'USA','someowner@somewhere.com', '1234567');
INSERT INTO pilot VALUES (2, 'BD9051', '90-A1-9K', 'Janis', 'Priede', 'male', 60.0, '1985-08-20', '5th Barona str, Riga', 'Latvia','someowner@somewhere.com', '34534625');
INSERT INTO pilot VALUES (3, 'LK4562', '1Q-3B-23', 'Fiona', 'Pridaine', 'female', 85.0, '1995-05-29', '1st Amerino str, Berlin', 'Germany','someowner@somewhere.com', '437372342');

INSERT INTO owner VALUES (1, 'Alexey', 'Vishnyakov', 'male', '3rd Kennedy str, New-York', 'USA', 'someowner@somewhere.com', '1234567');
INSERT INTO owner VALUES (2, 'John', 'Denny', 'male', '5rd Avenue str, New-York', 'USA', 'someowner@somewhere.com', '1234567');
INSERT INTO owner VALUES (3, 'Jane', 'Smith', 'female', '12th Elizabeth str, London', 'England', 'someowner@somewhere.com', '1234567');

INSERT INTO aircraft VALUES (1, '123D', 1, '213D34', '777-300ER', 'Boeing', 3450, 1000, 3);
INSERT INTO aircraft VALUES (2, '12dc3', 2, '23d', 'A220', 'Airbus', 43000, 5400, 6);
INSERT INTO aircraft VALUES (3, '82497Df', 3, '38fc12', 'B30', 'Airbus', 42007, 3200, 7);

INSERT INTO route VALUES (2, '2019-07-02', '2019-07-01', 1000, 'Riga', 123, 2);
INSERT INTO route VALUES (3, '2019-08-23', '2019-08-22', 3600, 'Singapoore', 3400, 3);
INSERT INTO route VALUES (1, '2019-08-23', '2019-08-22', 3600, 'New-York', 3400, 1);

INSERT INTO passenger VALUES (1, 'Arena', 'Kronfold', 45, 'female', '8923847');
INSERT INTO passenger VALUES (2, 'Sergei', 'Visotsky', 21, 'male', '1234567');
INSERT INTO passenger VALUES (3, 'Krist', 'Kronfold', 29, 'male', '9204');

INSERT INTO manager VALUES (1, '12-34-56', 'John', 'Smith', 'male', '3rd Kennedy str, New-York', 'USA','someowner@somewhere.com', '1234567');
INSERT INTO manager VALUES (2, '90-A1-9K', 'Janis', 'Priede', 'male', '5th Barona str, Riga', 'Latvia','someowner@somewhere.com', '34534625');
INSERT INTO manager VALUES (3, '1Q-3B-23', 'Fiona', 'Pridaine', 'female', '1st Amerino str, Berlin', 'Germany','someowner@somewhere.com', '437372342');