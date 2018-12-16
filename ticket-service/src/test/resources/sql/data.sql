insert into flight_reservation.customer(customer_id, first_name, last_name, age)
values (1, 'John', 'Smith', 20);
insert into flight_reservation.aircraft(aircraft_id, aircraft_name, weight, max_passengers, model)
VALUES (1, 'Boeing', 2000, 2300, '123-897');
insert into flight_reservation.route(route_id, arrival_time, departure_time, distance, place, price, aircraft_id)
values (1, '2018-12-23', '2018-12-23', 2500, 'Riga', 2500, 1);
insert into flight_reservation.reservation(reservation_id, reservation_date, customer_id, route_id)
VALUES (1, '2018-12-23', 1, 1);

/*create view ticket_view as
select c.customer_id,
       c.first_name,
       c.last_name,
       r.route_id,
       r.place,
       r.distance,
       r.price,
       a.aircraft_id
from flight_reservation.customer c,
     flight_reservation.route r,
     flight_reservation.aircraft a;*/