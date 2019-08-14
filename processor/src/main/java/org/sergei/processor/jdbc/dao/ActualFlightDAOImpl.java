package org.sergei.processor.jdbc.dao;

import org.sergei.processor.jdbc.model.*;
import org.sergei.processor.rest.exceptions.FlightRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergei Visotsky
 */
@Repository
public class ActualFlightDAOImpl implements ActualFlightDAO {

    private final DataSource dataSource;

    @Autowired
    public ActualFlightDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Reservation> findAll() {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        return jdbc.query(
                "SELECT p.first_name,\r\n" +
                        " p.last_name,\r\n" +
                        " p.age,\r\n" +
                        " p.gender,\r\n" +
                        " p.phone,\r\n" +
                        " rt.departure_time,\r\n" +
                        " rt.arrival_time,\r\n" +
                        " rt.distance,\r\n" +
                        " rt.place,\r\n" +
                        " rt.price,\r\n" +
                        " r.date_of_flying,\r\n" +
                        " r.departure_time,\r\n" +
                        " r.arrival_time,\r\n" +
                        " r.hours_flying\r\n" +
                        "FROM reservation r\r\n" +
                        "JOIN passenger p \r\n" +
                        "    ON p.id = r.passenger_id\r\n" +
                        "JOIN route rt\r\n" +
                        "    ON rt.id = r.route_id",
                (rs, rowNum) ->
                        Reservation.builder()
                                .id(rs.getLong("id"))
                                .departureTime(LocalDateTime.parse(String.valueOf(rs.getTimestamp("departure_time"))))
                                .arrivalTime(LocalDateTime.parse(String.valueOf(rs.getTimestamp("arrival_time"))))
                                .dateOfFlying(LocalDateTime.parse(String.valueOf(rs.getDate("date_of_flying"))))
                                .hoursFlying(rs.getInt("hours_flying"))
                                .passenger(Passenger.builder()
                                        .firstName(rs.getString("first_name"))
                                        .lastName(rs.getString("last_name"))
                                        .age(rs.getInt("age"))
                                        .gender(rs.getString("gender"))
                                        .phone(rs.getString("phone"))
                                        .build())
                                .route(Route.builder()
                                        .departureTime(LocalDateTime.parse(String.valueOf(rs.getTimestamp("departure_time"))))
                                        .arrivalTime(LocalDateTime.parse(String.valueOf(rs.getTimestamp("arrival_time"))))
                                        .place(rs.getString("place"))
                                        .price(rs.getBigDecimal("price"))
                                        .distance(rs.getDouble("distance"))
                                        .build())
                                .build());
    }

    @Override
    public Long getAvailableAircraft() {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        List<Aircraft> availableAircrafts = jdbc.query("SELECT * FROM reservation", (rs, rowNum) ->
                Aircraft.builder().build());
        if (availableAircrafts.isEmpty()) {
            throw new FlightRuntimeException("No available aircrafts found");
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("availableAircraftId", availableAircrafts.get(0).getId());
            jdbc.update("UPDATE aircraft a SET a.available = 0 WHERE a.id = :aircraftId", params);
            return (Long) params.get("availableAircraftId");
        }
    }

    @Override
    public Long getAvailableRoute() {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        List<Route> availableRoutes = jdbc.query("SELECT * FROM route", (rs, rowNum) ->
                Route.builder().build());
        if (availableRoutes.isEmpty()) {
            throw new FlightRuntimeException("No available routes found");
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("availableRouteId", availableRoutes.get(0).getId());
            jdbc.update("UPDATE route rt SET rt.available = 0 WHERE rt.id = :routeId", params);
            return (Long) params.get("availableRouteId");
        }
    }

    @Override
    public ActualFlight saveActualFlight() {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        List<Reservation> reservationList = findAll();
        reservationList.forEach(reservation -> {
            if (reservation.getDepartureTime().equals(LocalDateTime.now())) {
                Passenger passenger = reservation.getPassenger();
                ActualFlight actualFlight = ActualFlight.builder()
                        .firstName(passenger.getFirstName())
                        .lastName(passenger.getLastName())
                        .gender(passenger.getGender())
                        .country("USA")
                        .email("test email")
                        .phone(passenger.getPhone())
                        .dateOfFlying(reservation.getDateOfFlying())
                        .departureTime(reservation.getDepartureTime())
                        .arrivalTime(reservation.getArrivalTime())
                        .hoursFlying(reservation.getHoursFlying())
                        .routeId(routeModelMapper.apply(routeDTO))
                        .aircraftId(aircraftService.getAvailableAircraft())
                        .pilotid(pilotService.getAvailablePilot())
                        .build();
                return jdbc.execute("INSERT INTO actual_flight VALUES " +
                        "(:dateOfFlying, :departureTime :arrivalTime, :hoursFlying, :firstName, :lastName, " +
                        ":gender, :address, :country, :email, :phone)");
            }
        });
    }
}
