package org.sergei.orchestration.service;

import com.google.common.collect.ImmutableList;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final PassengerFeignClient passengerFeignClient;
    private final BookingFeignClient bookingFeignClient;
    private final FlightFeignClient flightFeignClient;
    private final FlyModeFeignClient flyModeFeignClient;

    @Autowired
    public TicketServiceImpl(PassengerFeignClient passengerFeignClient,
                             BookingFeignClient bookingFeignClient,
                             FlightFeignClient flightFeignClient,
                             FlyModeFeignClient flyModeFeignClient) {
        this.passengerFeignClient = passengerFeignClient;
        this.bookingFeignClient = bookingFeignClient;
        this.flightFeignClient = flightFeignClient;
        this.flyModeFeignClient = flyModeFeignClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponseDTO<List<TicketDTO>>> findAllTickets(Long passengerId, String currency) {
        List<TicketDTO> tickets = new ArrayList<>();
            ResponseEntity<ResponseDTO<PassengerDTO>> passengerResponse =
                passengerFeignClient.getPassengerById(passengerId);
        List<ResponseErrorDTO> errors = new ArrayList<>();
        if (!passengerResponse.getBody().getErrorList().isEmpty()) {
            errors.addAll(passengerResponse.getBody().getErrorList());
        } else {
            ResponseEntity<ResponseDTO<List<BookingDTO>>> bookingResponse =
                    bookingFeignClient.getAllBookingsForPassenger(passengerId);
            if (!bookingResponse.getBody().getErrorList().isEmpty()) {
                errors.addAll(bookingResponse.getBody().getErrorList());
            } else {
                List<List<BookingDTO>> bookings = bookingResponse.getBody().getResponse();
                int index = 0;
                for (List<BookingDTO> bookingDTO : bookings) {
                    ResponseEntity<ResponseDTO<FlightDTO>> flightResponse =
                            flightFeignClient.getFlightById(bookingDTO.get(index).getFlightId());
                    flyModeFeignClient.getFlyModeByCode(bookingDTO.get(index).getFlyModeCode());
                    index++;
                    List<FlightDTO> flightResponseBody = flightResponse.getBody().getResponse();
                    TicketDTO ticketDTO = TicketDTO.builder()
                            .amount(flightResponseBody.get(index).getPrice().doubleValue())
                            .departureTime(flightResponseBody.get(index).getDepartureTime())
                            .arrivalTime(flightResponseBody.get(index).getArrivalTime())
//                            .currency(bookingDTO)
                            .build();
                    tickets.add(ticketDTO);
                }
            }
        }
        return new ResponseEntity<>(new ResponseDTO<>(ImmutableList.of(), ImmutableList.of(tickets)), HttpStatus.OK);
    }
}
