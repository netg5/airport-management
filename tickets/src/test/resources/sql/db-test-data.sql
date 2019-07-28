insert into flight_reservation.passenger(customer_id, first_name, last_name, age)
values (1, 'John', 'Smith', 20);
insert into flight_reservation.aircraft(aircraft_id, aircraft_name, weight, max_passengers, model)
values (1, 'Boeing', 2000, 2300, '123-897');
insert into flight_reservation.route(route_id, arrival_time, departure_time, distance, place, price, aircraft_id)
values (1, '2018-12-23', '2018-12-23', 2500, 'Riga', 2500, 1);
insert into flight_reservation.reservation(reservation_id, reservation_date, customer_id, route_id)
values (1, '2018-12-23', 1, 1);