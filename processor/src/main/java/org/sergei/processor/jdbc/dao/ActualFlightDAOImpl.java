package org.sergei.processor.jdbc.dao;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.processor.jdbc.model.ActualFlight;
import org.sergei.processor.jdbc.model.Passenger;
import org.sergei.processor.jdbc.model.Reservation;
import org.sergei.processor.jdbc.model.Route;
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
@Slf4j
@Repository
public class ActualFlightDAOImpl implements ActualFlightDAO {

    private final DataSource dataSource;
    private final Tracer tracer;

    @Autowired
    public ActualFlightDAOImpl(DataSource dataSource, Tracer tracer) {
        this.dataSource = dataSource;
        this.tracer = tracer;
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
        List<Long> availableAircraftIds = jdbc.query("SELECT * FROM aircraft a WHERE a.available = 1",
                (rs, rowNum) -> rs.getLong("aircraft_id"));
        Map<String, Object> params = new HashMap<>();
        params.put("aircraftId", availableAircraftIds.get(0));
        jdbc.update("UPDATE aircraft a SET a.available = 0 WHERE a.id = :aircraftId", params);
        return (Long) params.get("aircraftId");
    }

    @Override
    public Long getAvailablePilot() {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        List<Long> availablePilotIds = jdbc.query("SELECT * FROM pilot p WHERE p.available = 1",
                (rs, rowNum) -> rs.getLong("pilot_id"));
        Map<String, Object> params = new HashMap<>();
        params.put("pilotId", availablePilotIds.get(0));
        jdbc.update("UPDATE pilot p SET p.available = 0 WHERE p.id = :pilotId", params);
        return (Long) params.get("aircraftId");
    }

    @Override
    public void saveActualFlight() {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        List<Reservation> reservationList = findAll();
        reservationList.forEach(reservation -> {
            if (reservation.getDepartureTime().equals(LocalDateTime.now())) {
                Long availableAircraftId = getAvailableAircraft();
                Long availablePilotId = getAvailablePilot();
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
                        .routeId(reservation.getRoute().getId())
                        .aircraftId(availableAircraftId)
                        .pilotId(availablePilotId)
                        .build();
                Map<String, Object> params = new HashMap<>();
                params.put("dateOfFlying", actualFlight.getDateOfFlying());
                params.put("departureTime", actualFlight.getDepartureTime());
                params.put("arrivalTime", actualFlight.getArrivalTime());
                params.put("hoursFlying", actualFlight.getHoursFlying());
                params.put("firstName", actualFlight.getFirstName());
                params.put("lastName", actualFlight.getLastName());
                params.put("gender", actualFlight.getGender());
                params.put("address", actualFlight.getAddress());
                params.put("country", actualFlight.getCountry());
                params.put("email", actualFlight.getEmail());
                params.put("phone", actualFlight.getPhone());
                params.put("routeId", actualFlight.getRouteId());
                params.put("aircraftId", actualFlight.getAircraftId());
                params.put("pilotId", actualFlight.getPilotId());

                Span span = tracer.buildSpan("executing update of actual_flight table").start();

                jdbc.update("INSERT INTO actual_flight VALUES " +
                        "(:dateOfFlying, :departureTime :arrivalTime, :hoursFlying, :firstName, :lastName, " +
                        ":gender, :address, :country, :email, :phone)", params);
                log.debug("actual_flight table updated");
                span.finish();
            }
        });
    }
}
