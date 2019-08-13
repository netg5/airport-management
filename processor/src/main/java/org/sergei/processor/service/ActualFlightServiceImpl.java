package org.sergei.processor.service;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.processor.jpa.model.ActualFlight;
import org.sergei.processor.jpa.model.Reservation;
import org.sergei.processor.jpa.model.mappers.RouteModelMapper;
import org.sergei.processor.jpa.repository.ActualFlightRepository;
import org.sergei.processor.jpa.repository.ReservationRepository;
import org.sergei.processor.rest.dto.PassengerDTO;
import org.sergei.processor.rest.dto.ReservationDTO;
import org.sergei.processor.rest.dto.RouteDTO;
import org.sergei.processor.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.processor.rest.exceptions.FailedHttpRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class ActualFlightServiceImpl implements ActualFlightService {

    @Value("${task.rate}")
    private int taskRate;

    private final TaskScheduler scheduler;
    private final ActualFlightRepository actualFlightRepository;
    private final Tracer tracer;
    private final RouteModelMapper routeModelMapper;
    private final PilotService pilotService;
    private final AircraftService aircraftService;
    private final ReservationRepository reservationRepository;
    private final ReservationDTOListMapper reservationDTOListMapper;

    @Autowired
    public ActualFlightServiceImpl(TaskScheduler scheduler,
                                   ActualFlightRepository actualFlightRepository,
                                   Tracer tracer,
                                   RouteModelMapper routeModelMapper,
                                   PilotService pilotService,
                                   AircraftService aircraftService,
                                   ReservationRepository reservationRepository,
                                   ReservationDTOListMapper reservationDTOListMapper) {
        this.scheduler = scheduler;
        this.actualFlightRepository = actualFlightRepository;
        this.tracer = tracer;
        this.routeModelMapper = routeModelMapper;
        this.pilotService = pilotService;
        this.aircraftService = aircraftService;
        this.reservationRepository = reservationRepository;
        this.reservationDTOListMapper = reservationDTOListMapper;
        scheduler.schedule(this, Instant.now().plusMillis(taskRate));
    }

    @Override
    public void run() {
        processFlights();
        scheduler.schedule(this, Instant.now().plusMillis(taskRate));
    }

    @Override
    public void processFlights() {
        List<Reservation> reservationList = reservationRepository.findAll();
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
                            .route(routeModelMapper.apply(routeDTO))
                            .aircraft(aircraftService.getAvailableAircraft())
                            .pilot(pilotService.getAvailablePilot())
                            .build();
                    actualFlightRepository.save(actualFlight);
                }
            });
            span.finish();
        }
    }
}
