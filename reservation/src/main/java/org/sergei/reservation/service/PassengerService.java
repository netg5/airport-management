package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.PassengerResponseDTO;
import org.sergei.reservation.rest.dto.PassengerUpdateRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Sergei Visotsky
 */
public interface PassengerService {
    ResponseEntity<ResponseDTO<PassengerResponseDTO>> findOne(Long passengerId);

    ResponseEntity<ResponseDTO<PassengerResponseDTO>> findAllPassengers(int page, int size);

    ResponseEntity<ResponseDTO<PassengerResponseDTO>> save(PassengerResponseDTO passengerResponseDTO);

    ResponseEntity<ResponseDTO<PassengerResponseDTO>> update(PassengerUpdateRequestDTO request);

    ResponseEntity<ResponseDTO<PassengerResponseDTO>> delete(Long passengerId);
}
