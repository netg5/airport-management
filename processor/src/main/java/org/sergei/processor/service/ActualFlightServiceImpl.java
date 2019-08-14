package org.sergei.processor.service;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.processor.jdbc.dao.ActualFlightDAO;
import org.sergei.processor.jdbc.model.ActualFlight;
import org.sergei.processor.jdbc.model.Reservation;
import org.sergei.processor.jdbc.model.mappers.RouteModelMapper;
import org.sergei.processor.jdbc.repository.ActualFlightRepository;
import org.sergei.processor.jdbc.repository.ReservationRepository;
import org.sergei.processor.rest.dto.PassengerDTO;
import org.sergei.processor.rest.dto.ReservationDTO;
import org.sergei.processor.rest.dto.RouteDTO;
import org.sergei.processor.rest.dto.mappers.ReservationDTOListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
@EnableScheduling
public class ActualFlightServiceImpl implements ActualFlightService {

    private final ActualFlightDAO actualFlightDAO;
    private final ActualFlightRepository actualFlightRepository;
    private final Tracer tracer;
    private final RouteModelMapper routeModelMapper;
    private final PilotService pilotService;
    private final AircraftService aircraftService;
    private final ReservationRepository reservationRepository;
    private final ReservationDTOListMapper reservationDTOListMapper;

    @Autowired
    public ActualFlightServiceImpl(ActualFlightDAO actualFlightDAO, ActualFlightRepository actualFlightRepository,
                                   Tracer tracer,
                                   RouteModelMapper routeModelMapper,
                                   PilotService pilotService,
                                   AircraftService aircraftService,
                                   ReservationRepository reservationRepository,
                                   ReservationDTOListMapper reservationDTOListMapper) {
        this.actualFlightDAO = actualFlightDAO;
        this.actualFlightRepository = actualFlightRepository;
        this.tracer = tracer;
        this.routeModelMapper = routeModelMapper;
        this.pilotService = pilotService;
        this.aircraftService = aircraftService;
        this.reservationRepository = reservationRepository;
        this.reservationDTOListMapper = reservationDTOListMapper;
    }

    @Scheduled(cron = "${cron.expression}")
    @Override
    public void processFlights() {
        List<Reservation> reservationList = actualFlightDAO.findAll();
        List<ReservationDTO> reservationDTOList = reservationDTOListMapper.apply(reservationList);
        if (reservationList.isEmpty()) {
            log.info("Reservation list is empty");
        } else {
            Span span = tracer.buildSpan("Actualizing flight").start();
            reservationDTOList.forEach(reservationDTO -> {
                if (reservationDTO.getDepartureTime().equals(LocalDateTime.now())) {
                    PassengerDTO passengerDTO = reservationDTO.getPassenger();
                    RouteDTO routeDTO = reservationDTO.getRoute();
                    ActualFlight actualFlight = ActualFlight.builder()
                            .firstName(passengerDTO.getFirstName())
                            .lastName(passengerDTO.getLastName())
                            .gender(passengerDTO.getGender())
                            .country("USA")
                            .email("test email")
                            .phone(passengerDTO.getPhone())
                            .dateOfFlying(reservationDTO.getDateOfFlying())
                            .departureTime(reservationDTO.getDepartureTime())
                            .arrivalTime(reservationDTO.getArrivalTime())
                            .hoursFlying(reservationDTO.getHoursFlying())
//                            .route(routeModelMapper.apply(routeDTO))
//                            .aircraftId(aircraftService.getAvailableAircraft())
//                            .pilot(pilotService.getAvailablePilot())
                            .build();
                    actualFlightDAO.saveActualFlight();
                }
            });
            span.finish();
        }
    }
}
