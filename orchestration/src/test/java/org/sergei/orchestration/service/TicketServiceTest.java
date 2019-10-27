package org.sergei.orchestration.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.sergei.orchestration.feign.BookingFeignClient;
import org.sergei.orchestration.feign.FlightFeignClient;
import org.sergei.orchestration.feign.FlyModeFeignClient;
import org.sergei.orchestration.feign.PassengerFeignClient;
import org.sergei.orchestration.rest.dto.BookingDTO;
import org.sergei.orchestration.rest.dto.FlightDTO;
import org.sergei.orchestration.rest.dto.PassengerDTO;
import org.sergei.orchestration.rest.dto.TicketDTO;
import org.sergei.orchestration.rest.dto.response.ResponseDTO;
import org.sergei.orchestration.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@SpringBootTest
public class TicketServiceTest {

    @Autowired
    private PassengerFeignClient passengerFeignClient;

    @Autowired
    private BookingFeignClient bookingFeignClient;

    @Autowired
    private FlightFeignClient flightFeignClient;

    @Autowired
    private FlyModeFeignClient flyModeFeignClient;

    @Test
    public void findAllTicketsTest() {
        final long passengerId = 1L;
        List<TicketDTO> tickets = new ArrayList<>();
        ResponseEntity<ResponseDTO<PassengerDTO>> passengerResponse =
                passengerFeignClient.getPassengerById(passengerId);
        List<ResponseErrorDTO> errors = new ArrayList<>();
        if (passengerResponse.getBody().getErrorList() != null) {
            errors.addAll(passengerResponse.getBody().getErrorList());
        } else {
            ResponseEntity<ResponseDTO<List<BookingDTO>>> bookingResponse =
                    bookingFeignClient.getAllBookingsForPassenger(passengerId);
            if (bookingResponse.getBody().getErrorList() != null) {
                errors.addAll(bookingResponse.getBody().getErrorList());
            } else {
                List<List<BookingDTO>> bookings = bookingResponse.getBody().getResponse();
                int index = 0;
                for (List<BookingDTO> bookingDTO : bookings) {
                    ResponseEntity<ResponseDTO<FlightDTO>> flightResponse =
                            flightFeignClient.getFlightById(bookingDTO.get(index).getFlightId());
                    flyModeFeignClient.getFlyModeByCode(bookingDTO.get(index).getFlyModeCode());
                    index++;
                    TicketDTO ticketDTO = TicketDTO.builder()
                            .amount(flightResponse.getBody().getResponse().get(index).getPrice().doubleValue())
                            .departureTime(flightResponse.getBody().getResponse().get(index).getDepartureTime())
                            .arrivalTime(flightResponse.getBody().getResponse().get(index).getArrivalTime())
//                            .currency(bookingDTO)
                            .build();
                    tickets.add(ticketDTO);
                }
            }
        }
        tickets.forEach(ticketDTO -> log.info(ticketDTO.toString()));
    }
}
