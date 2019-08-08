package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.PassengerDTO;
import org.sergei.reservation.rest.dto.PassengerUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface PassengerService {
    ResponseEntity<ResponseDTO<PassengerDTO>> findOne(Long passengerId);

    ResponseEntity<ResponseDTO<PassengerDTO>> findAllPassengers(int page, int size);

    ResponseEntity<ResponseDTO<PassengerDTO>> save(PassengerDTO passengerDTO);

    ResponseEntity<ResponseDTO<PassengerDTO>> update(PassengerUpdateRequestDTO request);

    ResponseEntity<ResponseDTO<PassengerDTO>> delete(Long passengerId);
}
