package org.sergei.booking.service;

import org.sergei.booking.rest.dto.BookingDTO;
import org.sergei.booking.rest.dto.request.BookingRequestDTO;
import org.sergei.booking.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface BookingService {

    ResponseEntity<ResponseDTO<BookingDTO>> findAll();

    ResponseEntity<ResponseDTO<BookingDTO>> findAllForPassenger(Long passengerId);

    ResponseEntity<ResponseDTO<BookingDTO>> findOneForPassenger(Long passengerId, Long bookingId);

    ResponseEntity<ResponseDTO<BookingDTO>> saveBooking(BookingRequestDTO request);

    ResponseEntity<ResponseDTO<BookingDTO>> updateBooking(BookingDTO request);

    ResponseEntity<ResponseDTO<BookingDTO>> discardBooking(Long passengerId, Long bookingId);
}
