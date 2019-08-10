package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.PassengerDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface PassengerService {
    ResponseEntity<ResponseDTO<PassengerDTO>> findPassengerById(Long passengerId);

    ResponseEntity<ResponseDTO<PassengerDTO>> findAllPassengers(int page, int size);

    ResponseEntity<ResponseDTO<PassengerDTO>> update(PassengerDTO request);

}
