package org.sergei.booking.service;

import org.sergei.booking.rest.dto.PassengerDTO;
import org.sergei.booking.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface PassengerService {
    ResponseEntity<ResponseDTO<PassengerDTO>> findPassengerById(Long passengerId);

    ResponseEntity<ResponseDTO<PassengerDTO>> findAllPassengers(int page, int size);

    ResponseEntity<ResponseDTO<PassengerDTO>> update(PassengerDTO request);

}
