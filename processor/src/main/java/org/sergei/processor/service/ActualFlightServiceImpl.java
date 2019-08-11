package org.sergei.processor.service;

import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.processor.feign.ReservationFeignClient;
import org.sergei.processor.jpa.repository.ActualFlightRepository;
import org.sergei.processor.rest.dto.ActualFlightDTO;
import org.sergei.processor.rest.dto.ReservationDTO;
import org.sergei.processor.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class ActualFlightServiceImpl implements ActualFlightService {

    @Value("${task.value}")
    private int taskRate;

    private final TaskScheduler scheduler;
    private final ActualFlightRepository actualFlightRepository;
    private final ReservationFeignClient reservationFeignClient;
    private final ResponseMessageService responseMessageService;
    private final Tracer tracer;

    @Autowired
    public ActualFlightServiceImpl(TaskScheduler scheduler,
                                   ActualFlightRepository actualFlightRepository,
                                   ReservationFeignClient reservationFeignClient,
                                   ResponseMessageService responseMessageService, Tracer tracer) {
        this.scheduler = scheduler;
        this.actualFlightRepository = actualFlightRepository;
        this.reservationFeignClient = reservationFeignClient;
        this.responseMessageService = responseMessageService;
        this.tracer = tracer;
        scheduler.schedule(this, Instant.now().plusMillis(taskRate));
    }

    @Override
    public void run() {
        processFlights();
        scheduler.schedule(this, Instant.now().plusMillis(taskRate));
    }

    @Override
    public List<ActualFlightDTO> processFlights() {
        ResponseEntity<ResponseDTO<ReservationDTO>> reservationResponse = reservationFeignClient.getAllReservations();

        log.info("Reservation response is: {}", reservationResponse.getBody().getResponse().toString());

        return null;
    }

}
