package org.sergei.processor.service;

import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.processor.feign.ReservationFeignClient;
import org.sergei.processor.jpa.repository.ActualFlightRepository;
import org.sergei.processor.rest.dto.ActualFlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Slf4j
@Service
public class ActualFlightServiceImpl implements ActualFlightService {

    @Value("${task.rate}")
    private int taskRate;

    //    private final TaskScheduler scheduler;
    private final ActualFlightRepository actualFlightRepository;
    private final ReservationFeignClient reservationFeignClient;
    private final ResponseMessageService responseMessageService;
    private final Tracer tracer;

    @Autowired
    public ActualFlightServiceImpl(/*TaskScheduler scheduler,*/
            ActualFlightRepository actualFlightRepository,
            ReservationFeignClient reservationFeignClient,
            ResponseMessageService responseMessageService, Tracer tracer) {
//        this.scheduler = scheduler;
        this.actualFlightRepository = actualFlightRepository;
        this.reservationFeignClient = reservationFeignClient;
        this.responseMessageService = responseMessageService;
        this.tracer = tracer;
//        scheduler.schedule(this, Instant.now().plusMillis(taskRate));
    }

//    @Override
//    public void run() {
//        processFlights();
//        scheduler.schedule(this, Instant.now().plusMillis(taskRate));
//    }

    @KafkaListener(topics = "RESERVATION", groupId = "org.sergei.processing")
    @Override
    public List<ActualFlightDTO> processFlights(String message) {
        log.info("Received message is: {}", message);
//        Span span = tracer.buildSpan("reservationFeignClient.getAllReservations() started").start();
//        ResponseEntity<ResponseDTO<ReservationDTO>> reservationResponse = reservationFeignClient.getAllReservations();
//        log.info("Reservation response is: {}", reservationResponse.getBody().getResponse().toString());
//        span.finish();
        return null;
    }

}
