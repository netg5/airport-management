package org.sergei.processor.jdbc.dao;

import org.sergei.processor.jdbc.model.ActualFlight;
import org.sergei.processor.jdbc.model.Aircraft;
import org.sergei.processor.jdbc.model.Reservation;
import org.sergei.processor.jdbc.model.Route;
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
        return jdbc.query("SELECT * FROM reservation", (rs, rowNum) ->
                Reservation.builder()
                        .id(rs.getLong("id"))
                        .departureTime(LocalDateTime.parse(String.valueOf(rs.getTimestamp("departure_time"))))
                        .arrivalTime(LocalDateTime.parse(String.valueOf(rs.getTimestamp("arrival_time"))))
                        .dateOfFlying(LocalDateTime.parse(String.valueOf(rs.getDate("date_of_flying"))))
                        .hoursFlying(rs.getInt("hours_flying"))
                        .passengerId(rs.getLong("passenger_id"))
                        .routeId(rs.getLong("route_id"))
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
        return null;
    }
}
